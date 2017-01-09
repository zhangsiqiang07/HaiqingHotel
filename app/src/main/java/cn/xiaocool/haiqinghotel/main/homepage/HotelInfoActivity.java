package cn.xiaocool.haiqinghotel.main.homepage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.haiqinghotel.R;

/**
 * Created by mac on 16/5/23.
 */
public class HotelInfoActivity extends Activity implements View.OnClickListener {
    private RelativeLayout btn_back;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hotel_info);
        title = (TextView) findViewById(R.id.top_title);
        title.setText(this.getString(R.string.app_name));
        initView();
    }

    private void initView() {
        btn_back = (RelativeLayout) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
