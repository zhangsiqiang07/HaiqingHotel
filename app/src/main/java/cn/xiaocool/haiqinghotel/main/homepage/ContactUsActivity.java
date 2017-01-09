package cn.xiaocool.haiqinghotel.main.homepage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.haiqinghotel.R;

/**
 * Created by wzh on 2016/4/28.
 */
public class ContactUsActivity extends Activity implements View.OnClickListener {
    private RelativeLayout btn_back;
    private TextView title, tv_number;
    private RelativeLayout rl_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_contact_us);
        title = (TextView) findViewById(R.id.top_title);
        title.setText(this.getString(R.string.home_btn_contact_us));
        initView();
    }

    private void initView() {
        rl_click = (RelativeLayout) findViewById(R.id.click);
        rl_click.setOnClickListener(this);
        tv_number = (TextView) findViewById(R.id.tv_car_wait_spent);
        btn_back = (RelativeLayout) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.click:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContactUsActivity.this.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        callPhone();
                    } else {
                    }
                } else {
                    callPhone();
                }

        }

    }

    void callPhone() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        //String str = tv_number.getText().toString();
        //String[] array = new String[10];
        //array = str.split("-");
        callIntent.setData(Uri.parse("tel:0532-85969888"));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try{
            startActivity(callIntent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


