package cn.xiaocool.haiqinghotel.main;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.request.MainRequest;
import cn.xiaocool.haiqinghotel.ui.MyDatePickerDialog;
import cn.xiaocool.haiqinghotel.utils.IntentUtils;

/**
 * Created by wzh on 2016/5/7.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    private RelativeLayout btnBack;
    private Button btnSendCode;
    private Button btnRegister;
    private EditText edName, etCity, etEmail, edPhoNum, edCode, edPassword;
    private TextView tvTitle, etBirth;
    private String phoneNumber, code;
    private CheckBox cbAgree;
    private static int second;
    private RadioGroup radioGroup;
    private String sex;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.SEND_CODE:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            Toast.makeText(RegisterActivity.this, "发送验证码成功！", Toast.LENGTH_SHORT).show();
                            String data = jsonObject.getString("data");
                            JSONObject joCode = new JSONObject(data);
                            code = joCode.getString("code");
                            Log.e("code is ", code);
                        } else {
                            Toast.makeText(RegisterActivity.this, "验证码获取失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case CommunalInterfaces.BTN_UNTOUCH:
                    //按钮不可用，开始倒计时
                    btnSendCode.setClickable(false);
                    btnSendCode.setText(second + "s");
                    break;
                case CommunalInterfaces.BTN_TOUCH:
                    btnSendCode.setText("发送验证码");
                    second = 30;
                    btnSendCode.setClickable(true);

                    break;
                case CommunalInterfaces.REGISTER:
                    JSONObject registerObj = (JSONObject) msg.obj;
                    try {
                        String regStatus = registerObj.getString("status");
                        String userNum = registerObj.getString("data");
                        Log.e("status is this", regStatus);
                        if (regStatus.equals("success")) {

                            IntentUtils.getIntent(RegisterActivity.this, LoginActivity.class);
                        } else {
                            Toast.makeText(RegisterActivity.this, userNum, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_register);
        initView();
    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        radioGroup = (RadioGroup) findViewById(R.id.main_register_rg_sex);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rd_man) {
                    sex = "男";
                } else if (checkedId == R.id.rd_woman) {
                    sex = "女";
                }

            }
        });
        btnSendCode = (Button) findViewById(R.id.send_code);
        btnSendCode.setOnClickListener(this);
        btnRegister = (Button) findViewById(R.id.register);
        btnRegister.setOnClickListener(this);
//        cbAgree = (CheckBox) findViewById(R.id.main_cb_agree);
//        cbAgree.setOnClickListener(this);
        edName = (EditText) findViewById(R.id.main_ed_name);
        etCity = (EditText) findViewById(R.id.main_et_city);
        etEmail = (EditText) findViewById(R.id.main_et_email);
        etBirth = (TextView) findViewById(R.id.main_et_birthday);
        etBirth.setOnClickListener(this);
        edPhoNum = (EditText) findViewById(R.id.main_ed_phonum);
        edCode = (EditText) findViewById(R.id.main_ed_code);
        edPassword = (EditText) findViewById(R.id.main_ed_password);
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText(this.getString(R.string.register));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.send_code:
                sendCode();
                break;
            case R.id.register:
                register();
                break;
            case R.id.main_et_birthday:
                showDatePicker();
                break;

        }
    }

    //时间选择控件
    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        Date myData = new Date();
        cal.setTime(myData);

        //获取系统的时间
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int minute = cal.get(Calendar.MINUTE);
        final int second = cal.get(Calendar.SECOND);

        Log.e("MONTH", "year" + year);
        Log.e("MONTH", "month" + month);
        Log.e("MONTH", "day" + day);
        Log.e("MONTH", "hour" + hour);
        Log.e("MONTH", "minute" + minute);
        Log.e("MONTH", "second" + second);

        MyDatePickerDialog dlg = new MyDatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e("MONTH", "monthOfYear" + monthOfYear);
                monthOfYear += 1;//monthOfYear 从0开始

                String data = year + "-" + monthOfYear + "-" + dayOfMonth;
                etBirth.setText(data);
//                        String data_new = dataOne(data + "-" + hour + "-" + minute + "-" + second);

                //时分秒用0代替
                /*String data_new = dataOne(data + "-" + hour + "-" + minute + "-" + second);
                Log.e("--444444---", data_new);
                begintime = data_new;*/

            }
        }, year, month, day);
        dlg.show();

    }


    private void sendCode() {
        phoneNumber = edPhoNum.getText().toString();
        Log.e("phone number is", phoneNumber);
        second = 30;
        if (phoneNumber.length() == 11) {
            new MainRequest(this, handler).sendCode(phoneNumber);
            new Thread() {
                public void run() {
                    for (int i = 0; i < 30; i++) {
                        try {
                            handler.sendEmptyMessage(CommunalInterfaces.BTN_UNTOUCH);
                            Thread.sleep(1000);
                            second--;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendEmptyMessage(CommunalInterfaces.BTN_TOUCH);
                }
            }.start();
        } else {
            Toast.makeText(RegisterActivity.this, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
        }
    }


    private void register() {
        String verifyCode = edCode.getText().toString();
        String userName = edName.getText().toString();
        String userCity = etCity.getText().toString();
        String userEmail = etCity.getText().toString();
        String userBirth = etCity.getText().toString();
        String userPass = edPassword.getText().toString();
        phoneNumber = edPhoNum.getText().toString();
        if (verifyCode.equals(code)) {
            if ((userName.length() != 0) && (userPass.length() != 0)) {
                if (sex == null || sex == "") {
                    Toast.makeText(RegisterActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
                } else {
                    new MainRequest(this, handler).register(sex, userName, userCity, userEmail, userBirth, phoneNumber, userPass, verifyCode);
                }

            } else {
                Toast.makeText(RegisterActivity.this, "姓名或密码不能为空", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(RegisterActivity.this, "请输入正确的验证码！", Toast.LENGTH_SHORT).show();
        }
    }
}
