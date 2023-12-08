
决策树='策略停滞业务驱动事件升级配置'
决策树id='11'
决策树描述='策略停滞业务用于对驱动事件进行升级'

返回属性 'eventLevel'

策略 {
    策略 = '策略停滞业务数据驱动事件升级'

    规则 {
        规则名 = 'thirdGrade级别检测规则'
        操作 {
            if (事件状态 == 'waiting_deal' && days > 30) {
                eventLevel = 'thirdGrade'
            }
        }
        拒绝 {
            days > 30
        }
    }


    规则 {
        规则名 = 'secondGrade级别检测规则'
        操作 {
            if (事件状态 == 'waiting_deal' && days > 15) {
                eventLevel = 'secondGrade'
            }
        }
        拒绝 {
            days > 15
        }
    }

    规则 {
        规则名 = 'firstGrade级别检测规则'
        操作 {
            if (事件状态 == 'waiting_deal' && days > 7) {
                eventLevel = 'firstGrade'
            }
        }
        拒绝 {
            days > 7
        }
    }
}
