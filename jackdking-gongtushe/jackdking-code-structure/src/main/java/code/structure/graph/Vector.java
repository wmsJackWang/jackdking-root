package code.structure.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName Vector
 * @Description TODO
 * @Author jackdking
 * @Date 27/12/2022 2:30 下午
 * @Version 2.0
 **/
public class Vector<T> {

  private T name;
  private List<Vector<T>> from;
  private List<Vector<T>> to;

  public Vector(T name) {
    this.name = name;
    this.from = new ArrayList<>();
    this.to = new ArrayList<>();
  }

  public T getName() {
    return name;
  }

  public void setName(T name) {
    this.name = name;
  }

  public List<Vector<T>> getFrom() {
    return from;
  }

  public List<Vector<T>> getTo() {
    return to;
  }
}
