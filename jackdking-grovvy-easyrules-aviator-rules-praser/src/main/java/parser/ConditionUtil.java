package parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 规则条件生成及执行助手类
 *
 * @author zrz
 * @date 2021/5/13 14:05
 */
public class ConditionUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String GROOVY_CONDITION_FUNC_NAME = "validateCondition";

    ConditionUtil() {
    }

    /**
     * 执行规则条件
     *
     * @param condition 条件表达式json结构
     * @param args      条件变量值
     * @return 返回true或false
     */
    public static boolean execCondition(String condition, Map<String, Object> args) throws IOException, ScriptException, NoSuchMethodException {
        return (Boolean) ScriptUtil.invokeScriptFunction(ScriptUtil.SCRIPT_GROOVY,
                generateGroovyFunc(condition, GROOVY_CONDITION_FUNC_NAME, "args"),
                GROOVY_CONDITION_FUNC_NAME,
                args
        );
    }

    /**
     * @param condition 条件表达式json形式
     * @param funcName  脚本函数名
     * @param argName   函数参数名称
     * @return 返回可给ScriptEngine执行的Groovy函数
     */
    public static String generateGroovyFunc(String condition, String funcName, String argName) throws IOException {
        return String.format("def %s(%s){ return %s;}", funcName, argName, convertToGroovy(condition, argName + "."));
    }

    /**
     * 解析规则字符串，转换成表达式形式
     * 示例:
     * <p>
     * 输入：
     * { "or":
     * [
     * { "and": [
     * { "ge": ["A", 10] },
     * { "eq": ["B", 20] }
     * ]},
     * { "lt": ["C", 30] },
     * { "ne": ["D", 50] }
     * ]}
     * </p>
     * <p>
     * 输出：
     * ( A >= 10 && B == 20 ) || ( C < 30 ) || ( D != 50 )
     * </p>
     *
     * @param condition   规则的json字符串形式
     * @param paramPrefix 条件表达式参数前缀
     * @return 返回 bool 表达式
     * @throws IOException 解析json字符串异常
     */
    public static String convertToGroovy(String condition, String paramPrefix) throws IOException {

        JsonNode jsonNode = OBJECT_MAPPER.readTree(condition);
        return convertToGroovy(jsonNode, paramPrefix, false);
    }

    /**
     * 抽取条件表达式变量列表
     *
     * @param condition 条件表达式json结构
     * @return 返回条件表达式变量列表, 包含变量数据来源、id
     * @throws IOException io异常
     */
    public static List<AttributeParam> extractParams(String condition) throws IOException {
        JsonNode jsonNode = OBJECT_MAPPER.readTree(condition);
        List<AttributeParam> params = Lists.newArrayList();
        extractParams(jsonNode, params);
        return params;
    }

    /**
     * 解析规则节点，转换成表达式形式
     *
     * @param node        Jackson Node
     * @param paramPrefix 参数前缀
     * @param isParam     节点是否变量
     * @return 返回bool表达式
     */
    private static String convertToGroovy(JsonNode node, String paramPrefix, boolean isParam) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> it = node.fields();
            if (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();

                // "非"运算符表达式特殊组装
                if (ConditionOperator.NOT.equals(ConditionOperator.fromString(entry.getKey()))) {
                    return String.format("!%s", convertToGroovy(entry.getValue(), paramPrefix, isParam));
                }

                List<String> arrayList = new ArrayList<>();
                int index = 0;
                for (JsonNode jsonNode : entry.getValue()) {
                    isParam = index == 0;
                    arrayList.add(convertToGroovy(jsonNode, paramPrefix, isParam));
                    index++;
                }

                // 如果是比较操作符，需要添加参数的前缀
                if (ConditionOperator.isCompareOperator(entry.getKey())) {
                    arrayList.set(0, paramPrefix + arrayList.get(0));
                    arrayList = arrayList.stream().limit(2).collect(Collectors.toList());
                }

                return String.format("(%s)", String.join(" " + ConditionOperator.fromString(entry.getKey()).getTarget() + " ", arrayList));
            }

            // 兼容空节点
            return " 1==1";
        } else if (node.isTextual() && !isParam) {
            return node.toString();
        } else if (node.isValueNode()) {
            return node.asText();
        }

        return "";
    }

    /**
     * 抽取表达式中的变量
     *
     * @param node   jackson node
     * @param params 变量列表
     * @return 返回变量名列表
     */
    private static void extractParams(JsonNode node, List<AttributeParam> params) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> it = node.fields();
            if (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();

                if (ConditionOperator.isCompareOperator(entry.getKey())) {
                    String key = entry.getValue().get(0).asText();
                    AttributeParam param = new AttributeParam();
                    param.setCode(key);
                    param.setSource(entry.getValue().get(2).intValue());
                    param.setId(entry.getValue().get(3).intValue());
                    if (!params.contains(param)) {
                        params.add(param);
                    }
                } else {
                    for (JsonNode jsonNode : entry.getValue()) {
                        extractParams(jsonNode, params);
                    }
                }
            }
        }
    }


    @Data
    public static class AttributeParam {

        /**
         * 属性编码
         */
        private String code;
        /**
         * 属性来源：1事件消息体  2用户属性 3累计器
         */
        private Integer source;
        /**
         * 属性id
         */
        private Integer id;
    }

}