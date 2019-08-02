package com.ko.accs2.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.List;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 * 云端数据bean
 */
public class CloudDataBean {

    private List<ItemData> data;

    public List<ItemData> getData() {
        return data;
    }

    public void setData(List<ItemData> data) {
        this.data = data;
    }

    public static class ItemData implements Serializable {
        private int id ;
        private int toid;
        private String status;
        private String serial_num;
        private String cellname;
        private String submission;
        private String batch_num;
        private String cultur_time;
        private String passage_time;
        private String change_liquid_time;
        private String cellnum;
        private String cell_coverage;
        private String passage_ratio;
        private String remark;
        private int mid;

		public String getPassage_ratio() {
            return passage_ratio;
        }

        public void setPassage_ratio(String passage_ratio) {
            this.passage_ratio = passage_ratio;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getToid() {
            return toid;
        }

        public void setToid(int toid) {
            this.toid = toid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSerial_num() {
            return serial_num;
        }

        public void setSerial_num(String serial_num) {
            this.serial_num = serial_num;
        }

        public String getCellname() {
            return cellname;
        }

        public void setCellname(String cellname) {
            this.cellname = cellname;
        }

        public String getSubmission() {
            return submission;
        }

        public void setSubmission(String submission) {
            this.submission = submission;
        }

        public String getBatch_num() {
            return batch_num;
        }

        public void setBatch_num(String batch_num) {
            this.batch_num = batch_num;
        }

        public String getCultur_time() {
            return cultur_time;
        }

        public void setCultur_time(String cultur_time) {
            this.cultur_time = cultur_time;
        }

        public String getPassage_time() {
            return passage_time;
        }

        public void setPassage_time(String passage_time) {
            this.passage_time = passage_time;
        }

        public String getChange_liquid_time() {
            return change_liquid_time;
        }

        public void setChange_liquid_time(String change_liquid_time) {
            this.change_liquid_time = change_liquid_time;
        }

        public String getCellnum() {
            return cellnum;
        }

        public void setCellnum(String cellnum) {
            this.cellnum = cellnum;
        }

        public String getCell_coverage() {
            return cell_coverage;
        }

        public void setCell_coverage(String cell_coverage) {
            this.cell_coverage = cell_coverage;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }


        @Override
        public String toString() {
            return "ItemData{" +
                    "id=" + id +
                    ", toid=" + toid +
                    ", status='" + status + '\'' +
                    ", serial_num='" + serial_num + '\'' +
                    ", cellname='" + cellname + '\'' +
                    ", submission='" + submission + '\'' +
                    ", batch_num='" + batch_num + '\'' +
                    ", cultur_num='" + cultur_time + '\'' +
                    ", passage_time='" + passage_time + '\'' +
                    ", change_liquid_time='" + change_liquid_time + '\'' +
                    ", cellnum='" + cellnum + '\'' +
                    ", cell_coverage='" + cell_coverage + '\'' +
                    ", passage_ratio='" + passage_ratio + '\'' +
                    ", remark='" + remark + '\'' +
                    ", mid=" + mid +
                    '}';
        }


    }



}