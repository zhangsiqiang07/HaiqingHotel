package cn.xiaocool.haiqinghotel.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 16/5/23.
 */
public class Facility implements Serializable {
    /**
     * id : 16
     * photo:
     * content:
     * title:
     * excerpt:
     * status:
     * type:
     * create_time:
     * showid :
     */
    private String status;
    private List<FacilityData> data;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public List<FacilityData> getData() {
        return data;
    }
    public void setData(List<FacilityData> data) {
        this.data = data;
    }

    public static class FacilityData implements Serializable {
        /**
         * id : 16
         * photo:
         * content:
         * title:
         * excerpt:
         * status:
         * type:
         * create_time:
         * showid :
         */
        private String id;
        private String title;
        private String photo;
        private String content;
        private String excerpt;
        private String status;
        private String type;
        private String create_time;
        private String showid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getExcerpt() {
            return excerpt;
        }

        public void setExcerpt(String excerpt) {
            this.excerpt = excerpt;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getShowid() {
            return showid;
        }

        public void setShowid(String showid) {
            this.showid = showid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
    @Override
    public String toString() {
        return "Facility{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}