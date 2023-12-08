package dsl.params

import dsl.groovy.DslDelegate
import groovy.transform.BaseScript

@BaseScript DslDelegate _

par1 = "1"
strParam(par1, checks = [length: 2])