package dsl.params

import dsl.groovy.DslDelegate
import groovy.transform.BaseScript

@BaseScript DslDelegate _

par1 = "0"
longParam(par1, checks = [min: 1, max: 3])