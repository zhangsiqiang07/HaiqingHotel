package cn.xiaocool.haiqinghotel.main.ecshop;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;
import cn.xiaocool.haiqinghotel.net.request.MineRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import cn.xiaocool.haiqinghotel.net.request.ShopRequest;

/**
 * Created by wzh on 2016/5/23.
 */
public class GoodIntroActivity extends Activity implements View.OnClickListener {
    private Context context;
    private TextView tvTitle, tvName, tvIntro, tvPrice,shop_btn_add_shopcar;
    private RelativeLayout btnExit;
    private ImageView ivLike,shop_pic,shop_detail_pic;
    private Button btnBuyNow;
    private String name;
    private String goodId;
    private String price;
    private String oPrice;
    private String unit;
    private String description;
    private UserInfo user;
    private String pic;
    private String[] picName;
    private int i = 2;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GOOD_INTRO:
                    if (NetUtil.isConnnected(GoodIntroActivity.this)) {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        try {

                            String status = jsonObject.getString("status");
                            Log.e("1111111", status);
                            if (status.equals("success")) {
                                JSONObject dataObject = jsonObject.getJSONObject("data");
                                Log.e("adcadc", dataObject.toString());
                                name = dataObject.getString("name");
                                Log.e("a1a1a1a1", name);
                                price = dataObject.getString("price");
                                oPrice = dataObject.getString("oprice");
                                unit = dataObject.getString("unit");
                                description = dataObject.getString("description");
                                tvTitle.setText(name);
                                tvName.setText(name);
                                tvIntro.setText(description);
                                tvPrice.setText("¥" + price);
                                pic = dataObject.getString("picture");
                                JSONArray jsonArray = dataObject.getJSONArray("photolist");
                                int length = jsonArray.length();
                                picName = new String[length];
                                JSONObject arrayObject;
                                for (int i = 0; i < length; i++) {
                                    arrayObject = (JSONObject) jsonArray.get(i);
                                    picName[i] = arrayObject.getString("photo");
                                }
                                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + pic, shop_detail_pic, displayImageOptions);
                                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + pic, shop_pic, displayImageOptions);
                            }else{
                                Toast.makeText(GoodIntroActivity.this,"产品已下架！",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case CommunalInterfaces.LIKE_GOOD:
                    if (NetUtil.isConnnected(GoodIntroActivity.this)) {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        try {
                            String status = jsonObject.getString("status");
                            Log.e("this status", status);
                            if (status.equals("success")) {
                                Toast.makeText(GoodIntroActivity.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case CommunalInterfaces.DISLIKE_GOOD:
                    if (NetUtil.isConnnected(GoodIntroActivity.this)) {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        try {
                            String status = jsonObject.getString("status");
                            Log.e("this status", status);
                            if (status.equals("success")) {
                                Toast.makeText(GoodIntroActivity.this, "取消收藏！", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
              case CommunalInterfaces.MY_LIKE_GOOD:
                  if (NetUtil.isConnnected(context)) {
                      JSONObject jsonObject = (JSONObject) msg.obj;
                      try {
                          String status = jsonObject.getString("status");
                          if (status.equals("success")) {
                              JSONArray jsonArray = jsonObject.getJSONArray("data");
                              JSONObject dataObject;
                              int length = jsonArray.length();
                              for (int i = 0 ;i<length;i++){
                                  if(goodId.equals(jsonArray.getJSONObject(i).getString("object_id"))){
                                        ivLike.setSelected(true);
                                  }
                              }
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
        setContentView(R.layout.shop_good_intro);
        context = this;
        user = new UserInfo(context);
        user.readData(context);
        Intent intent = getIntent();
        goodId = intent.getStringExtra("shopId");
        Log.e("shopId in here is", goodId);
        Log.e("add shop id is ", user.getshopId().toString());
        // 获取货物信息
        new ShopRequest(context, handler).goodIntro(goodId);
        initView();

    }

    private void initView() {
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.NONE)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
        shop_detail_pic = (ImageView) findViewById(R.id.shop_detail_pic);
        shop_pic = (ImageView) findViewById(R.id.shop_pic);
        tvTitle = (TextView) findViewById(R.id.top_title);
        btnExit = (RelativeLayout) findViewById(R.id.btn_back);
        btnExit.setOnClickListener(this);
        tvName = (TextView) findViewById(R.id.shop_good_name);
        tvIntro = (TextView) findViewById(R.id.shop_good_intro);
        tvPrice = (TextView) findViewById(R.id.shop_good_price);
        ivLike = (ImageView) findViewById(R.id.shop_iv_like);
        ivLike.setOnClickListener(this);
        btnBuyNow = (Button) findViewById(R.id.shop_btn_buy_now);
        btnBuyNow.setOnClickListener(this);
        shop_btn_add_shopcar = (TextView)findViewById(R.id.shop_btn_add_shopcar);
        shop_btn_add_shopcar.setOnClickListener(this);
        new MineRequest(this, handler).myLikeGood();
    }

    private void likeGood() {
        new ShopRequest(this, handler).likeGood(goodId, name, description);
    }
    private void disLikeGood() {
        new ShopRequest(this, handler).disLikeGood(goodId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.shop_iv_like:
                if (!ivLike.isSelected()) {
                    likeGood();
                    ivLike.setSelected(true);
                }else{
                    disLikeGood();
                    ivLike.setSelected(false);
                }
                Log.e("i = ",String.valueOf(i));
                break;
            case R.id.shop_btn_buy_now:
                Intent intent = new Intent();
                intent.setClass(this, BuyGoodNowActivity.class);
                intent.putExtra("goodId", goodId);
                intent.putExtra("name", name);
                intent.putExtra("intro", description);
                intent.putExtra("price", price);
                intent.putExtra("pic",pic);
                startActivity(intent);
                finish();
                break;
            case R.id.shop_btn_add_shopcar:
                if (user.getUserId().equals("")){
                    Toast.makeText(context,"未登录尚未开通此功能",Toast.LENGTH_SHORT).show();
                }else {
                    user.addshopId(goodId);
                    user.writeData(context);
                    Log.e("shopId is ", goodId+"本地存储存入成功");
                    Toast.makeText(context,"加入购物车成功",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }




}
