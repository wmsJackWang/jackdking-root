package decision.bo

abstract class BaseAgent {

  /**
   * 自定义规则属性. 例: 自定义id, 描述
   */
  @Lazy Map<String, Object> attrs = new HashMap<>()
  /**
   * 顺序节点: 拒绝, 通过, 人工, 操作, 清除
   * 依次遍历节点执行
   */
  @Lazy List<Tuple2<String, Closure>> nodes = new LinkedList<>()


  /**
   * 名字
   * @return
   */
  abstract String name()
}
