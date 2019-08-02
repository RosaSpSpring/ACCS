package com.ko.accs2.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class BoundedDeviceBean {
	private List<ItemBoundedDevice> data;

	public List<ItemBoundedDevice> getData() {
		return data;
	}

	public void setData(List<ItemBoundedDevice> data) {
		this.data = data;
	}

	public static class ItemBoundedDevice implements Serializable {
		private int id;
		private String uuid;
		private String content;
		private int typeID;
		private int comID;


		public ItemBoundedDevice(int id, String uuid, String content, int typeID, int comID) {
			this.id = id;
			this.uuid = uuid;
			this.content = content;
			this.typeID = typeID;
			this.comID = comID;
		}

		public ItemBoundedDevice() {
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getTypeID() {
			return typeID;
		}

		public void setTypeID(int typeID) {
			this.typeID = typeID;
		}

		public int getComID() {
			return comID;
		}

		public void setComID(int comID) {
			this.comID = comID;
		}

		@Override
		public String toString() {
			return "ItemBoundedDevice{" + "id=" + id + ", uuid=" + uuid + ", content='" + content + '\'' + ", typeID=" + typeID + ", comID=" + comID + '}';
		}
	}
}
