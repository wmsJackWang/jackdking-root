package dsl.params

import dsl.groovy.DslDelegate
import groovy.transform.BaseScript

@BaseScript DslDelegate _

par1 = "1"
strParam(par1, checks = [length: 1])
par1 = "aaa@qq.com"
strParam(par1, checks = [regex: "^(\\w+)*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+\$"])