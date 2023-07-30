package OptionalTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName VarReplaceTest
 * @Description TODO
 * @Author jackdking
 * @Date 19/07/2023 5:52 下午
 * @Version 2.0
 **/
public class VarReplaceTest {
  private static final String LEFT = "_LEFTBRACE_";
  private static final String RIGHT = "_RIGHTBRACE_";
  private static Map<String, String> valueMap = new HashMap<>();
  static {
    Calendar cal = Calendar.getInstance();
    String today = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    cal.add(Calendar.DATE, -1);
    String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    valueMap.put("today", today);
    valueMap.put("yesterday", yesterday);
  }

  public static void main(String[] args) {
    String sql = "create table pssdb.HEALTH_${yesterday} as select * FROM pssdb.ORG_TREE_${today} T WHERE T.set_day='${yesterday}' ;";
    String replaceStr = replaceBrace(sql);
    System.out.println(replaceStr);
    System.out.println(recoverBrace(replaceStr));
    System.out.println(replaceVarValue(sql));
  }

  public static String replaceBrace(String sql) {
    String pattern = "(\\$\\{(.+?)\\})";
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(sql);
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      String key = m.group();
//            System.out.print(key);
//            System.out.print("  -> ");
      if (key != null && key.length() > 3) {
        String value = key.replace("${", LEFT).replace("}", RIGHT);//;
//                System.out.print(value);
        m.appendReplacement(sb, value == null ? "" : value);
      }
    }
    m.appendTail(sb);
    return sb.toString();
  }

  /**
   * 还原${}
   *
   * @param name
   * @return
   */
  public static String recoverBrace(String name) {
    if (name == null) {
      return null;
    }
    //转义字符要使用\\转义或使用"\\\\" +
    String recoverStr = name.replaceAll(LEFT, "\\${").replaceAll(RIGHT, "}");//;
    return recoverStr;
  }


  public static String replaceVarValue(String sql) {
    String pattern = "\\$\\{(.+?)\\}";
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(sql);
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      // 取第一个group
      String key = m.group(1);
      String value = valueMap.get(key);//;
      m.appendReplacement(sb, value == null ? "" : value);
    }
    m.appendTail(sb);
    return sb.toString();
  }

}
