package dsl.http

import groovy.transform.BaseScript
import decision.bo.DslAgent

@BaseScript DslAgent _

def res = http {
    method "GET"
    params "arg1=123&arg2=456"
    url "https://lesofn.com"
}
println res
res
