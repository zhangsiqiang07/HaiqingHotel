package cn.xiaocool.haiqinghotel.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.haiqinghotel.bean.AppInfo;
import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.net.constant.WebAddress;
import cn.xiaocool.haiqinghotel.utils.NetBaseUtils;


/**
 * Created by mac on 16/5/9.
 */
public class UserRequest {
    private Context mContext;
    private Handler handler;
    private UserInfo user;
    private AppInfo appInfo;
    private String TAG = "UserRequest.class";

    public UserRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
        user = new UserInfo();
        user.readData(mContext);
        appInfo=new AppInfo();
        appInfo.readData(mContext);
    }

    public UserRequest(Context context) {
        super();
        this.mContext = context;
        user = new UserInfo();
        user.readData(mContext);
        appInfo=new AppInfo();
        appInfo.readData(mContext);
    }

    /*
    *上传图片
    */

    public void uploadavatar(final String img, final int KEY){
        new Thread(){
            Message msg = Message.obtain();
            public void run(){
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("upfile", img));
                String result = NetBaseUtils.getResponseForImg(WebAddress.UPLOADAVATAR,params,mContext);
                Log.e("头像路径", img);
                Log.e("result= ",result);
                msg.what = KEY;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }.start();
    }

    public void savepersonalinfo(final String userid ,final String nicename , final String city , final int KEY){
        new Thread(){
            Message msg = Message.obtain();
            public  void run(){

            }
        }.start();
    }
}
