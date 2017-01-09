package cn.xiaocool.haiqinghotel.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import cn.xiaocool.haiqinghotel.BaseActivity;
import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.fragment.EcshopFragment;
import cn.xiaocool.haiqinghotel.fragment.FacilityFragment;
import cn.xiaocool.haiqinghotel.fragment.HomePageFragment;
import cn.xiaocool.haiqinghotel.fragment.MineFragment;
import cn.xiaocool.haiqinghotel.utils.IntentUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private HomePageFragment homePageFragment;
    private FacilityFragment facilityFragment;
    private EcshopFragment ecshopFragment;
    private MineFragment mineFragment;
    private RelativeLayout rlContainer;
    private Fragment[] fragments;
    private FragmentManager fragmentManager;
    private Button mTabs[];
    private Context mContext;
    private int index, currentIndex;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.navigation_button);
        userInfo = new UserInfo(this);
        userInfo.readData(this);
        rlContainer = (RelativeLayout) findViewById(R.id.fragment_container);
        homePageFragment = new HomePageFragment();
        facilityFragment = new FacilityFragment();
        ecshopFragment = new EcshopFragment();
        mineFragment = new MineFragment();
        fragments = new Fragment[]{homePageFragment, facilityFragment,ecshopFragment,mineFragment};
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, homePageFragment);
        fragmentTransaction.commit();
        initBtn();
//        Log.e("HQUID is ", String.valueOf(HQApplacation.UID));
    }

    private void initBtn() {
        mTabs = new Button[4];
        mTabs[0] = (Button) findViewById(R.id.btn_home);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (Button) findViewById(R.id.btn_facility);
        mTabs[1].setOnClickListener(this);
        mTabs[2] = (Button) findViewById(R.id.btn_store);
        mTabs[2].setOnClickListener(this);
        mTabs[3] = (Button) findViewById(R.id.btn_mine);
        mTabs[3].setOnClickListener(this);
        mTabs[0].setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home:
                index = 0;
                break;
            case R.id.btn_facility:
                index = 1;
                break;
            case R.id.btn_store:
                index = 2;
                break;
            case R.id.btn_mine:
                Log.e("userInfo is", userInfo.toString());
                if (userInfo.getUserId().equals("")) {
                    IntentUtils.getIntent(this, LoginActivity.class);
                } else {
                    index = 3;
                }
                break;

        }
        if (currentIndex != index) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()) {
                fragmentTransaction.add(R.id.fragment_container, fragments[index]);
            }
            fragmentTransaction.show(fragments[index]);
            fragmentTransaction.commit();
        }
        mTabs[currentIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentIndex = index;
    }
}