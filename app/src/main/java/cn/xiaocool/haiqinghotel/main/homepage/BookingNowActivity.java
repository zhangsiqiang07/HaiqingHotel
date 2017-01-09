package cn.xiaocool.haiqinghotel.main.homepage;

import android.app.Activity;
import android.content.Context;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.main.MainActivity;
import cn.xiaocool.haiqinghotel.main.mine.MyOrderActivity;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;
import cn.xiaocool.haiqinghotel.net.request.HomepageRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import cn.xiaocool.haiqinghotel.utils.IntentUtils;
import cn.xiaocool.haiqinghotel.utils.TimeToolUtils;

/**
 * Created by wzh on 2016/5/8.
 */
public class BookingNowActivity extends Activity implements View.OnClickListener {
    private PopupWindow mPopupWindow;
    private LinearLayout llRoomNum, llArriveTime;
    private TextView tvTitle, tvRoomCount, tvArriveTime, tvTotalPrice,room_name;
    private ImageView room_pic;
    private String arriveTime, roomId;
    private int roomCount;
    private TextView tvIntro, tvCheckIn, tvCheckOUt, tvDayCount,tvPriceList;
    private RelativeLayout rlExit;
    private String bedSize, network, checkIn, checkOut;
    private long msInDay, msOutDay;
    private Button btnBookNow;
    private EditText etName, etPhoNum, etRemark;
    private UserInfo user;
    private Context mContext;
    private int totalPrice;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private int length;
    private String[] price, date;
    private ArrayList<HashMap<String, String>> arrayList;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.RESERVE_ROOM_NOW:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            Toast.makeText(BookingNowActivity.this,"预订成功！",Toast.LENGTH_SHORT).show();
                            if (user.getUserId().equals("0")){
                                showPopupMenu();
                            }else {
//                                IntentUtils.getIntents( mContext, MyOrderActivity.class);
                                Intent intent = new Intent(mContext,MyOrderActivity.class);
                                intent.putExtra("type",0);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(BookingNowActivity.this, "预定失败！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                case CommunalInterfaces.ROOM_DETAILS:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                        JSONArray jsonArray = jsonObject2.getJSONArray("pricelist");
                        JSONObject arrayObject;
                        int length = jsonArray.length();
                        price = new String[length];
                        date = new String[length];
                        for (int i = 0; i < length; i++) {
                            arrayObject = jsonArray.getJSONObject(i);
                            price[i] = arrayObject.getString("price");
                            totalPrice += Integer.parseInt(price[i]);
                        }
                        String price = String.valueOf(totalPrice);
                        tvTotalPrice.setText("¥"+price);
                        priceTotal = totalPrice;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };
    private long dayCount;
    private int priceNumber;
    private int priceTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_booking_now);
        mContext = this;
        user = new UserInfo(mContext);
        user.readData(this);
        if (user.getUserId().equals("")){
            user.setUserId("0");
        }
        initView();
        getData();
    }

    private void getData() {
        String inDay = TimeToolUtils.fromateTimeShow(msInDay, "yyyy-MM-dd");
        String outDay = TimeToolUtils.fromateTimeShow(msOutDay, "yyyy-MM-dd");
//        if(user.getUserId().equals("")) {
//            new HomepageRequest(this, handler).roomPriceDetails(inDay, outDay, roomId,"0");
//        }else {
            new HomepageRequest(this, handler).roomPriceDetails(inDay, outDay, roomId,user.getUserId());
//        }
    }

    private void initView() {
        roomCount = 1;
        room_name = (TextView) findViewById(R.id.room_name);
        room_pic = (ImageView) findViewById(R.id.room_img);
        tvTotalPrice = (TextView) findViewById(R.id.home_reserve_now_totalPrice);
        tvDayCount = (TextView) findViewById(R.id.tv_dayCount);
        llRoomNum = (LinearLayout) findViewById(R.id.ll_room_num);
        llRoomNum.setOnClickListener(this);
        llArriveTime = (LinearLayout) findViewById(R.id.ll_arrive_time);
        llArriveTime.setOnClickListener(this);
        tvRoomCount = (TextView) findViewById(R.id.tv_roomCount);
        tvArriveTime = (TextView) findViewById(R.id.tv_arriveTime);
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText(this.getString(R.string.home_btn_reserve_now));
        rlExit = (RelativeLayout) findViewById(R.id.btn_back);
        rlExit.setOnClickListener(this);
        tvCheckIn = (TextView) findViewById(R.id.tv_check_in);
        tvCheckOUt = (TextView) findViewById(R.id.tv_check_out);
        tvIntro = (TextView) findViewById(R.id.tv_introduce);
        btnBookNow = (Button) findViewById(R.id.btn_booking_now);
        btnBookNow.setOnClickListener(this);
        etName = (EditText) findViewById(R.id.et_name);
        etName.setText(user.getUserName());
        etPhoNum = (EditText) findViewById(R.id.et_phoNum);
        etPhoNum.setText(user.getUserPhone());
        etRemark = (EditText) findViewById(R.id.et_remark);
        tvPriceList=(TextView)findViewById(R.id.tv_price_list);
        tvPriceList.setOnClickListener(this);

        Intent intent = getIntent();
        Log.e("bedsize network", bedSize + network);
        bedSize = intent.getStringExtra("bedsize");
        network = intent.getStringExtra("network");
        checkIn = intent.getStringExtra("textCheckIn");
        checkOut = intent.getStringExtra("textCheckOut");
        dayCount = intent.getLongExtra("dayCount", 1);
        Log.e("check in day is ", checkIn + checkOut);
        roomId = intent.getStringExtra("roomId");
        String name = intent.getStringExtra("name");
        String pic = intent.getStringExtra("pic");
        String tvRepast = intent.getStringExtra("tvRepast");
        Log.e("roomId here is  ", roomId);
        //取到毫秒值
        msInDay = intent.getLongExtra("msInDay", 0);
        msOutDay = intent.getLongExtra("msOutDay", 0);
        Log.e("ms time", msInDay + "aaaa" + msOutDay);
        tvCheckIn.setText(checkIn);
        tvCheckOUt.setText(checkOut);
        //介绍行
        tvIntro.setText(bedSize + this.getString(R.string.space) + network + " " + tvRepast);
        tvDayCount.setText("共" + dayCount + "晚");
        arriveTime = "18:00前";


        room_name.setText(name);
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + pic, room_pic, displayImageOptions);
    }


    private void showRoomPopupWindow() {
        View contentView = LayoutInflater.from(BookingNowActivity.this).inflate(R.layout.room_popuplayout, null);
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        TextView tv1 = (TextView) contentView.findViewById(R.id.pop_roomCount1);
        TextView tv2 = (TextView) contentView.findViewById(R.id.pop_roomCount2);
        TextView tv3 = (TextView) contentView.findViewById(R.id.pop_roomCount3);
        TextView tv4 = (TextView) contentView.findViewById(R.id.pop_roomCount4);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);

        View rootview = LayoutInflater.from(BookingNowActivity.this).inflate(R.layout.home_booking_now, null);
        mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }

    private void showTimePopupWindow() {
        View contentView = LayoutInflater.from(BookingNowActivity.this).inflate(R.layout.time_popuplayout, null);
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        TextView tv1 = (TextView) contentView.findViewById(R.id.pop_time0);
        TextView tv2 = (TextView) contentView.findViewById(R.id.pop_time1);
        TextView tv3 = (TextView) contentView.findViewById(R.id.pop_time2);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);

        View rootview = LayoutInflater.from(BookingNowActivity.this).inflate(R.layout.home_booking_now, null);
        mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }

    /**
     *未登录时，显示选择菜单
     * */
    private void showPopupMenu() {

        //自定义布局
        View layout = LayoutInflater.from(mContext).inflate(R.layout.pop_not_login_book, null);
        //初始化popwindow
        final PopupWindow popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //设置弹出位置
        popupWindow.showAtLocation(btnBookNow, Gravity.BOTTOM, 0, 0);

        final RelativeLayout back_home = (RelativeLayout)layout.findViewById(R.id.back_home);
        RelativeLayout phone_tell = (RelativeLayout)layout.findViewById(R.id.phone_tell);
        RelativeLayout btn_exit = (RelativeLayout)layout.findViewById(R.id.btn_exit);


        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = BookingNowActivity.this.getWindow().getAttributes();
        lp.alpha = 0.7f;
        BookingNowActivity.this.getWindow().setAttributes(lp);
        //监听popwindow消失事件，取消遮盖层
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1.0f;
                BookingNowActivity.this.getWindow().setAttributes(lp);
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
        IntentUtils.getIntents(mContext, MainActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_room_num:
                showRoomPopupWindow();
                break;
            case R.id.ll_arrive_time:
                showTimePopupWindow();
                break;
            case R.id.pop_roomCount1:
                tvRoomCount.setText("1间");
                roomCount = 1;
                priceTotal = totalPrice * roomCount;
                tvTotalPrice.setText("¥" + priceTotal);
                mPopupWindow.dismiss();
                break;
            case R.id.pop_roomCount2:
                tvRoomCount.setText("2间");
                roomCount = 2;
                priceTotal = totalPrice * roomCount;
                tvTotalPrice.setText("¥" + priceTotal);
                mPopupWindow.dismiss();
                break;
            case R.id.pop_roomCount3:
                tvRoomCount.setText("3间");
                roomCount = 3;
                priceTotal = totalPrice * roomCount;
                tvTotalPrice.setText("¥" + priceTotal);
                mPopupWindow.dismiss();
                break;
            case R.id.pop_roomCount4:
                tvRoomCount.setText("4间");
                roomCount = 4;
                priceTotal = totalPrice * roomCount;
                tvTotalPrice.setText("¥" + priceTotal);
                mPopupWindow.dismiss();
                break;
            case R.id.pop_time0:
                tvArriveTime.setText("12:00前");
                arriveTime = "12:00前";
                mPopupWindow.dismiss();
                break;
            case R.id.pop_time1:
                tvArriveTime.setText("18:00前");
                arriveTime = "18:00前";
                mPopupWindow.dismiss();
                break;
            case R.id.pop_time2:
                tvArriveTime.setText("24:00前");
                arriveTime = "24:00前";
                mPopupWindow.dismiss();
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_booking_now:
                if(etPhoNum.getText().length()!=11){
                    Toast.makeText(mContext, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                }else {
                    String name = etName.getText().toString();
                    String phoNum = etPhoNum.getText().toString();
                    String remark = etRemark.getText().toString();
                    Log.e("入参数据", roomId + "--11---" + arriveTime + "--22---" + phoNum + "--3--" + remark);
                    if (NetUtil.isConnnected(this)) {
                        user.readData(mContext);
                        if(user.getUserId()==""){
                            user.setUserId("0");
                        }
                        Log.e("入参数据====", user.getUserId());
                        Log.e("msinday", String.valueOf(msInDay));
                        Log.e("msOutDay", String.valueOf(msOutDay));
                        Log.e("arriveTime", String.valueOf(arriveTime));
                        //发起预定房间请求
                        new HomepageRequest(this, handler).reserveNow(user.getUserId(), roomId,  String.valueOf(msInDay/1000), String.valueOf(msOutDay/1000), arriveTime,
                                String.valueOf(roomCount), "1", phoNum, remark, name, String.valueOf(priceTotal));
                    } else {
                        Toast.makeText(this, "无网络连接！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tv_price_list:
                Intent priceIntent = new Intent(this,PriceListActivity.class);
                Log.e("msinday in here is", String.valueOf(msInDay));
                Log.e("msoutday in here is",String.valueOf(msOutDay));
                Log.e("roomid in here is",roomId);
                priceIntent.putExtra("roomId",roomId);
                priceIntent.putExtra("msInDay",msInDay);
                priceIntent.putExtra("msOutDay",msOutDay);
                priceIntent.putExtra("roomCount",roomCount);
                startActivity(priceIntent);
                break;
        }
    }
}
