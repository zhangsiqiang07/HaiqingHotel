package cn.xiaocool.haiqinghotel.main.facility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;

/**
 * Created by mac on 16/5/24.
 */
public class activityShowFacility extends Activity implements View.OnClickListener {
    private Context context;
    private TextView tvTitle, tvContent;
    private RelativeLayout btnExit;
    private ImageView ivPhoto;
    private String title,content;
    private String Id;
    private String photo;
    private String description;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private int i = 2;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GOOD_INTRO:
                    if (NetUtil.isConnnected(context)) {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        try {

                            String status = jsonObject.getString("status");
//                            Log.e("1111111", status);
//                            if (status.equals("success")) {
//                                JSONObject dataObject = jsonObject.getJSONObject("data");
//                                Log.e("adcadc", dataObject.toString());
//                                name = dataObject.getString("name");
//                                Log.e("a1a1a1a1", name);
//                                price = dataObject.getString("price");
//                                oPrice = dataObject.getString("oprice");
//                                unit = dataObject.getString("unit");
//                                description = dataObject.getString("description");
//                                tvTitle.setText(name);
//                                tvName.setText(name);
//                                tvIntro.setText(description);
//                                tvPrice.setText("¥" + price);
//                                pic = dataObject.getString("picture");
//                                JSONArray jsonArray = dataObject.getJSONArray("photolist");
//                                int length = jsonArray.length();
//                                picName = new String[length];
//                                JSONObject arrayObject;
//                                for (int i = 0; i < length; i++) {
//                                    arrayObject = (JSONObject) jsonArray.get(i);
//                                    picName[i] = arrayObject.getString("photo");
//                                }
//
//                            }else{
//                                Toast.makeText(context, "产品已下架！", Toast.LENGTH_SHORT).show();
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case CommunalInterfaces.LIKE_GOOD:
                    if (NetUtil.isConnnected(context)) {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        try {
                            String status = jsonObject.getString("status");
                            Log.e("this status", status);
                            if (status.equals("success")) {
                                Toast.makeText(context, "收藏成功！", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.show_facility_intro);
        context = this;
        Intent intent = getIntent();
        Id = intent.getStringExtra("id");
        photo=intent.getStringExtra("photo");
        content=intent.getStringExtra("content");
        title=intent.getStringExtra("title");
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
        initView();
//        new ShopRequest(context, handler).goodIntro(goodId);
//        tvTitle.setText(name);
//        tvName.setText(name);
//        tvIntro.setText(description);
//        tvPrice.setText("¥" + price);
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.top_title);
        btnExit = (RelativeLayout) findViewById(R.id.btn_back);
        btnExit.setOnClickListener(this);
        tvContent = (TextView) findViewById(R.id.tv_facility_content);
        tvContent.setText(content);
        tvTitle.setText(title);
        ivPhoto=(ImageView)findViewById(R.id.iv_facility_photo);
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + photo, ivPhoto, displayImageOptions);

//        tvIntro = (TextView) findViewById(R.id.shop_good_intro);

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