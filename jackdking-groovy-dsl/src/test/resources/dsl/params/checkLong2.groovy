package dsl.params

import groovy.transform.BaseScript

import decision.bo.DslAgent

@BaseScript DslAgent _
par1 = "1"
longParam(par1, checks = [min: 2, max: 3])
