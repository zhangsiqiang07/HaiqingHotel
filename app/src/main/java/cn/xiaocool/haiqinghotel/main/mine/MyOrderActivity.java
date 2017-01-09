package cn.xiaocool.haiqinghotel.main.mine;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.fragment.MineOrderCateringFragment;
import cn.xiaocool.haiqinghotel.fragment.MineOrderRoomFragment;

/**
 * Created by wzh on 2016/5/21.
 */
public class MyOrderActivity extends Activity implements View.OnClickListener {
    private MineOrderRoomFragment mineOrderRoomFragment;
    private MineOrderCateringFragment mineOrderCateringFragment;
    private LinearLayout llContainer;
    private Fragment[] fragment;
    private FragmentManager fragmentManager;
    private Button[] button;
    private Button btnSelect0,btnSelect1;
    private int currentIndex,index;
    private TextView tvTitle;
    private RelativeLayout btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mine_order_layout);
        initView();
    }

    private void initView() {
        btnSelect0 = (Button) findViewById(R.id.mine_select0);
        btnSelect1 = (Button) findViewById(R.id.mine_select1);
        btnSelect0.setOnClickListener(this);
        btnSelect1.setOnClickListener(this);
        btnSelect0.setSelected(true);
        llContainer = (LinearLayout) findViewById(R.id.mine_order_fragment_container);
        mineOrderRoomFragment = new MineOrderRoomFragment();
        mineOrderCateringFragment = new MineOrderCateringFragment();
        fragment = new Fragment[]{mineOrderRoomFragment, mineOrderCateringFragment};
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mine_order_fragment_container, mineOrderRoomFragment);
        fragmentTransaction.commit();
        button = new Button[2];
        button[0] = (Button) findViewById(R.id.mine_btn_myRoom);
        button[1] = (Button) findViewById(R.id.mine_btn_myCatering);
        button[0].setOnClickListener(this);
        button[1].setOnClickListener(this);
        btnback = (RelativeLayout) findViewById(R.id.btn_back);
        btnback.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText(this.getString(R.string.mine_title_orderRoom));

        //判断预订完那种类型的 显示哪种类型的fragment
        index = getIntent().getIntExtra("type",0);

        if (currentIndex != index) {
            if (index==0){
                btnSelect0.setSelected(true);
                btnSelect1.setSelected(false);
            }else {
                btnSelect1.setSelected(true);
                btnSelect0.setSelected(false);
            }
            FragmentTransaction fragmentTransactiont = fragmentManager.beginTransaction();
            fragmentTransactiont.hide(fragment[currentIndex]);
            if (!fragment[index].isAdded()) {
                fragmentTransactiont.add(R.id.mine_order_fragment_container, fragment[index]);
            }
            fragmentTransactiont.show(fragment[index]);
            fragmentTransactiont.commit();
        }
        currentIndex = index;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_back:
                finish();
                break;
            case R.id.mine_btn_myRoom:
                btnSelect0.setSelected(true);
                btnSelect1.setSelected(false);
                index = 0;
                Log.e("加载第一个", "fragment");
                break;
//                FragmentTransaction fragmentTransaction0 = fragmentManager.beginTransaction();
//                if (!fragment[0].isAdded()) {
//                    fragmentTransaction0.add(R.id.mine_order_fragment_container, fragment[0]);
//                } else if (fragment[0].isAdded()) {
//                    fragmentTransaction0.show(fragment[0]);
//                    fragmentTransaction0.hide(fragment[1]);
//                }
//                fragmentTransaction0.commit();

            case R.id.mine_btn_myCatering:
                btnSelect1.setSelected(true);
                btnSelect0.setSelected(false);
                index = 1;
                Log.e("加载第2个", "fragment");
                break;
//                FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
//                if (!fragment[1].isAdded()) {
//                    fragmentTransaction1.add(R.id.mine_order_fragment_container, fragment[1]);
//                } else if (fragment[1].isAdded()) {
//                    fragmentTransaction1.show(fragment[1]);
//                    fragmentTransaction1.hide(fragment[0]);
//                }
//                fragmentTransaction1.commit();
        }

        if (currentIndex != index) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(fragment[currentIndex]);
            if (!fragment[index].isAdded()) {
                fragmentTransaction.add(R.id.mine_order_fragment_container, fragment[index]);
            }
            fragmentTransaction.show(fragment[index]);
            fragmentTransaction.commit();
        }
        currentIndex = index;
    }
}

