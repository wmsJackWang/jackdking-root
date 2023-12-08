package dsl.params

import dsl.groovy.DslDelegate
import groovy.transform.BaseScript

@BaseScript DslDelegate _

par1 = "1"
longParam(par1, checks = [min: 2, max: 3])