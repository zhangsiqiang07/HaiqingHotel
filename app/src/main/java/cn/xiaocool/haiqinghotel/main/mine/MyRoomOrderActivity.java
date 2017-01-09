package cn.xiaocool.haiqinghotel.main.mine;

import android.app.Activity;
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
import cn.xiaocool.haiqinghotel.adapter.MineRoomOrderAdapter;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.request.MineRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;

/**
 * Created by wzh on 2016/5/21.
 */
public class MyRoomOrderActivity extends Activity implements View.OnClickListener {
    private ListView listView;
    private ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    private String[] pic, name, count, price, time;
    private String a;
    private RelativeLayout btnback;
    private TextView tvTitle;
    private MineRoomOrderAdapter mineRoomOrderAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.MINE_ROOM_ORDER:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            pic = new String[length];
                            name = new String[length];
                            count = new String[length];
                            price = new String[length];
                            time = new String[length];
                            for (int i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                pic[i] = dataObject.getString("picture");
                                name[i] = dataObject.getString("name");
                                count[i] = dataObject.getString("number");
                                price[i] = dataObject.getString("price");
                                time[i] = dataObject.getString("time");
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("pic", pic[i]);
                                hashMap.put("name", name[i]);
                                hashMap.put("count", count[i]);
                                hashMap.put("price", price[i]);
                                hashMap.put("time", time[i]);
                                arrayList.add(hashMap);
                            }
                            mineRoomOrderAdapter = new MineRoomOrderAdapter(MyRoomOrderActivity.this, arrayList , handler);
                            listView.setAdapter(mineRoomOrderAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mine_room_order);
        initView();
        if (NetUtil.isConnnected(this)) {
            Log.e("net ok", "netok");
            new MineRequest(this, handler).myRoomOrder();
        }
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv_mine_roomorder);
        btnback = (RelativeLayout) findViewById(R.id.btn_back);
        btnback.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText(this.getString(R.string.mine_title_orderRoom));
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
