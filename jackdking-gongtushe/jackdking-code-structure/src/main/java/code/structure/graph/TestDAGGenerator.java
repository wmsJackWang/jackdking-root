package code.structure.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName TestDAGGenerator
 * @Description TODO
 * @Author jackdking
 * @Date 27/12/2022 2:32 下午
 * @Version 2.0
 **/
public class TestDAGGenerator {

  /**
   * FORMAT:
   *
   * <pre>
   * digraph abc{
   *   a;
   *   b;
   *   c;
   *   d;
   *
   *   a -> b;
   *   b -> d;
   *   c -> d;
   * }
   * </pre>
   *
   * @param graphName
   * @param vectorList
   */
  public static <T> String convertToDot(String graphName, List<Vector<T>> vectorList) {
    StringBuilder sb = new StringBuilder();
    sb.append("@startuml\n\n")
      .append("digraph ").append(graphName).append(" { \n");
    vectorList.forEach(vector -> sb.append("    ").append(vector.getName()).append(";\n"));
    sb.append("\n");
    vectorList.forEach(from -> from.getTo().forEach(to -> {
      sb.append("    ").append(from.getName()).append(" -> ").append(to.getName()).append(";\n");
    }));
    sb.append("}\n")
      .append("\n@enduml\n");
    return sb.toString();
  }

  public static void main(String[] args) {
    final int vectorCount = 10;
    List<Vector<Integer>> vectorList = new ArrayList<>(vectorCount);
    for (int i = 0; i < vectorCount; i++) {
      vectorList.add(new Vector<>(i));
    }

    DAGGenerator.random(vectorList);

    String dotGraph = convertToDot("test_DAG_generator", vectorList);
    System.out.println(dotGraph);
  }
}
