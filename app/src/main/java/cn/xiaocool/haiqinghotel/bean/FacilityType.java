package cn.xiaocool.haiqinghotel.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 16/5/23.
 */
public class FacilityType implements Serializable {

    private String status;
    private List<FacilityTypeData> data;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public List<FacilityTypeData> getData() {
        return data;
    }
    public void setData(List<FacilityTypeData> data) {
        this.data = data;
    }
    public static class FacilityTypeData implements Serializable {
        /*
        *id:
        *name:
        * picture:
        * time:
        * showid:
        * */
        private String id;
        private String title;
        private String photo;
        private String create_time;
        private String excerpt;
        private String type;

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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getExcerpt() {
            return excerpt;
        }

        public void setExcerpt(String excerpt) {
            this.excerpt = excerpt;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
