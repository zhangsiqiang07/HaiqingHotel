package cn.xiaocool.haiqinghotel.main.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;
import cn.xiaocool.haiqinghotel.net.request.HomepageRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import cn.xiaocool.haiqinghotel.ui.ReboundScrollView;

/**
 * Created by wzh on 2016/5/13.
 */
public class CateringIntroActivity extends Activity implements View.OnClickListener {
    private String cateringId, cateringName, cateringPrice, cateringOprice, cateringUnit, cateringDesc,pic;
    private String[] id, name, unit, price;
    private ListView listView;
    private RelativeLayout rlExit;
    private Button btnReserve;
    private TextView tvTitle,tvDescrip,tvOprice,tvOprice1,tvPrice,tvPrice1;
    private SimpleAdapter simpleAdapter;
    private ImageView bg_pic;
    private ReboundScrollView scrollview;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.CATERING_DETAILS:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONObject dataObject = jsonObject.getJSONObject("data");
                            Log.e("datajsonobject is ", dataObject.toString());
                            cateringId = dataObject.getString("id");
                            cateringName = dataObject.getString("name");
                            cateringPrice = dataObject.getString("price");
                            cateringOprice = dataObject.getString("oprice");
                            cateringUnit = dataObject.getString("unit");
                            cateringDesc = dataObject.getString("description");
                            Log.e("price oprice",cateringPrice + cateringOprice + cateringDesc);
                            tvDescrip.setText(cateringDesc);
                            tvOprice.setText(cateringOprice);
                            tvOprice1.setText("¥" + cateringOprice);
                            tvPrice.setText(cateringPrice);
                            tvPrice1.setText("¥" + cateringPrice);
                            JSONArray listArray = dataObject.getJSONArray("goodlist");
                            JSONObject listObject;
                            int length = listArray.length();
                            name = new String[length];
                            unit = new String[length];
                            price = new String[length];
                            ArrayList<HashMap<String, String>> arraylist = new ArrayList<>();
                            for (int i = 0; i < length; i++) {
                                listObject = (JSONObject) listArray.get(i);
                                String listName = listObject.getString("name");
                                String listUnit = listObject.getString("unit");
                                String listPrice = listObject.getString("price");
                                name[i] = listName;
                                unit[i] = listUnit;
                                price[i] = listPrice;
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("name", name[i]);
                                hashMap.put("unit", unit[i]);
                                hashMap.put("price", price[i]);
                                arraylist.add(hashMap);
                            }
                            simpleAdapter = new SimpleAdapter(CateringIntroActivity.this, arraylist, R.layout.catering_list_item,
                                    new String[]{"name", "unit"},
                                    new int[]{R.id.tv_catering_name, R.id.tv_catering_unit});
                            listView.setAdapter(simpleAdapter);
                            scrollview.post(new Runnable() {
                                public void run() {

                                    scrollview.fullScroll(ScrollView.FOCUS_UP);
                                }
                            });

                        } else {

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
        setContentView(R.layout.home_catering_intro);
        Intent intent = getIntent();
        cateringId = intent.getStringExtra("cateringId");
        String cateringName = intent.getStringExtra("cateringName");
        pic = intent.getStringExtra("pic");
        initView();
        tvTitle.setText(cateringName);

        if (NetUtil.isConnnected(this)) {
            new HomepageRequest(this, handler).cateringDetails(cateringId);

        }
    }

    private void initView() {
        scrollview = (ReboundScrollView) findViewById(R.id.scrollview);
        listView = (ListView) findViewById(R.id.catering_set_meal_list);
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvDescrip = (TextView) findViewById(R.id.tv_catering_descrip);
        tvOprice = (TextView) findViewById(R.id.tv_catering_oprice);
        tvOprice1 = (TextView) findViewById(R.id.tv_catering_oprice1);
        tvPrice = (TextView) findViewById(R.id.tv_catering_price);
        tvPrice1 = (TextView) findViewById(R.id.tv_catering_price1);
        rlExit = (RelativeLayout) findViewById(R.id.btn_back);
        rlExit.setOnClickListener(this);
        btnReserve = (Button) findViewById(R.id.btn_catering_reserve_now);
        btnReserve.setOnClickListener(this);
        bg_pic = (ImageView) findViewById(R.id.bg_pic);
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_loading)
                .showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();

        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + pic, bg_pic, displayImageOptions);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_catering_reserve_now:
                Intent intent = new Intent();
                intent.setClass(this,BookingCateringActivity.class);
                intent.putExtra("cateringName",cateringName);
                intent.putExtra("cateringPrice",cateringPrice);
                intent.putExtra("cateringId",cateringId);
                intent.putExtra("cateringPic",pic);
                startActivity(intent);
                break;
        }
    }
}
