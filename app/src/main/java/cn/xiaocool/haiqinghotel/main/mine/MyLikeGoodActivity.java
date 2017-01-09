package cn.xiaocool.haiqinghotel.main.mine;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import cn.xiaocool.haiqinghotel.adapter.MyLikeGoodAdapter;
import cn.xiaocool.haiqinghotel.net.request.MineRequest;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;

/**
 * Created by wzh on 2016/5/23.
 */
public class MyLikeGoodActivity extends Activity implements View.OnClickListener {
    private String[] id, title, description,price, createtime, photo;
    private ListView listView;
    private RelativeLayout btnExit;
    private TextView tvTitle;
    private ArrayList<HashMap<String, String>> arrayList;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.MY_LIKE_GOOD:
                    if (NetUtil.isConnnected(MyLikeGoodActivity.this)) {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        try {
                            String status = jsonObject.getString("status");
                            if (status.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                JSONObject dataObject;
                                int length = jsonArray.length();
                                id = new String[length];
                                title = new String[length];
                                price = new String[length];
                                description = new String[length];
                                createtime = new String[length];
                                photo = new String[length];
                                for (int i = 0; i < length; i++) {
                                    dataObject = jsonArray.getJSONObject(i);
                                    id[i] = dataObject.getString("object_id");
                                    title[i] = dataObject.getString("title");
                                    description[i] = dataObject.getString("description");
                                    price[i] = dataObject.getString("price");
                                    createtime[i] = dataObject.getString("createtime");
                                    photo[i] = dataObject.getString("photo");
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("id", id[i]);
                                    hashMap.put("title", title[i]);
                                    hashMap.put("price",price[i]);
                                    hashMap.put("description", description[i]);
                                    hashMap.put("createtime", createtime[i]);
                                    hashMap.put("photo", photo[i]);
                                    arrayList.add(hashMap);
                                }
                                MyLikeGoodAdapter myLikeGoodAdapter = new MyLikeGoodAdapter(MyLikeGoodActivity.this,
                                        arrayList);
                                listView.setAdapter(myLikeGoodAdapter);
                            }
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
        setContentView(R.layout.my_like_good);
        initView();
        arrayList = new ArrayList<>();
        new MineRequest(this, handler).myLikeGood();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.mine_my_like_good_list);
        btnExit = (RelativeLayout) findViewById(R.id.btn_back);
        btnExit.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText("我的收藏");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
