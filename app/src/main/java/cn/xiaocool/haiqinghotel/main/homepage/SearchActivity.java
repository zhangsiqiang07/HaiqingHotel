package cn.xiaocool.haiqinghotel.main.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import cn.xiaocool.haiqinghotel.R;

/**
 * Created by wzh on 2016/5/29.
 */
public class SearchActivity extends Activity implements View.OnClickListener {

    private RelativeLayout btnBack;
    private Button btnSearch;
    private EditText editText;
    private String inDay;
    private String outDay;
    private long msInday;
    private long msOutday;
    private long dayCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.homepage_search);
        Intent intent = getIntent();
        inDay = intent.getStringExtra("textCheckIn");
        outDay = intent.getStringExtra("textCheckOut");
        dayCount = intent.getLongExtra("dayCount",1);
        Log.e("in here daycount is", String.valueOf(dayCount));
        msInday = intent.getLongExtra("msInDay",1);
        msOutday = intent.getLongExtra("msOutDay",1);
        btnBack = (RelativeLayout) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        btnSearch = (Button) findViewById(R.id.homepage_btn_search);
        btnSearch.setOnClickListener(this);
        editText = (EditText) findViewById(R.id.homepage_et_search);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.homepage_btn_search:
                String search = editText.getText().toString();
                Intent intent1 = new Intent();
                intent1.putExtra("searchName",search);
                intent1.setClass(this, SearchActivity.class);
                intent1.putExtra("msInDay", msInday);
                intent1.putExtra("msOutDay", msOutday);
                intent1.putExtra("textCheckIn", inDay);
                intent1.putExtra("textCheckOut", outDay);
                intent1.putExtra("dayCount", dayCount);
                startActivity(intent1);
                intent1.setClass(this,SearchReserveNowActivity.class);
                finish();
                startActivity(intent1);
        }
    }
}
