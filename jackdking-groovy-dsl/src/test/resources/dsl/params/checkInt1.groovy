package dsl.params

import com.alibaba.fastjson.JSONObject
import dsl.groovy.DslDelegate
import groovy.transform.BaseScript

@BaseScript DslDelegate _

par1 = "1"
int ret_1 = intParam(par1, checks = [min: 1, max: 3])

JSONObject result = new JSONObject()
result.put("ret_1", ret_1)

int ret_2 = intParam("34")
result.put("ret_2", ret_2)
result
