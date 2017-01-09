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
public class MainRequest {
    private Context mContext;
    private Handler handler;

    public MainRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
    }


    //登录
    public void login(final String phoNum, final String password) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&phone="+ phoNum + "&password=" + password;
                String result_data = NetUtil.getResponse(WebAddress.LOGIN, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.LOGIN;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                    msg.obj="";
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
                Log.e("result data is", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
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

    //注册
    public void register(final String sex,final String name,final String city,final String email,final String birthday,
                         final String phoNum, final String password, final String verifyCode) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&name=" + name + "&avatar=1234.jpg" + "&phone=" + phoNum + "&password=" + password +
                        "&code=" + verifyCode + "&devicestate=2";
                String result_data = NetUtil.getResponse(WebAddress.REGISTER, data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.REGISTER;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    //修改密码
    public void amendPass() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&phone=15853505932" + "&code=876864" + "&password=12345";
                String result_data = NetUtil.getResponse(WebAddress.SEND_CODE, data);
                Log.e(" password result is ", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
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
}
