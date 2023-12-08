import com.lesofn.runner.GroovyScriptRunner

决策树='安全策略停滞检测决策'
决策树id='10'
决策树描述='用于检测安全策略长时间未发布迭代的情况'

返回属性 '停滞级别'
返回属性 'level'
返回属性 'city'
返回属性 'beanName'

策略 {
    GroovyScriptRunner dataSource = null
    策略名 = '策略停滞检测'
    操作 {
        dataSource = (GroovyScriptRunner)GroovyScriptRunner.getBean("GroovyScriptRunner")
    }

    规则 {
        规则名 = '策略状态监测'
        拒绝 {
            策略状态.startsWith("全量")
        }
    }

    规则 {
        规则名 = '策略上线停滞天数'
        操作 {
            if (p_上线天数 >7) {
                停滞级别 = 'firstGrade'
                level = 'firstGrade'
                city = 'beijing'
                beanName = dataSource.name
            }
        }
        拒绝 {
            p_上线天数 > 7
        }
    }
}
