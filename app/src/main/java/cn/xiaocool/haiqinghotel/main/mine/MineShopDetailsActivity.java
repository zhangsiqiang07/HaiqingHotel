package cn.xiaocool.haiqinghotel.main.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.haiqinghotel.R;

/**
 * Created by wzh on 2016/5/21.
 */
public class MineShopDetailsActivity extends Activity implements View.OnClickListener {
    private TextView tvOrderNum, tvRoomType, tvName, tvCount, tvPhoNum, tvRemark,mine_one_price;
    private TextView tvTitle;
    private RelativeLayout btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mine_shop_details);
        initView();
        Intent intent = getIntent();
        String orderNum = intent.getStringExtra("orderNum");
        String name = intent.getStringExtra("name");
        String peoName = intent.getStringExtra("peoName");
        String count = intent.getStringExtra("count");
        String remark = intent.getStringExtra("remark");
        String phoNum = intent.getStringExtra("phoNum");
//        String one_price = intent.getStringExtra("one_price");
        tvOrderNum.setText(orderNum);
        tvRoomType.setText(name);
        tvName.setText(peoName);
        tvCount.setText(count);
        tvRemark.setText(remark);
        tvPhoNum.setText(phoNum);
//        mine_one_price.setText(one_price);
    }

    private void initView() {
//        mine_one_price = (TextView) findViewById(R.id.mine_one_price);
        tvOrderNum = (TextView) findViewById(R.id.mine_tv_order_num);
        tvRoomType = (TextView) findViewById(R.id.mine_tv_order_type);
        tvName = (TextView) findViewById(R.id.mine_tv_name);
        tvCount = (TextView) findViewById(R.id.mine_tv_count);
        tvPhoNum = (TextView) findViewById(R.id.mine_tv_phoNum);
        tvRemark = (TextView) findViewById(R.id.mine_tv_remark);
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText(this.getString(R.string.mine_title_shopDetails));
        btnExit = (RelativeLayout) findViewById(R.id.btn_back);
        btnExit.setOnClickListener(this);
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
