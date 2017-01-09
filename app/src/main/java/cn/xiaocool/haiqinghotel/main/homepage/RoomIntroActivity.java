package cn.xiaocool.haiqinghotel.main.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;
import cn.xiaocool.haiqinghotel.net.request.HomepageRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import cn.xiaocool.haiqinghotel.utils.TimeToolUtils;

/**
 * Created by wzh on 2016/5/8.
 */
public class RoomIntroActivity extends Activity implements View.OnClickListener {
    private TextView tvTitle;
    private RelativeLayout btnExit;
    private LinearLayout llDateChoose;
    private TextView tvPriceList;
    private String roomId, roomName;
    private long price;
    private String oPrice,pic;
    private String network;
    private String window;
    private String peopleNum;
    private String bathRoom;
    private String roomFloor;
    private String roomArea;
    private String bedSize;
    private String textChechIn,textCheckOut;
    private long msInDay,msOutDay;
    private UserInfo userInfo;
    private long dayCount;
    private int count;
    private Context context;
    private final int CODE = 1;
    private TextView tvOPrice, tvPrice, tvNetwork, tvWindow, tvPeopleNum, tvBathroom,
            tvRoomFloor, tvRoomArea, tvBedSize, tvTotalPrice, tvInday, tvOutday, tvDayCount;
    private Button btnReserve;
    private ImageView bg_pic;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.ROOM_DETAILS:
                    if (NetUtil.isConnnected(context)) {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        try {
                            String status = jsonObject.getString("status");
                            Log.e("room details status is", status);
                            if (status.equals("success")) {
                                JSONObject dataObject = (JSONObject) jsonObject.get("data");
                                oPrice = dataObject.getString("oprice");
                                network = dataObject.getString("network");
                                window = dataObject.getString("window");
                                peopleNum = dataObject.getString("peoplenumber");
                                bathRoom = dataObject.getString("bathroom");
                                roomFloor = dataObject.getString("floor");
                                roomArea = dataObject.getString("acreage");
                                bedSize = dataObject.getString("bedsize");
                                Log.e("", "oprice " + oPrice + "price" + price + "network" + network + "window" + window
                                        + "peopleNum" + peopleNum + "bathroom" + bathRoom + "floor" + roomFloor + "roomarea"
                                        + roomArea + "bedsize" + bedSize);
                                tvOPrice.setText(oPrice);
                                tvNetwork.setText(network);
                                tvWindow.setText(window);
                                tvPeopleNum.setText(peopleNum);
                                tvBathroom.setText(bathRoom);
                                tvRoomFloor.setText(roomFloor);
                                tvRoomArea.setText(roomArea);
                                tvBedSize.setText(bedSize);
//                                tvTotalPrice.setText("¥" + price);
//                                int intPrice = Integer.parseInt(price);
//                                int count = (int) (dayCount * intPrice);
//                                Log.e("count is", String.valueOf(count));
//                                tvTotalPrice.setText("¥" + count);
                                JSONArray jsonArray = dataObject.getJSONArray("pricelist");
                                int sumprice = 0;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    sumprice += Integer.parseInt(jsonArray.getJSONObject(i).getString("price"));
                                }
                                Log.e("sumprice is", String.valueOf(sumprice));
                                tvTotalPrice.setText("¥" + sumprice);
                                if(dayCount!=jsonArray.length()){
                                    Log.e("RoomIntroActivity","某些日期的房间不能被预定");
                                    Toast.makeText(context,"某些日期的房间不能被预定",Toast.LENGTH_SHORT).show();
                                    btnReserve.setText("不可预订");
                                    tvPrice.setText("--");
                                }else {
                                    btnReserve.setText("立即预订");
                                    price = sumprice/dayCount;
                                    tvPrice.setText(String.valueOf(price));
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_room_intro);
        context = this;
        userInfo = new UserInfo(context);
        Intent intent = getIntent();
        roomId = intent.getStringExtra("roomId");
        roomName = intent.getStringExtra("roomName");
        pic = intent.getStringExtra("pic");
        Log.e("roomId/Name is ", roomId + roomName);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE) {
            try{
                String indayMonNum = data.getStringExtra("inDayMonth1");
                String indayDayNum = data.getStringExtra("inDayNum0");
                String outdayMonNum = data.getStringExtra("outMonth1");
                String outdayDayNum = data.getStringExtra("outDayNum0");
                dayCount = data.getLongExtra("dayCount",1);
                msInDay = data.getLongExtra("msInDate",1);
                msOutDay = data.getLongExtra("msOutDate",1);
                Log.e("ms in out day is", String.valueOf(msInDay + "bbb" + msOutDay));
                Log.e("五个数：", indayMonNum + indayDayNum + outdayMonNum + outdayDayNum + "ccc" + dayCount);
                textChechIn = "入住：" + indayMonNum + "月" + indayDayNum + "日";
                tvInday.setText(textChechIn);
                textCheckOut = "离店：" + outdayMonNum + "月" + outdayDayNum + "日";
                tvOutday.setText(textCheckOut);
                tvDayCount.setText("共" + dayCount + "晚");
//                int intPrice = Integer.parseInt(price);
//                count = (int) (dayCount * intPrice);
//                Log.e("count is", String.valueOf(count));
//                tvTotalPrice.setText("¥" + count);

                String inDay = TimeToolUtils.fromateTimeShow(msInDay, "yyyy-MM-dd");
                String outDay = TimeToolUtils.fromateTimeShow(msOutDay, "yyyy-MM-dd");
                Log.e("inday and out day is", inDay + "111111111111" + outDay);
                if(userInfo.getUserId().equals("")) {
                    new HomepageRequest(this, handler).roomPriceDetails(inDay, outDay, roomId,"0");
                    Log.e("user","is not huiyuan");
                }else {
                    new HomepageRequest(this, handler).roomPriceDetails(inDay, outDay, roomId,userInfo.getUserId());
                }
            }catch (Exception ex){

            }

        }else{

        }
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText(roomName);
        tvPriceList = (TextView) findViewById(R.id.tv_price_list);
        tvPriceList.setOnClickListener(this);
        btnExit = (RelativeLayout) findViewById(R.id.btn_back);
        btnExit.setOnClickListener(this);
        tvOPrice = (TextView) findViewById(R.id.tv_oprice);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvNetwork = (TextView) findViewById(R.id.tv_network);
        tvWindow = (TextView) findViewById(R.id.tv_window);
        tvPeopleNum = (TextView) findViewById(R.id.tv_prople_num);
        tvBathroom = (TextView) findViewById(R.id.tv_bathroom);
        tvRoomFloor = (TextView) findViewById(R.id.tv_floor);
        tvRoomArea = (TextView) findViewById(R.id.tv_area);
        tvBedSize = (TextView) findViewById(R.id.tv_bedsize);
        tvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        btnReserve = (Button) findViewById(R.id.btn_reserve_now);
        btnReserve.setOnClickListener(this);
        llDateChoose = (LinearLayout) findViewById(R.id.reserve_choose_date);
        llDateChoose.setOnClickListener(this);
        bg_pic = (ImageView) findViewById(R.id.bg_pic);
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_loading)
                .showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();

        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + pic, bg_pic, displayImageOptions);
        //日期
        tvInday = (TextView) findViewById(R.id.tv_inday);
        tvOutday = (TextView) findViewById(R.id.tv_outday);
        tvDayCount = (TextView) findViewById(R.id.tv_day_count);
        //填充基本数据
        Calendar cal = Calendar.getInstance();

        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        Date dNow = new Date();
        cal.setTime(dNow);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        String indayMonNum = String.valueOf(month);
        String indayDayNum = String.valueOf(day);
        day = cal.get(Calendar.DATE);
        month = cal.get(Calendar.MONTH) + 1;
        year = cal.get(Calendar.YEAR);
        String outdayMonNum = String.valueOf(month);
        String outdayDayNum = String.valueOf(day);
        dayCount = 1;
        msInDay = TimeToolUtils.getNowTime();
        msOutDay = TimeToolUtils.getNowTime() + 86400000;
        Log.e("ms in out day is", String.valueOf(msInDay + "bbb" + msOutDay));
        Log.e("五个数：", indayMonNum + indayDayNum + outdayMonNum + outdayDayNum + "ccc" + dayCount);
        textChechIn = "入住：" + indayMonNum + "月" + indayDayNum + "日";
        tvInday.setText(textChechIn);
        textCheckOut = "离店：" + outdayMonNum + "月" + outdayDayNum + "日";
        tvOutday.setText(textCheckOut);
        tvDayCount.setText("共" + dayCount + "晚");
        String inDay = TimeToolUtils.fromateTimeShow(msInDay, "yyyy-MM-dd");
        String outDay = TimeToolUtils.fromateTimeShow(msOutDay, "yyyy-MM-dd");
        Log.e("inday and out day is", inDay + "111111111111" + outDay);
