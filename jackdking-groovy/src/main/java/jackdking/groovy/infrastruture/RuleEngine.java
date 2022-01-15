package jackdking.groovy.infrastruture;

import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName RuleEngine
 * @Description TODO
 * @Author jackdking
 * @Date 12/01/2022 11:41 上午
 * @Version 2.0
 **/
public class RuleEngine {

    private static final ConcurrentHashMap<String, Template> SCRIPT_CACHE = new ConcurrentHashMap<>();

    private static final SimpleTemplateEngine ENGINE = new SimpleTemplateEngine();

    private static final String CONDITION_STR_TEMPLATE = "${%s ? true : false}";

    private static final String EXECUTE_STR_TEMPLATE = "${%s}";

    public static void main(String[] args) {
        String request = "data";
        String condition = "request.length() == 4";
        String execute = "System.out.println(request)";

        if (condition(condition, request)) {
            execute(execute, request);
        }
    }

    public static boolean condition(String condition, String request) {
        boolean result;
        String conditionTemplate = String.format(CONDITION_STR_TEMPLATE, condition);
        Map<String, Object> context = new HashMap<>();
        context.put("request", request);
        try {
            Template template;
            if (SCRIPT_CACHE.containsKey(condition)) {
                template = SCRIPT_CACHE.get(condition);
            } else {
                template = ENGINE.createTemplate(conditionTemplate);
                SCRIPT_CACHE.put(condition, template);
            }
            Writer writer = new StringWriter();
            template.make(context).writeTo(writer);
            result = Boolean.parseBoolean(writer.toString());
        } catch (Exception e) {
            throw new RuntimeException("模板解析异常" + conditionTemplate);
        }
        return result;
    }

    public static void execute(String execute, String request) {
        String executeTemplate = String.format(EXECUTE_STR_TEMPLATE, execute);
        Map<String, Object> context = new HashMap<>();
        context.put("request", request);
        Template template;
        try {
            if (SCRIPT_CACHE.containsKey(execute)) {
                template = SCRIPT_CACHE.get(execute);
            } else {
                template = ENGINE.createTemplate(executeTemplate);
                SCRIPT_CACHE.put(execute, template);
            }
            template.make(context).writeTo(new StringWriter());
        } catch (Exception e) {
            throw new RuntimeException("模板解析异常" + executeTemplate);
        }
    }

}
