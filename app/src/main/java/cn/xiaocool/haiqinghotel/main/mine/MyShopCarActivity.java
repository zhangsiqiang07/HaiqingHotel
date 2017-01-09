package cn.xiaocool.haiqinghotel.main.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.adapter.ShopCarListAdapter;
import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.main.ecshop.GoodIntroActivity;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import cn.xiaocool.haiqinghotel.net.request.ShopRequest;

/**
 * Created by 潘 on 2016/6/11.
 */
public class MyShopCarActivity extends Activity implements View.OnClickListener {
    private Context context;
    private TextView tvTitle,my_shopcar_clear;
    private ListView listView;
    private UserInfo userInfo;
    private ShopCarListAdapter shopListAdapter;
    private String name, id, price, pic, description;
    private String shopid;
    private ArrayList<HashMap<String, String>> arrayList;
    private Set set ;
    private RelativeLayout btn_back;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GOOD_INTRO:
                    Log.e("1111","success");
                    if (NetUtil.isConnnected(context)) {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        try {
                            String status = jsonObject.getString("status");
                            if (status.equals("success")) {
                                JSONObject object = jsonObject.getJSONObject("data");
                                name = new String();
                                id = new String();
                                price = new String();
                                pic = new String();
                                description =new String();
                                name = object.getString("name");
                                id = object.getString("id");
                                price = object.getString("price");
                                pic = object.getString("picture");
                                description = object.getString("description");
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id",id);
                                hashMap.put("name",name);
                                hashMap.put("price",price);
                                hashMap.put("pic",pic);
                                hashMap.put("description",description);
                                arrayList.add(hashMap);
                                }
                            shopListAdapter = new ShopCarListAdapter(context, arrayList);
                            listView.setAdapter(shopListAdapter);

                            } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_shopcar_fragment);
        context = this;
        userInfo = new UserInfo();
        userInfo.readData(context);
        Log.e("add shop id is ", userInfo.getshopId().toString());
        initview();
    }

    private void initview() {
        btn_back = (RelativeLayout) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        my_shopcar_clear = (TextView)findViewById(R.id.my_shopcar_clear);
        my_shopcar_clear.setOnClickListener(this);
        shopid = new String();
        listView = (ListView)findViewById(R.id.my_shopcar_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) shopListAdapter.getItem(position);
                String shopId = (String) hashMap.get("id");
                Log.e("shopId is ", shopId);
                Intent intent = new Intent();
                intent.setClass(context, GoodIntroActivity.class);
                intent.putExtra("shopId",shopId);
                startActivity(intent);
                finish();
            }
        });
        tvTitle = (TextView)findViewById(R.id.top_title);
        tvTitle.setText("我的购物车");
        arrayList = new ArrayList<>();
        set = new HashSet();
        set = userInfo.getshopId();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            shopid =iterator.next().toString();
            Log.e("set is ", shopid);
            new ShopRequest(context,handler).goodIntro(shopid);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_shopcar_clear:
                userInfo.clearshopId();
                userInfo.writeData(context);
                Toast.makeText(context,"清除成功",Toast.LENGTH_SHORT).show();
                ArrayList<HashMap<String, String>> List = new ArrayList<>();
                shopListAdapter = new ShopCarListAdapter(context,List);
                listView.setAdapter(shopListAdapter);
                break;
            case R.id.btn_back:
                finish();
                break;
        }

    }
}
