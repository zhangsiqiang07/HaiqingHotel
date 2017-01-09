package cn.xiaocool.haiqinghotel.main.mine;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.request.MineRequest;
import cn.xiaocool.haiqinghotel.net.request.NetSendCode;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;

/**
 * Created by wzh on 2016/5/22.
 */
public class ChangePasswordActivity extends Activity implements View.OnClickListener {
    private TextView tvTitle;
    private Button btnConfirm, btnSendCode;
    private RelativeLayout btnExit;
    private String resultData;
    private EditText etPhoNum, etCode, etPass0, etPass1;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    JSONObject jsonObject0 = null;
                    try {
                        jsonObject0 = new JSONObject(resultData);
                        String status0 = jsonObject0.getString("status");
                        if (status0.equals("success")) {
                            Toast.makeText(ChangePasswordActivity.this, "发送验证码成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "发送验证码失败！", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    JSONObject jsonObject1 = null;
                    try {
                        jsonObject1 = new JSONObject(resultData);
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")){
                            Toast.makeText(ChangePasswordActivity.this, "修改成功！", Toast.LENGTH_LONG).show();
                            finish();
                        }else{
                            Toast.makeText(ChangePasswordActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mine_change_password);
        initView();
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText(this.getString(R.string.mine_title_change_password));
        btnExit = (RelativeLayout) findViewById(R.id.btn_back);
        btnExit.setOnClickListener(this);
        btnSendCode = (Button) findViewById(R.id.mine_btn_send_code);
        btnSendCode.setOnClickListener(this);
        etPhoNum = (EditText) findViewById(R.id.mine_et_phoNUm);
        btnConfirm = (Button) findViewById(R.id.mine_btn_confirm_change);
        btnConfirm.setOnClickListener(this);
        etCode = (EditText) findViewById(R.id.mine_et_code);
        etPass0 = (EditText) findViewById(R.id.mine_et_pass0);
        etPass1 = (EditText) findViewById(R.id.mine_et_pass1);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.mine_btn_confirm_change:
                if (etPass0.length() != 0 && etPass1.length() != 0 &&
                        etCode.length() != 0 && etPhoNum.length() != 0) {
                    final String phoNum = etPhoNum.getText().toString();
                    final String pass0 = etPass0.getText().toString();
                    String pass1 = etPass1.getText().toString();
                    final String code = etCode.getText().toString();
                    if (pass0.equals(pass1)) {
                        if (code.length() == 6) {
                            if (NetUtil.isConnnected(this)) {
//                                new MineRequest(this, handler).changePass(phoNum, code, pass0);
                                new Thread(){
                                    public void run(){
                                        String resultData = new NetSendCode().changePass(phoNum,code,pass0);
                                        handler.sendEmptyMessage(2);
                                        try {
                                            JSONObject jsonObject = new JSONObject(resultData);
                                            String status = jsonObject.getString("status");
                                            if (status.equals("success")){

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }.start();
                            } else {
                                Toast.makeText(this, "请检查网络！", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "请输入正确的验证码！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "请检查密码是否一致！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "输入不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mine_btn_send_code:
                Log.e("enter","enter");
                if ((etPhoNum.length()) != 0) {
                    final String phone = etPhoNum.getText().toString();
                    if (phone.length() == 11) {
                        if (NetUtil.isConnnected(this)) {
//                            new MineRequest(this, handler).sendCode(phone);
                            new Thread(){
                                public void run(){
                                    resultData = new NetSendCode().sendCode(phone);
                                    handler.sendEmptyMessage(1);
                                }
                            }.start();
                        } else {
                            Toast.makeText(this, "请检查网络！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入手机号！", Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }
}
