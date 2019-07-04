package com.ko.testtrees.bean;

import java.util.List;

/**
 * @author lxm
 * @version 2019/7/4-9:40
 * @des ${TODO}
 * @updateDes ${TODO}
 * @updateAuthor $Author$
 */
public class CurData {

	/**
	 * msg : success
	 * data : [{"id":"1","name":"hela","daici":"P1","caozuo":"结束","pingshu":"17","date":"2019/06/14","operator":"杨","node":0},{"id":"2","name":"hela","daici":"P1","caozuo":"结束","pingshu":"3","date":"2019/06/16","operator":"杨","node":1},{"id":"3","name":"hela","daici":"P2","caozuo":"结束","pingshu":"5","date":"2019/06/17","operator":"杨","node":1},{"id":"4","name":"jczgxb","daici":"P1","caozuo":"结束","pingshu":"5","date":"2019/06/11","operator":"杨","node":0},{"id":"5","name":"jczgxb","daici":"P2","caozuo":"结束","pingshu":"5","date":"2019/06/18","operator":"杨","node":1},{"id":"6","name":"zhongliucell","daici":"P1","caozuo":"结束","pingshu":"2","date":"2019/06/13","operator":"杨","node":0},{"id":"7","name":"zhongliucell","daici":"P2","caozuo":"结束","pingshu":"2","date":"2019/06/13","operator":"杨","node":1},{"id":"8","name":"hela","daici":"P1","caozuo":"中间","pingshu":"17","date":"2019/06/14","operator":"杨","node":1},{"id":"9","name":"maonangcell","daici":"P2","caozuo":"结束","pingshu":"5","date":"2019/06/19","operator":"杨","node":0},{"id":"10","name":"maonangcell","daici":"P2","caozuo":"结束","pingshu":"5","date":"2019/06/26","operator":"杨","node":1},{"id":"11","name":"hela","daici":"P2","caozuo":"中间","pingshu":"5","date":"2019/06/29","operator":"杨","node":1},{"id":"12","name":"shenjingcell","daici":"P1","caozuo":"传代","pingshu":"4","date":"2019/06/19","operator":"杨","node":0},{"id":"13","name":"shenjingcell","daici":"P2","caozuo":"新建","pingshu":"4","date":"2019/06/19","operator":"杨","node":1}]
	 */

	private String msg;
	private List<DataBean> data;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<DataBean> getData() {
		return data;
	}

	public void setData(List<DataBean> data) {
		this.data = data;
	}

	public static class DataBean {
		/**
		 * id : 1
		 * name : hela
		 * daici : P1
		 * caozuo : 结束
		 * pingshu : 17
		 * date : 2019/06/14
		 * operator : 杨
		 * node : 0
		 */

		private String id;
		private String name;
		private String daici;
		private String caozuo;
		private String pingshu;
		private String date;
		private String operator;
		private int node;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDaici() {
			return daici;
		}

		public void setDaici(String daici) {
			this.daici = daici;
		}

		public String getCaozuo() {
			return caozuo;
		}

		public void setCaozuo(String caozuo) {
			this.caozuo = caozuo;
		}

		public String getPingshu() {
			return pingshu;
		}

		public void setPingshu(String pingshu) {
			this.pingshu = pingshu;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public int getNode() {
			return node;
		}

		public void setNode(int node) {
			this.node = node;
		}
	}
}
