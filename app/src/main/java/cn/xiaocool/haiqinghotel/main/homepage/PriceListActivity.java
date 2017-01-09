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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.adapter.HomePriceListAdapter;
import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.net.request.HomepageRequest;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.utils.TimeToolUtils;

/**
 * Created by wzh on 2016/6/7.
 */
public class PriceListActivity extends Activity implements View.OnClickListener {
    private RelativeLayout btn_back;
    private ListView listView;
    private UserInfo userInfo;
    private ArrayList<HashMap<String, String>> arrayList;
    private HashMap<String, String> hashMap;
    private HomePriceListAdapter homePriceListAdapter;
    private int length;
    private int roomCount;
    private String[] price, date;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.ROOM_DETAILS:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        JSONArray jsonArray = jsonObject1.getJSONArray("pricelist");
                        JSONObject arrayObject;
                        length = jsonArray.length();
                        price = new String[length];
                        date = new String[length];
                        for (int i = 0; i < length; i++) {
                            arrayObject = jsonArray.getJSONObject(i);
                            price[i] = arrayObject.getString("price");
                            date[i] = arrayObject.getString("time");
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("price", price[i]);
                            hashMap.put("date", date[i]);
                            arrayList.add(hashMap);
                        }
                        homePriceListAdapter = new HomePriceListAdapter(PriceListActivity.this, arrayList,roomCount);
                        listView.setAdapter(homePriceListAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.homepage_price_list);
        userInfo = new UserInfo();
        userInfo.readData(this);
        initView();
        Intent intent = getIntent();
        String roomId = intent.getStringExtra("roomId");
        long msInDay = intent.getLongExtra("msInDay", 0);
        long msOutDay = intent.getLongExtra("msOutDay", 0);
        roomCount = intent.getIntExtra("roomCount",0);
        String inDay = TimeToolUtils.fromateTimeShow(msInDay, "yyyy-MM-dd");
        String outDay = TimeToolUtils.fromateTimeShow(msOutDay, "yyyy-MM-dd");
        Log.e("inday and out day is", inDay + "111111111111" + outDay);
        Log.e("userid is",userInfo.getUserId());
        arrayList = new ArrayList<>();
        if(userInfo.getUserId().equals("")) {
            new HomepageRequest(this, handler).roomPriceDetails(inDay, outDay, roomId,"0");
        }else {
            new HomepageRequest(this, handler).roomPriceDetails(inDay, outDay, roomId,userInfo.getUserId());
        }
    }

    private void initView() {
        btn_back = (RelativeLayout) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.homepage_price_listview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
        }
    }
}
