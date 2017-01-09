package cn.xiaocool.haiqinghotel.main.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.adapter.HomePageReserveAdapter;
import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.request.HomepageRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import cn.xiaocool.haiqinghotel.utils.TimeToolUtils;

/**
 * Created by wzh on 2016/5/17.
 */
public class HomeReserveNowActivity extends Activity implements View.OnClickListener {
    private ListView listView;
    private TextView tvTitle;
    private RelativeLayout btnExit;
    private HomePageReserveAdapter homePageReserveAdapter;
    private ArrayList<HashMap<String, String>> arrayList;
    private int length, i;
    private UserInfo user;
    private String[] id, pic, name, price, oprice, acreage, network, repast, bedsize, adtitle, floor;
    private int[] iscanbook;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.HOME_RESERVE_ROOM:
                    JSONObject jsonobject = (JSONObject) msg.obj;
                    try {
                        String status = jsonobject.getString("status");
                        Log.e("HOME_RESERVE_ROOM",jsonobject.getString("data"));
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonobject.getJSONArray("data");
                            length = jsonArray.length();
                            JSONObject dataObject;
                            network = new String[length];
                            id = new String[length];
                            pic = new String[length];
                            name = new String[length];
                            price = new String[length];
                            oprice = new String[length];
                            acreage = new String[length];
                            repast = new String[length];
                            bedsize = new String[length];
                            adtitle = new String[length];
                            floor = new String[length];
                            iscanbook = new int[length];
                            for (i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                network[i] = dataObject.getString("network");
                                id[i] = dataObject.getString("id");
                                pic[i] = dataObject.getString("picture");
                                name[i] = dataObject.getString("name");
                                price[i] = dataObject.getString("price");
                                oprice[i] = dataObject.getString("oprice");
                                acreage[i] = dataObject.getString("acreage");
                                repast[i] = dataObject.getString("repast");//早餐1代表有
                                bedsize[i] = dataObject.getString("bedsize");
                                adtitle[i] = dataObject.getString("adtitle");
                                floor[i] = dataObject.getString("floor");
                                iscanbook[i] = dataObject.getInt("iscanbook");
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", id[i]);
                                hashMap.put("pic", pic[i]);
                                hashMap.put("name", name[i]);
                                hashMap.put("price", price[i]);
                                hashMap.put("oprice", oprice[i]);
                                hashMap.put("acreage", acreage[i]);
                                hashMap.put("network", network[i]);
                                hashMap.put("repast", repast[i]);
                                hashMap.put("bedsize", bedsize[i]);
                                hashMap.put("adtitle", adtitle[i]);
                                hashMap.put("floor", floor[i]);
                                hashMap.put("iscanbook", String.valueOf(iscanbook[i]));
                                arrayList.add(hashMap);
                            }
                            homePageReserveAdapter = new HomePageReserveAdapter(HomeReserveNowActivity.this, arrayList,
                                    inDay, outDay,msInday,msOutday,dayCount);
                            listView.setAdapter(homePageReserveAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };
    private String inDay;
    private String outDay;
    private long msInday;
    private long msOutday;
    private long dayCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.homepage_reserve);
        initView();
        Intent intent = getIntent();
        inDay = intent.getStringExtra("textCheckIn");
        outDay = intent.getStringExtra("textCheckOut");
        dayCount = intent.getLongExtra("dayCount", 1);
        Log.e("in here daycount is", String.valueOf(dayCount));
        msInday = intent.getLongExtra("msInDay",1);
        msOutday = intent.getLongExtra("msOutDay",1);
        String begindate = TimeToolUtils.fromateTimeShow(msInday, "yyyy-MM-dd");
        String enddate = TimeToolUtils.fromateTimeShow(msOutday, "yyyy-MM-dd");
        //Log.e("msInday and msOutday is ",String.valueOf(msInday + msOutday));
        Log.e("this.inday is", begindate);
        Log.e("this.outday is", enddate);
//        intent.getStringExtra("bedsize");
//        intent.getStringExtra("network");
//        intent.getStringExtra("roomId");
        user = new UserInfo(getApplicationContext());
        user.readData(getApplicationContext());
        if (NetUtil.isConnnected(this)) {
            if (user.getUserId()==""){
                user.setUserId("0");
            }
            new HomepageRequest(this, handler).homeReserveRoom(begindate,enddate,user.getUserId());
        }
        Log.e("执行", "...............");
//        onItemClick();
    }

//    private void onItemClick() {
//        Log.e("进入进入进入", "");
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.e("点击点击点击", "");
//                HashMap<String, String> intentMap = (HashMap<String, String>) homePageReserveAdapter.getItem(position);
////                String intentPic = intentMap.get("pic");
////                String intentName = intentMap.get("name");
////                String intentPrice = intentMap.get("price");
////                String intentOprice = intentMap.get("oprice");
////                String intentAcreage = intentMap.get("acreage");
////                String intentRepast = intentMap.get("repast");
////                String intentFloor = intentMap.get("floor");
//                String intentId = intentMap.get("id");
//                String intentBedsize = intentMap.get("bedsize");
//                String intentNet = intentMap.get("network");
//                Intent intent = new Intent();
//                intent.setClass(HomeReserveNowActivity.this, BookingNowActivity.class);
//                intent.putExtra("roomId", intentId);
//                intent.putExtra("bedsize", intentBedsize);
//                intent.putExtra("network", intentNet);
//                intent.putExtra("textCheckIn", inDay);
//                intent.putExtra("textCheckOut", outDay);
//                Log.e("intentintentintent", "");
//                startActivity(intent);
//            }
//        });
//    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText(this.getString(R.string.home_btn_reserve_now));
        listView = (ListView) findViewById(R.id.home_reserve_now_room_list);
        btnExit = (RelativeLayout) findViewById(R.id.btn_back);
        btnExit.setOnClickListener(this);
        arrayList = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
