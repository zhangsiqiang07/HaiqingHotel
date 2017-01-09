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
public class ShopRequest {
    private Context mContext;
    private Handler handler;
    private UserInfo user;

    public ShopRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
        user = new UserInfo(mContext);

    }

    //获取商城列表
    public void shopList() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "";
                String result_data = NetUtil.getResponse(WebAddress.SHOP_LIST, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    Log.e("shoplist is", result_data);
                    msg.what = CommunalInterfaces.SHOP_LIST;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取商城商品详情
    public void goodIntro(final String goodId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&id=" + goodId;
                Log.e("shopId in here ------", goodId);
                String result_data = NetUtil.getResponse(WebAddress.GOOD_INTRO, data);
                Log.e("result = ",result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GOOD_INTRO;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //收藏商品
    public void likeGood(final String goodId, final String name, final String intro) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                user.readData(mContext);
                String data = "&userid=" + user.getUserId() + "&goodsid=" + goodId + "&type=3"
                        + "&title=" + name + "&description="+ intro;
                String result_data = NetUtil.getResponse(WebAddress.LIKE_GOOD, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.LIKE_GOOD;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //取消商品
    public void disLikeGood(final String goodId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                user.readData(mContext);
                String data = "&userid=" + user.getUserId() + "&goodsid=" + goodId + "&type=3";
                String result_data = NetUtil.getResponse(WebAddress.DISLIKE_GOOD, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.DISLIKE_GOOD;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //立即购买商品
    public void buyGoodNow(final String goodId, final String roomName, final String goodNum,
                           final String peoName,final String phoNum,  final String remark,final String money) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                user.readData(mContext);
                if (user.getUserId().equals("")){
                    user.setUserId("0");
                }
                String data = "&userid=" +user.getUserId() + "&goodsid=" + goodId + "&roomname=" + roomName +
                        "&goodnum=" + goodNum +"&peoplename=" + peoName +  "&mobile=" + phoNum + "&remark=" + remark
                        + "&money=" + money;
                String result_data = NetUtil.getResponse(WebAddress.SHOP_BUY_NOW, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.SHOP_BUY_NOW;
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
