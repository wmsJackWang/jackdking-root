package dsl.http

import dsl.groovy.DslDelegate
import groovy.transform.BaseScript

@BaseScript DslDelegate _

def res = http {
    method "GET"
    params "arg1=123&arg2=456"
    url "https://lesofn.com"
}
println res
res