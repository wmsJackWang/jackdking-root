package dsl.params

import groovy.transform.BaseScript

import decision.bo.DslAgent

@BaseScript DslAgent _
par1 = "1"
strParam(par1, checks = [length: 2])
