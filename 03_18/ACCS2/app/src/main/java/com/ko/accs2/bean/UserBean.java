package com.ko.accs2.bean;


import java.util.List;
/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class UserBean {
    private List<ItemUserBean> data;

    public List<ItemUserBean> getData() {
        return data;
    }

    public void setData(List<ItemUserBean> data) {
        this.data = data;
    }

    public static class ItemUserBean {
        private int id;
        private String username;
        private String password;

        public ItemUserBean(int id, String username, String password) {
            this.id = id;
            this.username = username;
            this.password = password;
        }

        public ItemUserBean() {

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "ItemUserBean{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
