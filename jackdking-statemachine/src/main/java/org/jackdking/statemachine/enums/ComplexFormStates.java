package org.jackdking.statemachine.enums;
public enum ComplexFormStates {
	BLANK_FORM, // 空白表单
	FULL_FORM, // 填写完表单
	CHECK_CHOICE,//表单校验判断
	DEAL_CHOICE,//表单处理校验
	DEAL_FORM,//待处理表单
	CONFIRM_FORM, // 校验完表单
	SUCCESS_FORM,// 成功表单
	FAILED_FORM//失败表单
}