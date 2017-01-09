package cn.xiaocool.haiqinghotel.main.ecshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.main.MainActivity;
import cn.xiaocool.haiqinghotel.main.mine.MyShopOrderActivity;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import cn.xiaocool.haiqinghotel.net.request.ShopRequest;
import cn.xiaocool.haiqinghotel.utils.IntentUtils;

/**
 * Created by wzh on 2016/5/23.
 */
public class BuyGoodNowActivity extends Activity implements View.OnClickListener {

    private String goodId, goodName, intro, price,pic;
    private TextView tvTitle, tvGoodName, tvIntro, tvPrice, tvTotalPrice;
    private TextView tvAdd, tvSub, tvCount;
    private String goodNum;
    private Button btnBuyNow;
    private RelativeLayout btnExit;
    private ImageView buy_pic;
    private EditText etName, etRoom, etPho, etRemark;
    private int num = 1;
    private UserInfo user;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.SHOP_BUY_NOW:
                    if (NetUtil.isConnnected(BuyGoodNowActivity.this)) {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        try {
                            Log.e("what??", "fffffffffffff");
                            String status = jsonObject.getString("status");
                            if (status.equals("success")) {
                                Log.e("what??", "gggggggggg");
                                Toast.makeText(BuyGoodNowActivity.this, "预订成功！", Toast.LENGTH_SHORT).show();
                                if (user.getUserId().equals("0")){
                                    showPopupMenu();
                                }else {
                                    user.removeShopId(goodId);
                                    IntentUtils.getIntent(BuyGoodNowActivity.this, MyShopOrderActivity.class);
                                    finish();
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

            }
        }
    };
    private int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.shop_buy_now);
        Intent intent = getIntent();
        goodId = intent.getStringExtra("goodId");
        goodName = intent.getStringExtra("name");
        intro = intent.getStringExtra("intro");
        price = intent.getStringExtra("price");
        pic = intent.getStringExtra("pic");
        user = new UserInfo(this);
        totalPrice = Integer.parseInt(price);
        initView();

    }

    private void initView() {
        buy_pic = (ImageView) findViewById(R.id.buy_pic);
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.NONE)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + pic, buy_pic, displayImageOptions);
        Log.e("sdsdsdsd",NetBaseConstant.NET_PIC_PREFIX + pic);

        btnExit = (RelativeLayout) findViewById(R.id.btn_back);
        btnExit.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText("立即预定");
        tvGoodName = (TextView) findViewById(R.id.shop_tv_good_name);
        tvGoodName.setText(goodName);
        tvIntro = (TextView) findViewById(R.id.shop_tv_good_intro);
        tvIntro.setText(intro);
        tvPrice = (TextView) findViewById(R.id.shop_tv_good_price);
        tvPrice.setText("¥" + price);
        tvSub = (TextView) findViewById(R.id.shop_tv_sub);
        tvAdd = (TextView) findViewById(R.id.shop_tv_add);
        tvSub.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        tvCount = (TextView) findViewById(R.id.shop_tv_number);
        etName = (EditText) findViewById(R.id.shop_et_name);
        etName.setText(user.getUserName());
        etRoom = (EditText) findViewById(R.id.shop_et_room);
        etPho = (EditText) findViewById(R.id.shop_et_phoNum);
        etPho.setText(user.getUserPhone());
        etRemark = (EditText) findViewById(R.id.shop_et_remark);
        btnBuyNow = (Button) findViewById(R.id.shop_btn_booking_now);
        btnBuyNow.setOnClickListener(this);
        tvTotalPrice = (TextView) findViewById(R.id.shop_tv_total_price);
        tvTotalPrice.setText("¥" + price);
    }

    /**
     *未登录时，显示选择菜单
     * */
    private void showPopupMenu() {

        //自定义布局
        View layout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pop_not_login_book, null);
        //初始化popwindow
        final PopupWindow popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //设置弹出位置
        popupWindow.showAtLocation(btnBuyNow, Gravity.BOTTOM, 0, 0);

        final RelativeLayout back_home = (RelativeLayout)layout.findViewById(R.id.back_home);
        RelativeLayout phone_tell = (RelativeLayout)layout.findViewById(R.id.phone_tell);
        RelativeLayout btn_exit = (RelativeLayout)layout.findViewById(R.id.btn_exit);


        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = BuyGoodNowActivity.this.getWindow().getAttributes();
        lp.alpha = 0.7f;
        BuyGoodNowActivity.this.getWindow().setAttributes(lp);
        //监听popwindow消失事件，取消遮盖层
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1.0f;
                BuyGoodNowActivity.this.getWindow().setAttributes(lp);
            }
        });

        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backhome();
            }
        });
        phone_tell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone();
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
            }
        });



    }


    /**
     * 拨打酒店电话
     * */
    private void phone() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + "053285969888");
        intent.setData(data);
        startActivity(intent);
    }


    /**
     * 回主页
     * */
    private void backhome() {
        IntentUtils.getIntents(getApplicationContext(), MainActivity.class);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_tv_add:
                if (num < 9) {
                    num += 1;
                }
//                count = Integer.valueOf(tvNumber.getText().toString()).intValue();
                totalPrice = num * (Integer.valueOf(price).intValue());
                tvTotalPrice.setText("¥" + totalPrice);
//                tvBottomPrice.setText("¥" + totalPrice);
                tvCount.setText(String.valueOf(num));
                break;
            case R.id.shop_tv_sub:
                if (num > 0) {
                    num -= 1;
                }
//                count = Integer.valueOf(tvNumber.getText().toString()).intValue();
//                totalPrice = num * (Integer.valueOf(cateringPrice).intValue());
//                tvSub.setText("¥" + totalPrice);
//                tvTotalPrice.setText("¥" + totalPrice);
//                tvBottomPrice.setText("¥" + totalPrice);
                totalPrice = num * (Integer.valueOf(price).intValue());
                tvTotalPrice.setText("¥" + totalPrice);
                tvCount.setText(String.valueOf(num));
                break;
            case R.id.shop_btn_booking_now:
                Log.e("what??","ddddddddddddddd");
                goodNum = tvCount.getText().toString();
                String roomNum = etRoom.getText().toString();
                String peoName = etName.getText().toString();
                String phoNum = etPho.getText().toString();
                String remark = etRemark.getText().toString();
                String money = tvTotalPrice.getText().toString();
                user.readData(this);
                if (user.getUserId().equals("")){
                    user.setUserId("0");
                }
                if (phoNum.length() == 11) {
                    if (peoName.isEmpty()) {
                        Toast.makeText(BuyGoodNowActivity.this, "请输入联系人姓名", Toast.LENGTH_SHORT).show();


                    }else {
                        Log.e("what??", "eeeeeeeeee");
                        new ShopRequest(this, handler).buyGoodNow(goodId, roomNum, goodNum, peoName, phoNum, remark, String.valueOf(totalPrice));
                    }
                } else {
                    Toast.makeText(BuyGoodNowActivity.this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
