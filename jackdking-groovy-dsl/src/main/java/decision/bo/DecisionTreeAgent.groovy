package decision.bo

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import decision.constant.DSLConstant
import decision.service.DecisionTreeContext
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer

class DecisionTreeAgent extends BaseAgent{

  String 决策树名
  String 决策树id
  String 决策树描述

  /**
   * 接口返回属性集
   */
  @Lazy Set<String> returnAttrs = new LinkedHashSet<>()

  /**
   * 全局决策内自定义函数
   */
  @Lazy Map<String, Closure> globalFunctions = new HashMap<>()

  DecisionTreeAgent 返回变量(String 变量名) {
    if (!变量名) {
      throw new IllegalArgumentException("变量名不能为空")
    }
    returnAttrs.add(变量名);
    this
  }

  DecisionTreeAgent 决策参数(String 属性名, Object 值) {
    if (!属性名) throw new IllegalArgumentException("属性名 不能为空")
    attrs.put(属性名, 值)
    this
  }

  DecisionTreeAgent 函数定义(String 函数名, Closure 函数) {
    if (!函数名) throw new IllegalArgumentException("函数名 不能为空")
    if (!函数) throw new IllegalArgumentException("函数 不能为空")
    globalFunctions.put(函数名, 函数)
    this
  }

  void 操作(Closure 操作) {
    nodes << Tuple2.tuple(DSLConstant.Operate, { DecisionTreeContext ctx ->
      def cl = 操作.rehydrate(ctx.data, 操作, this)
      cl.resolveStrategy = Closure.DELEGATE_FIRST
      cl()
    })
  }

  static DecisionTreeAgent of(@DelegatesTo(DecisionTreeAgent) Closure cl) {
    def p = new DecisionTreeAgent()
    def code = cl.rehydrate(p, cl, this)
    code.resolveStrategy = Closure.DELEGATE_FIRST
    code()
    return p
  }

  /**
   * 根据dsl字符串创建 DecisionTreeAgent 对象
   * @param dsl dsl 字符串
   * @return DecisionTreeAgent
   */
  static DecisionTreeAgent of(String dsl) {
    Binding binding = new Binding()
    def config = new CompilerConfiguration()
    def icz = new ImportCustomizer()
    config.addCompilationCustomizers(icz)
    icz.addImports(DecisionTreeAgent.class.name, JSON.class.name, JSONObject.class.name)
    def shell = new GroovyShell(Thread.currentThread().contextClassLoader, binding, config)
    shell.evaluate("decision.bo.DecisionTreeAgent.of{$dsl}")
  }

  @Override
  String name() {
    return 决策树名
  }

}
