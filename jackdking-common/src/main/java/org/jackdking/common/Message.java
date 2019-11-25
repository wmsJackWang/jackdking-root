package org.jackdking.common;

public class Message extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 信息代码
	 */
	private String msgCode = "";

	/**
	 * 备注信息。
	 */
	private String msgRemark = "";

	/**
	 * 根据msgCode查找到的提示信息。
	 */
	private String msgInfo = "";

	public Message() {
	}

	public Message(String msgCode) {
		this.setMsgCode(msgCode.trim());

	}

	/**
	 * 创建信息对象。
	 * <p>
	 * 1、调用setMsgCode(msgCode)方法。<br>
	 * 2、调用setMsgRemark(msgRemark)方法。
	 * 
	 * @param msgCode
	 *            错误信息代码
	 * @param msgRemark
	 *            错误信息备注
	 */
	public Message(String msgCode, String msgRemark) {
		this.setMsgCode(StringUtils.trimStr(msgCode));
		this.setMsgRemark(StringUtils.trimStr(msgRemark));

	}

	/**
	 * 获取提示信息。
	 * <p>
	 * 返回的信息。 返回信息格式为：msgCode：msgInfo【msgRemark】。
	 * 
	 * @return String
	 */
	public String getMsgInfo() {
		String returnMsg = "";
		if (msgInfo != null && !msgInfo.equals("")) {
			returnMsg += msgCode + "：" + msgInfo;
		}
		if (msgRemark != null && !msgRemark.equals("")) {
			returnMsg += "【" + msgRemark + "】";
		}
		return returnMsg;
	}

	@Override
	public String getMessage() {
		return this.getMsgInfo();
	}

	/**
	 * 设置提示信息。
	 * <p>
	 * 1、查询msgCode在系统中是否定义。若未定义则throw
	 * RuntimeException("未定义错误代码["+msgCode+"]，请定义后再使用。")。<br>
	 * 2、查询msgCode所对应的提示信息。<br>
	 * a)直接在数据库中查询。<br>
	 * b)程序启动时初始化，然后在初始化的对象中获得。<br>
	 * 3、this.msgCode = msgCode。 this.msgInfo = 2中查询的信息。
	 * 
	 * @param msgCode
	 */
	public void setMsgCode(String msgCode) {
		// this.msgInfo = ErrorCode.getErrorMsg(msgCode.trim());
		this.msgInfo = msgCode;
		this.msgCode = msgCode.trim();
	}

	/**
	 * 设置备注信息
	 * 
	 * @param msgRemark
	 *            备注信息
	 * 
	 */
	public void setMsgRemark(String msgRemark) {
		this.msgRemark = msgRemark.trim();
	}

	public String getMsgCode() {
		return msgCode;
	}

	public String getMsgRemark() {
		return msgRemark;
	}

	public boolean equals(Object e) {
		if (!(e instanceof Message)) {
			return false;
		}
		Message e1 = (Message) e;
		if (this.getMsgCode().equals(e1.getMsgCode())
				&& this.getMsgRemark().equals(e1.getMsgRemark())) {
			return true;
		}
		return false;
	}

	public int hashCode() {
		return this.msgRemark.hashCode() + this.msgRemark.hashCode();
	}
}