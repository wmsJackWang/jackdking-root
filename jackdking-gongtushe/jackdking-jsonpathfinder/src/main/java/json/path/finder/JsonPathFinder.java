package json.path.finder;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.ir.Optimistic;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.util.CollectionUtils;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.*;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName JsonPathFinder
 * @Description TODO
 * @Author jackdking
 * @Date 23/01/2024 8:54 下午
 * @Version 2.0
 **/
public class JsonPathFinder {
public static void main(String[] args) {

    JSONObject data = JSON.parseObject("{\"expectEmpName\" : \"鸣胜\",\"hours\" : 17,\"expectEmpId\" : \"322319\",\"minutes\" : 2000,\"executeStatus\" : 0,\"merchant\" : \"lbs\",\"expect_time\" : \"2024-01-18 22:00:00\",\"platformCheckTime\" : 2,\"relate_info\" : \"产品: 徐亚曼-423962\\\\n算法: 孙艺轩-353109\\\\n工程: 吴群旭-362966\\\\n\",\"platformHistoryCheckInfo\" : {    \"1\" : {      \"checkTime\" : \"2024-01-23 16:03:58\",      \"detail\" : \"预警数据检测结果:false，数据日期:ds, 检测日期:2024-01-23 16:03:58\",      \"ds\" : \"20240122\"    },    \"2\" : {      \"checkTime\" : \"2024-01-23 16:05:26\",      \"detail\" : \"预警数据检测结果:false，数据日期:ds, 检测日期:2024-01-23 16:05:26\",      \"ds\" : \"20240122\"    }},\"lastOpDate\" : \"2024-01-18 21:47:02\",\"days\" : 0,\"DingMsgDriveTime\" : 1,\"strategyId\" : 183,\"strategy_name\" : \"行前顺路推荐_二期\",\"status\" : \"灰度:5%\"}");
    System.out.println(data.toJSONString());

    List<PathInfo> result = findJsonPath(data);
    System.out.println(JSON.toJSONString(result));
  }

  private static List<PathInfo> findJsonPath(JSONObject data) {

    List<String> pathList = new ArrayList<>();
    List<PathInfo> pathResultList = new ArrayList<>();
    findJsonPathList(data, pathList, pathResultList);
    return pathResultList;
  }

  private static void findJsonPathList(JSONObject data, List<String> pathList, List<PathInfo> pathResultList) {
    if (data == null || data.keySet().isEmpty()) {
      return;
    }

    data.entrySet().stream()
      .forEach(stringObjectEntry -> {
        if (stringObjectEntry.getValue() instanceof JSONArray) {
          return;
        }
        if (stringObjectEntry.getValue() instanceof JSONObject) {
          pathList.add(stringObjectEntry.getKey());
          findJsonPathList(data.getJSONObject(stringObjectEntry.getKey()), pathList, pathResultList);
          pathList.remove(pathList.size()-1);
        }else {
          PathInfo pathInfo = createPathInfo(stringObjectEntry, pathList, data);
          pathResultList.add(pathInfo);
        }

      });
    return;
  }

  private static PathInfo createPathInfo(Map.Entry<String, Object> stringObjectEntry, List<String> pathList, JSONObject data) {
    PathInfo pathInfo = null;
    String key = stringObjectEntry.getKey();

    pathInfo= createBigDecimalPathInfo(key, data);
    if (pathInfo != null) {
      return populatePath(pathInfo, pathList, key);
    }

    pathInfo= createBigIntergerPathInfo(key, data);
    if (pathInfo != null) {
      return populatePath(pathInfo, pathList, key);
    }

    pathInfo= createLongPathInfo(key, data);
    if (pathInfo != null) {
      return populatePath(pathInfo, pathList, key);
    }

    pathInfo= createDoublePathInfo(key, data);
    if (pathInfo != null) {
      return populatePath(pathInfo, pathList, key);
    }

    pathInfo= createIntergerPathInfo(key, data);
    if (pathInfo != null) {
      return populatePath(pathInfo, pathList, key);
    }

    pathInfo= createStringPathInfo(key, data);
    if (pathInfo != null) {
      return populatePath(pathInfo, pathList, key);
    }

    return pathInfo;
  }

  private static PathInfo createIntergerPathInfo(String key, JSONObject data) {
    try{
      Integer val = data.getInteger(key);
      if (val != null) {
        PathInfo pathInfo = new PathInfo();
        pathInfo.setKey(key);
        pathInfo.setValue(val);
        pathInfo.setValueType("Integer");
        return pathInfo;
      }

    }catch (Throwable throwable) {

    }
    return null;
  }

  private static PathInfo createDoublePathInfo(String key, JSONObject data) {
     try {
      Double val = data.getDouble(key);
      if (val != null) {
        PathInfo pathInfo = new PathInfo();
        pathInfo.setKey(key);
        pathInfo.setValue(val);
        pathInfo.setValueType("Double");
        return pathInfo;
      }

    }catch (Throwable throwable) {

    }
    return null;
  }

  private static PathInfo createLongPathInfo(String key, JSONObject data) {
    try{
      Long val = data.getLong(key);
      if (val != null) {
        PathInfo pathInfo = new PathInfo();
        pathInfo.setKey(key);
        pathInfo.setValue(val);
        pathInfo.setValueType("Long");
        return pathInfo;
      }

    }catch (Throwable throwable) {

    }
    return null;
  }

  private static PathInfo createBigIntergerPathInfo(String key, JSONObject data) {
    try{
      BigInteger val = data.getBigInteger(key);
      if (val != null) {
        PathInfo pathInfo = new PathInfo();
        pathInfo.setKey(key);
        pathInfo.setValue(val);
        pathInfo.setValueType("BigInteger");
        return pathInfo;
      }
    }catch (Throwable throwable) {

    }
    return null;
  }

  private static PathInfo createBigDecimalPathInfo(String key, JSONObject data) {
    try {
      BigDecimal val = data.getBigDecimal(key);
      if (val != null) {
        PathInfo pathInfo = new PathInfo();
        pathInfo.setKey(key);
        pathInfo.setValue(val);
        pathInfo.setValueType("BigDecimal");
        return pathInfo;
      }
    }catch (Throwable throwable) {

    }
    return null;
  }

  private static PathInfo populatePath(PathInfo pathInfo, List<String> pathList, String key) {
    StringBuilder sb = new StringBuilder();
    sb.append("$");
    Optional.ofNullable(pathList).orElseGet(Collections::emptyList)
      .forEach(path -> sb.append("." + path));
    sb.append("." + key);
    pathInfo.setPath(sb.toString());
    return pathInfo;
  }

  private static PathInfo createStringPathInfo(String key, JSONObject data) {
    String val = data.getString(key);
    if (val != null) {
      PathInfo pathInfo = new PathInfo();
      pathInfo.setKey(key);
      pathInfo.setValue(val);
      pathInfo.setValueType("String");
      return pathInfo;
    }
    return null;
  }

  @Data
  static class PathInfo {
    String key;
    String valueType;
    Object value;
    String path;
  }
}
