package code.structure.graph;

import java.util.List;
import java.util.Random;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName DAGGenerator
 * @Description TODO
 * @Author jackdking
 * @Date 27/12/2022 2:31 下午
 * @Version 2.0
 **/
public class DAGGenerator {

  /**
   * 传入一个拓扑排序好的顶点列表，然后从这个拓扑排序中随机生成一个DAG
   *
   * @param topologicalSortedVectorList
   * @return
   */
  public static <T> void random(List<Vector<T>> topologicalSortedVectorList) {
    for (int i = 0, end = topologicalSortedVectorList.size(); i < end; i++) {
      for (int j = i + 1; j < end; j++) {
        if (Math.random() < 0.5) {
          Vector<T> from = topologicalSortedVectorList.get(i);
          Vector<T> to = topologicalSortedVectorList.get(j);
          from.getTo().add(to);
          to.getFrom().add(from);
        }
      }
    }

    // 检查是否有除了第一个顶点之外入度为0的顶点，如果有的话就从前面的顶点中随机选一个连过来，这个是为了避免有独立的顶点存在
    Random random = new Random();
    for (int i = 1, end = topologicalSortedVectorList.size(); i < end; i++) {
      Vector<T> to = topologicalSortedVectorList.get(i);
      if (to.getFrom().isEmpty()) {
        Vector<T> from = topologicalSortedVectorList.get(random.nextInt(i));
        from.getTo().add(to);
        to.getFrom().add(from);
      }
    }
  }

}
