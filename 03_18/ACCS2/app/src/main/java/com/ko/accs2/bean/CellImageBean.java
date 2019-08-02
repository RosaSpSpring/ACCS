package com.ko.accs2.bean;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
import java.io.Serializable;
import java.util.List;
public class CellImageBean {
	private List<ItemImage> data;

	public List<ItemImage> getData() {
		return data;
	}

	public void setData(List<ItemImage> data) {
		this.data = data;
	}
	public static class ItemImage implements Serializable {
		private int id;
		private int macID;
		private String name;
		private String time;
		private String imageurl;

		public ItemImage(int id, int macID, String name, String time, String imageurl) {
			this.id = id;
			this.macID = macID;
			this.name = name;
			this.time = time;
			this.imageurl = imageurl;
		}

		public ItemImage() {
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getMacID() {
			return macID;
		}

		public void setMacID(int macID) {
			this.macID = macID;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getImageurl() {
			return imageurl;
		}

		public void setImageurl(String imageurl) {
			this.imageurl = imageurl;
		}

		@Override
		public String toString() {
			return "ItemImage{" + "id=" + id + ", macID=" + macID + ", name='" + name + '\'' + ", time='" + time + '\'' + ", imageurl='" + imageurl + '\'' + '}';
		}
	}
}
