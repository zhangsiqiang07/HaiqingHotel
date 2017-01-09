package cn.xiaocool.haiqinghotel.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.main.mine.MineSettingActivity;
import cn.xiaocool.haiqinghotel.main.mine.MyEditActivity;
import cn.xiaocool.haiqinghotel.main.mine.MyLikeGoodActivity;
import cn.xiaocool.haiqinghotel.main.mine.MyOrderActivity;
import cn.xiaocool.haiqinghotel.main.mine.MyShopCarActivity;
import cn.xiaocool.haiqinghotel.main.mine.MyShopOrderActivity;
import cn.xiaocool.haiqinghotel.net.request.MineRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import cn.xiaocool.haiqinghotel.utils.IntentUtils;

/**
 * Created by wzh on 2016/4/28.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private LinearLayout llRoomOrder, llShopOrder;
    private Context context;
    private String name,img;
    private ImageView home_pic;
    private ImageView ivSetting;
    private TextView tvName;
    private UserInfo userInfo;
    private RelativeLayout rlMyLike , mine_shopcar;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.MY_INFOR:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONObject dataObject = jsonObject.getJSONObject("data");
                            Log.e("dataObject",jsonObject.optString("data"));
                            name = dataObject.getString("name");
                            img = dataObject.optString("photo");
                            ImageLoader.getInstance().displayImage("http://hq.xiaocool.net/uploads/microblog/"+img,home_pic);
                            Log.e("name is", name);
                            tvName.setText(name);
                            userInfo.setUserName(name);
                            userInfo.writeData(context);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new MineRequest(context, handler).myInfor();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        userInfo = new UserInfo(context);
        initView();
        if (NetUtil.isConnnected(context)) {
            new MineRequest(context, handler).myInfor();
        } else {
            tvName.setText(userInfo.getUserName());
        }
    }

    private void initView() {
        tvName = (TextView) getView().findViewById(R.id.mine_userName);
        llRoomOrder = (LinearLayout) getView().findViewById(R.id.ll_mine_room_order);
        llRoomOrder.setOnClickListener(this);
        llShopOrder = (LinearLayout) getView().findViewById(R.id.ll_mine_shop_order);
        llShopOrder.setOnClickListener(this);
        home_pic = (ImageView) getView().findViewById(R.id.home_pic);
        home_pic.setOnClickListener(this);
        ivSetting = (ImageView) getView().findViewById(R.id.mine_btn_setting);
        ivSetting.setOnClickListener(this);
        rlMyLike = (RelativeLayout) getView().findViewById(R.id.mine_rl_my_like);
        rlMyLike.setOnClickListener(this);
        mine_shopcar = (RelativeLayout)getView().findViewById(R.id.mine_shopcar);
        mine_shopcar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_mine_room_order:
                IntentUtils.getIntent((Activity) context, MyOrderActivity.class);
                break;
            case R.id.ll_mine_shop_order:
                IntentUtils.getIntent((Activity) context, MyShopOrderActivity.class);
                break;
            case R.id.mine_btn_setting:
                IntentUtils.getIntent((Activity) context, MineSettingActivity.class);
                break;
            case R.id.mine_rl_my_like:
                IntentUtils.getIntent((Activity) context, MyLikeGoodActivity.class);
                break;
            case R.id.home_pic:
                IntentUtils.getIntent((Activity) context, MyEditActivity.class);
                break;
            case R.id.mine_shopcar:
                IntentUtils.getIntent((Activity)context, MyShopCarActivity.class);
                break;
        }
    }
}
