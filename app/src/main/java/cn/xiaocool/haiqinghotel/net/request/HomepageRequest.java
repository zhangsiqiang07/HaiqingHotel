package cn.xiaocool.haiqinghotel.net.request;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.constant.WebAddress;

/**
 * Created by wzh on 2016/5/7.
 */
public class HomepageRequest {
    private Context mContext;
    private Handler handler;

    public HomepageRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
    }

    //获取首页促销列表
    public void onsaleList() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "";
                String result_data = NetUtil.getResponse(WebAddress.ONSALE_LIST, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.ONSALE_LIST;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //获取首页幻灯片
    public void onPicList() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "";
                String result_data = NetUtil.getResponse(WebAddress.ONPIC_LIST, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.ONPIC_LIST;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //获取房间详情
    public void roomDetails(final String id) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&id=" + id;
                String result_data = NetUtil.getResponse(WebAddress.ROOM_DETAILS, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.ROOM_DETAILS;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取房间详情
    public void roomPriceDetails(final String beginTime,final String endTime,final String id,final String userid) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&begindate=" + beginTime + "&enddate=" + endTime + "&id=" + id + "&userid=" + userid;
                Log.e("roomPriceDetails",data);
                String result_data = NetUtil.getResponse(WebAddress.ROOM_DETAILS, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.ROOM_DETAILS;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //发送立即预定房间请求
    public void reserveNow(final String userId, final String goodId, final String beginTime, final String endTime,
                           final String arriveTime, final String goodNum, final String peoNum, final String phoNum,
                           final String remark,final String name,final String money) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + userId + "&goodsid=" + goodId + "&begintime=" + beginTime + "&endtime="
                        + endTime + "&arrivetime=" + arriveTime + "&goodnum=" + goodNum + "&peoplenum=" + peoNum +
                        "&mobile=" + phoNum + "&remark=" + remark +"&peoplename=" + name+"&money="+money;
                Log.e("data = " ,data);
                String result_data = NetUtil.getResponse(WebAddress.RESERVE_ROOM_NOW, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.RESERVE_ROOM_NOW;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    //获取餐饮信息
    public void cateringDetails(final String id) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&id=" + id;
                String result_data = NetUtil.getResponse(WebAddress.CATERING_DETAILS, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CATERING_DETAILS;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //立即预定餐饮
    public void reserveCatering(final int userId, final String goodId,final String repastTime,final String peoplename,
                                final String goodNum, final String phoNum, final String remark,
                                final String price,final String startdate,final String roomName,final String money) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + userId + "&goodsid=" + goodId + "&repasttime=" + repastTime
                        + "&peoplename=" + peoplename+ "&goodnum=" + goodNum + "&mobile=" + phoNum
                        + "&remark=" + remark + "&money=" + price+ "&startdate=" + startdate +"&roomname=" +roomName+"&money="+money;
                String result_data = NetUtil.getResponse(WebAddress.RESERVE_CATERING, data);
                Log.e("result data", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.RESERVE_CATERING;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取首页促销客房列表
    public void homeReserveRoom(final String begindate,final String enddate, final String userid) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&begindate=" + begindate + "&enddate=" + enddate + "&userid=" + userid;
                Log.e("homeReserveRoom",data);
                String result_data = NetUtil.getResponse(WebAddress.HOME_RESERVE_ROOM, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.HOME_RESERVE_ROOM;
                    msg.obj = obj;
                    Log.e("HOME_RESERVE_ROOM","===========");
                    Log.e("HOME_RESERVE_ROOM",obj.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //搜索首页促销客房列表
    public void searchReserveRoom(final String searchName) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&searchname=" + searchName;
                String result_data = NetUtil.getResponse(WebAddress.HOME_RESERVE_ROOM, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.HOME_RESERVE_ROOM;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


}
