
决策树='策略停滞业务驱动事件级别映射处置方式'
决策树id='12'
决策树描述='策略停滞业务驱动事件级别对应的处置方式'

返回属性 'handlerList'

策略 {
    策略 = '事件级别映射处置'

    规则 {
        规则名 = '级别与处置'
        操作 {
            handlerList = new ArrayList<String>()
            if (eventClass == 'zeroGrade') {
                handlerList.add("DingDingMsg")
            } else if (eventClass == 'firstGrade') {
                handlerList.add("DingDingMsg")
                handlerList.add("yida_flow")
            } else if (eventClass == 'thirdGrade') {
                handlerList.add("DingDingMsg")
                handlerList.add("yida_flow")
            }
        }
    }

}
