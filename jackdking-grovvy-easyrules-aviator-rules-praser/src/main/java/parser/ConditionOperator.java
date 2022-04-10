package parser;

import java.util.HashMap;
import java.util.Map;

/**
 * 条件操作符
 *
 * @author zrz
 * @date 2021/5/13 13:53
 */
public enum ConditionOperator {
    /**
     * 与
     */
    AND("and", "&&", ConditionOperatorType.LOGIC_OPERATOR),

    /**
     * 或
     */
    OR("or", "||", ConditionOperatorType.LOGIC_OPERATOR),

    /**
     * 非
     */
    NOT("not", "!", ConditionOperatorType.LOGIC_OPERATOR),

    /**
     * 有值
     */
    NOTNULL("notnull", "!=", ConditionOperatorType.COMPARE_OPERATOR),

    /**
     * 无值
     */
    NULL("null", "==", ConditionOperatorType.COMPARE_OPERATOR),

    /**
     * 大于等于
     */
    GREATER_AND_EQUAL("ge", ">=", ConditionOperatorType.COMPARE_OPERATOR),

    /**
     * 大于
     */
    GREATER_THAN("gt", ">", ConditionOperatorType.COMPARE_OPERATOR),

    /**
     * 等于
     */
    EQUAL("eq", "==", ConditionOperatorType.COMPARE_OPERATOR),

    /**
     * 不等于
     */
    NOT_EQUAL("ne", "!=", ConditionOperatorType.COMPARE_OPERATOR),

    /**
     * 小于等于
     */
    LOWER_AND_EQUAL("le", "<=", ConditionOperatorType.COMPARE_OPERATOR),

    /**
     * 小于
     */
    LOWER_THAN("lt", "<", ConditionOperatorType.COMPARE_OPERATOR);

    private final String source;

    private final String target;

    private final ConditionOperatorType type;

    ConditionOperator(String source, String target, ConditionOperatorType type) {
        this.source = source;
        this.target = target;
        this.type = type;
    }

    private static final Map<String, ConditionOperator> ENUM_MAP = new HashMap<>(64);

    static {
        for (ConditionOperator v : values()) {
            ENUM_MAP.put(v.getSource(), v);
        }
    }

    /**
     * 字符串转换成枚举
     *
     * @param v 源操作符
     * @return 枚举
     */
    public static ConditionOperator fromString(String v) {
        return ENUM_MAP.get(v);
    }

    /**
     * 是否比较运算符
     *
     * @param source 源操作符
     * @return 返回bool
     */
    public static boolean isCompareOperator(String source) {
        return ENUM_MAP.get(source).getType().equals(ConditionOperatorType.COMPARE_OPERATOR);
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public ConditionOperatorType getType() {
        return type;
    }
}