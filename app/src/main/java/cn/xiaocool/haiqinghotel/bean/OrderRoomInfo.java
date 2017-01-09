package cn.xiaocool.haiqinghotel.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/26.
 */
public class OrderRoomInfo implements Serializable {
    private String status;
    private List<OrderRoomData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderRoomData> getData() {
        return data;
    }

    public void setData(List<OrderRoomData> data) {
        this.data = data;
    }


    public static class OrderRoomData implements Serializable {
       /* "id": "22",
                "hot": "0",
                "name": "A座商务标准间",
                "price": "0",
                "oprice": "980",
                "limit": "0",
                "count": null,
                "daynum": null,
                "daysurplus": null,
                "surplus": null,
                "floor": "3-7楼",
                "acreage": "36平米",
                "bedsize": "双床",
                "repast": "2",
                "network": "Wi-Fi和宽带",
                "window": "大窗",
                "peoplenumber": "",
                "bathroom": "独立卫浴",
                "picture": "./data/product_img/20160526162554.jpg",
                "showid": "0",
                "time": "1464251247",
                "adtitle": "",
                "status": "1",
                "racking": "0",
                "photolist": [],
                "pricelist": [],
                "allmoney": 0,
                "realcount": 0*/

        private String id;
        private String name;
        private String price;
        private String oprice;
        private String floor;
        private String acreage;
        private String bedsize;
        private String repast;
        private String network;
        private String window;
        private String peoplenumber;
        private String bathroom;
        private String picture;
        private String showid;
        private String time;
        private String adtitle;
        private ArrayList<PriceInfo> pricelist;


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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOprice() {
            return oprice;
        }

        public void setOprice(String oprice) {
            this.oprice = oprice;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getAcreage() {
            return acreage;
        }

        public void setAcreage(String acreage) {
            this.acreage = acreage;
        }

        public String getBedsize() {
            return bedsize;
        }

        public void setBedsize(String bedsize) {
            this.bedsize = bedsize;
        }

        public String getRepast() {
            return repast;
        }

        public void setRepast(String repast) {
            this.repast = repast;
        }

        public String getNetwork() {
            return network;
        }

        public void setNetwork(String network) {
            this.network = network;
        }

        public String getWindow() {
            return window;
        }

        public void setWindow(String window) {
            this.window = window;
        }

        public String getPeoplenumber() {
            return peoplenumber;
        }

        public void setPeoplenumber(String peoplenumber) {
            this.peoplenumber = peoplenumber;
        }

        public String getBathroom() {
            return bathroom;
        }

        public void setBathroom(String bathroom) {
            this.bathroom = bathroom;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getShowid() {
            return showid;
        }

        public void setShowid(String showid) {
            this.showid = showid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAdtitle() {
            return adtitle;
        }

        public void setAdtitle(String adtitle) {
            this.adtitle = adtitle;
        }

        public ArrayList<PriceInfo> getPricelist() {
            if (pricelist==null){
                pricelist = new ArrayList<>();
            }
            return pricelist;
        }

        public void setPricelist(ArrayList<PriceInfo> pricelist) {
            this.pricelist = pricelist;
        }
    }
}
