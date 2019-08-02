package com.ko.accs2.bean;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class RemoteParamsRedis {
	private int code;
	private String msg;
	private int data;

	public RemoteParamsRedis() {
	}

	public RemoteParamsRedis(int code, String msg, int data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RemoteParamsRedis{" + "code=" + code + ", msg='" + msg + '\'' + ", data=" + data + '}';
	}
}