//        new HomepageRequest(this, handler).roomDetails(roomId);
        if(userInfo.getUserId().equals("")) {
            new HomepageRequest(this, handler).roomPriceDetails(inDay, outDay, roomId,"0");
        }else {
            new HomepageRequest(this, handler).roomPriceDetails(inDay, outDay, roomId,userInfo.getUserId());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_reserve_now:
                if(btnReserve.getText().toString().equals("不可预订")){
                    Toast.makeText(context,"某些日期房间不可预订",Toast.LENGTH_SHORT).show();
                }else {
                    String price = tvTotalPrice.getText().toString();
                    Intent reserveIntent = new Intent(this, BookingNowActivity.class);
                    reserveIntent.putExtra("bedsize", bedSize);
                    reserveIntent.putExtra("network", network);
                    reserveIntent.putExtra("textCheckIn", textChechIn);
                    reserveIntent.putExtra("textCheckOut", textCheckOut);
                    reserveIntent.putExtra("msInDay", msInDay);
                    reserveIntent.putExtra("msOutDay", msOutDay);
                    Log.e("room id isisisis", roomId);
                    reserveIntent.putExtra("roomId", roomId);
                    reserveIntent.putExtra("dayCount", dayCount);
                    reserveIntent.putExtra("totalPrice", price);
                    startActivity(reserveIntent);
                }
                break;
            case R.id.reserve_choose_date:
                Intent intent = new Intent(this, CalenderMainActivity.class);
                startActivityForResult(intent, CODE);
                break;
            case R.id.tv_price_list:
                Intent priceIntent = new Intent(this,PriceListActivity.class);
                Log.e("msinday in here is", String.valueOf(msInDay));
                Log.e("msoutday in here is",String.valueOf(msOutDay));
                priceIntent.putExtra("roomId",roomId);
                priceIntent.putExtra("msInDay",msInDay);
                priceIntent.putExtra("msOutDay",msOutDay);
                startActivity(priceIntent);
                break;
        }
    }
}