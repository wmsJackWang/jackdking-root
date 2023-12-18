package dsl.params

import groovy.transform.BaseScript

import decision.bo.DslAgent

@BaseScript DslAgent _

par1 = "0"
longParam(par1, checks = [min: 1, max: 3])
