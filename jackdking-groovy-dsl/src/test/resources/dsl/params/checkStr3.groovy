package dsl.params

import groovy.transform.BaseScript

import decision.bo.DslAgent

@BaseScript DslAgent _

par1 = "qq.com"
strParam(par1, checks = [regex: "^(\\w+)*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+\$"])
