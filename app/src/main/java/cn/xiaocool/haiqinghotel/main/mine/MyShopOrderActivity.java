package cn.xiaocool.haiqinghotel.main.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.adapter.MineShopOrderAdapter;
import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.request.MineRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;

/**
 * Created by wzh on 2016/5/21.
 */
public class MyShopOrderActivity extends Activity implements View.OnClickListener {
    private ListView listView;
    private ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    private String[] orderNum, peoName, remark, pic, name, count, price, time, phoNum,one_price;
    private String a;
    private RelativeLayout btnback;
    private TextView tvTitle;
    private UserInfo userInfo;
    private MineShopOrderAdapter mineShopOrderAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.MINE_SHOP_ORDER:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            orderNum = new String[length];
                            peoName = new String[length];
                            remark = new String[length];
                            pic = new String[length];
                            name = new String[length];
                            count = new String[length];
                            price = new String[length];
                            time = new String[length];
                            phoNum = new String[length];
                            one_price = new String[length];
                            for (int i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                orderNum[i] = dataObject.getString("order_num");
                                pic[i] = dataObject.getString("picture");
                                name[i] = dataObject.getString("name");
                                count[i] = dataObject.getString("number");
                                peoName[i] = dataObject.getString("peoplename");
                                remark[i] = dataObject.getString("remarks");
                                price[i] = dataObject.getString("money");
                                time[i] = dataObject.getString("time");
                                phoNum[i] = dataObject.getString("mobile");
//                                one_price[i] = dataObject.getString("price");
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("orderNum", orderNum[i]);
                                hashMap.put("pic", pic[i]);
                                hashMap.put("name", name[i]);
                                hashMap.put("count", count[i]);
                                hashMap.put("price", price[i]);
                                hashMap.put("peoName", peoName[i]);
                                hashMap.put("time", time[i]);
                                hashMap.put("phoNum", phoNum[i]);
                                hashMap.put("remark", remark[i]);
//                                hashMap.put("one_price",one_price[i]);
                                arrayList.add(hashMap);
                            }
                            mineShopOrderAdapter = new MineShopOrderAdapter(MyShopOrderActivity.this, arrayList,handler);
                            listView.setAdapter(mineShopOrderAdapter);
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
        setContentView(R.layout.mine_shop_order);
        userInfo = new UserInfo(this);
        initView();
        userInfo.readData(this);
        if (NetUtil.isConnnected(this)) {
                new MineRequest(this, handler).myShopOrder();
        }
        setItemClick();
    }


    private void setItemClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) mineShopOrderAdapter.getItem(position);
                String orderNum = (String) hashMap.get("orderNum");//订单号
                String name = (String) hashMap.get("name");//餐饮名称
                String peoName = (String) hashMap.get("peoName");//预订人
                String count = (String) hashMap.get("count");//数量
                String remark = (String) hashMap.get("remark");//备注
                String phoNum = (String) hashMap.get("phoNum");// 联系方式
//                String one_price = (String) hashMap.get("one_price");
                Intent intent = new Intent();
                intent.putExtra("orderNum", orderNum);
                intent.putExtra("name", name);
                intent.putExtra("peoName", peoName);
                intent.putExtra("count", count);
                intent.putExtra("remark", remark);
                intent.putExtra("phoNum", phoNum);
//                intent.putExtra("one_price",one_price);
                intent.setClass(MyShopOrderActivity.this, MineShopDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv_mine_shopOrder);
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
