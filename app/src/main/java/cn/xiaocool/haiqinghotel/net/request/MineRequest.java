package cn.xiaocool.haiqinghotel.net.request;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.constant.WebAddress;

/**
 * Created by wzh on 2016/5/7.
 */
public class MineRequest {
    private Context mContext;
    private Handler handler;
    private UserInfo user;

    public MineRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
        user = new UserInfo(mContext);
    }


    //获取个人信息
    public void myInfor() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                user.readData(mContext);
                String data = "&userid=" + user.getUserId();
                String result_data = NetUtil.getResponse(WebAddress.MY_INFOR, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.MY_INFOR;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取我的房间订单
    public void myRoomOrder() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                user.readData(mContext);
                if (user.getUserId().equals("")){
                    user.setUserId("0");
                }
                String data = "&userid=" + user.getUserId();
                String result_data = NetUtil.getResponse(WebAddress.MINE_ROOM_ORDER, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.MINE_ROOM_ORDER;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取我的餐饮订单
    public void myCateringOrder() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                user.readData(mContext);
                if (user.getUserId().equals("")){
                    user.setUserId("0");
                }
                String data = "&userid=" + user.getUserId();
                String result_data = NetUtil.getResponse(WebAddress.MINE_CATERING_ORDER, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.MINE_CATERING_ORDER;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取我的商城订单
    public void myShopOrder() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                user.readData(mContext);
                String data = "&userid=" + user.getUserId();
                String result_data = NetUtil.getResponse(WebAddress.MINE_SHOP_ORDER, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.MINE_SHOP_ORDER;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取收藏列表
    public void myLikeGood() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                user.readData(mContext);
                String data = "&userid=" + user.getUserId();
                Log.e("myLikeGood", data);
                String result_data = NetUtil.getResponse(WebAddress.MY_LIKE_GOOD, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.MY_LIKE_GOOD;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    //发送验证码
    public void sendCode(final String phoNum) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&phone=" + phoNum;
                String result_data = NetUtil.getResponse(WebAddress.SEND_CODE, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    Log.e("code is", result_data);
                    msg.what = CommunalInterfaces.SEND_CODE;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //验证手机号密码
    public void changePass(final String phoNum, final String code, final String password) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&phone=" + phoNum + "&code=" + code + "&password=" + password;
                String result_data = NetUtil.getResponse(WebAddress.CHANGE_PASS, data);
                Log.e("success", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CHANGE_PASS;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //修改个人资料
    public void changeInfo(final String name, final String sex,final String birthday, final String email, final String city,final String pic) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                Log.e("userid",user.getUserId());
                Log.e("ziduna",name+ sex + email + city);
                String data = "&userid=" + user.getUserId() + "&nicename=" + name + "&sex=" + sex+ "&birthday=" + birthday +
                        "&email=" + email + "&city=" + city+ "&avatar=" + pic;
                String result_data = NetUtil.getResponse(WebAddress.CHANGE_INFO, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CHANGE_INFO;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //修改个人资料图片
    public void changePicInfo(final String pic) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                Log.e("userid",user.getUserId());
                String data = "&userid=" + user.getUserId() + "&avatar=" + pic;
                String result_data = NetUtil.getResponse(WebAddress.CHANGE_INFO, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CHANGE_IMGINFO;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //取消订单
    public void cancelorder(final String orderid ){
        new Thread(){
            Message msg = Message.obtain();
            public void run(){
                Log.e("userid",user.getUserId());
                String data =  "&userid=" + user.getUserId() + "&orderid=" + orderid;
                String result_data = NetUtil.getResponse(WebAddress.CANCEL_ORDER,data);
                Log.e("result_data = ",result_data);
                Log.e("删除的订单是：",orderid);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CANCEL_ORDER;
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
