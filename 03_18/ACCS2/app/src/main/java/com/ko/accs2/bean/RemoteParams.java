package com.ko.accs2.bean;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class RemoteParams {


	/**
	 * code : 200
	 * msg : success
	 * data : {"count":5,"items":{"temperatureSet":75,"temperature":45,"CO2Set":10,"CO2":30,"humidity":90}}
	 */

	private String code;
	private String msg;
	private DataBean data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public static class DataBean {
		/**
		 * count : 5
		 * items : {"temperatureSet":75,"temperature":45,"CO2Set":10,"CO2":30,"humidity":90}
		 */

		private int count;
		private ItemsBean items;

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public ItemsBean getItems() {
			return items;
		}

		public void setItems(ItemsBean items) {
			this.items = items;
		}

		public static class ItemsBean {
			/**
			 * temperatureSet : 75
			 * temperature : 45
			 * CO2Set : 10
			 * CO2 : 30
			 * humidity : 90
			 */

			private int temperatureSet;
			private int temperature;
			private int CO2Set;
			private int CO2;
			private int humidity;

			public int getTemperatureSet() {
				return temperatureSet;
			}

			public void setTemperatureSet(int temperatureSet) {
				this.temperatureSet = temperatureSet;
			}

			public int getTemperature() {
				return temperature;
			}

			public void setTemperature(int temperature) {
				this.temperature = temperature;
			}

			public int getCO2Set() {
				return CO2Set;
			}

			public void setCO2Set(int CO2Set) {
				this.CO2Set = CO2Set;
			}

			public int getCO2() {
				return CO2;
			}

			public void setCO2(int CO2) {
				this.CO2 = CO2;
			}

			public int getHumidity() {
				return humidity;
			}

			public void setHumidity(int humidity) {
				this.humidity = humidity;
			}
		}
	}
}
