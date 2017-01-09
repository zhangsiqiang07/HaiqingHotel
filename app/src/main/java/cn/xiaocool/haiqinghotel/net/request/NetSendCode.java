package cn.xiaocool.haiqinghotel.net.request;

import android.util.Log;

import cn.xiaocool.haiqinghotel.net.constant.WebAddress;

/**
 * Created by wzh on 2016/5/22.
 */
public class NetSendCode {
    //发送验证码
    public String sendCode(final String phoNum) {
        String url = WebAddress.SEND_CODE;
        String data = "phone="+phoNum;
        String result = "";
        result = NetUtil.getResponse(url,data);
        Log.e("result code is",result);
        return result;
    }

    //修改密码
    public String changePass(final String phoNum,final String code,final String password) {
        String url = WebAddress.CHANGE_PASS;
        String data = "&phone=" + phoNum + "&code=" + code + "&password=" + password;
        String result = "";
        result = NetUtil.getResponse(url,data);
        return result;
    }
}
