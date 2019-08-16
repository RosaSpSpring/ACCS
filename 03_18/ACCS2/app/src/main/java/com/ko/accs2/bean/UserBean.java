package com.ko.accs2.bean;


/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class UserBean {

    /**
     * code : 200
     * msg : success
     * data : {"id":11,"name":"123","pass":"123","content":"123","comID":1}
     */

    public int code;
    public String msg;
    public DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 11
         * name : 123
         * pass : 123
         * content : 123
         * comID : 1
         */

        public int id;
        public String name;
        public String pass;
        public String content;
        public int comID;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getComID() {
            return comID;
        }

        public void setComID(int comID) {
            this.comID = comID;
        }
    }
}
