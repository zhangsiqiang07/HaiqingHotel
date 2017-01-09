package cn.xiaocool.haiqinghotel.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.main.ecshop.GoodIntroActivity;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;

/**
 * Created by wzh on 2016/5/22.
 */
public class MyLikeGoodAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String, String>> arrayList;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    private String id;

    public MyLikeGoodAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        this.context = context;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        convertView = layoutInflater.inflate(R.layout.mine_my_like_good_item, null);

        holder = new ViewHolder(convertView);
        convertView.setTag(holder);
        holder.btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("11111","2222");
                getBundle(position,"id",GoodIntroActivity.class,"商品购买");

            }
        });




        String picName = arrayList.get(position).get("photo");
        id = arrayList.get(position).get("id");
        String createTime = arrayList.get(position).get("createtime");
        String price = arrayList.get(position).get("price");
        ImageView imageView = (ImageView) convertView.findViewById(R.id.mine_iv_my_like);
        TextView tvName = (TextView) convertView.findViewById(R.id.mine_tv_good_title);
        TextView tvIntro = (TextView) convertView.findViewById(R.id.mine_tv_good_intro);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.mine_my_like_good_price);
        tvName.setText(arrayList.get(position).get("title"));
        tvIntro.setText(arrayList.get(position).get("description"));
        tvPrice.setText("¥" + price);
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + picName, imageView, displayImageOptions);
        return convertView;
    }


    public void getBundle(final int position, String key, Class clazz, String str) {
//        Car.CarData cardataInfo = carDataList.get(position);
        Bundle bundle = new Bundle();
//        bundle.putSerializable(key, cardataInfo);
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        intent.putExtra("shopId",id);
        Log.e("shopId is",id);
        context.startActivity(intent);
    }
    class ViewHolder{
        //            TextView car_number,car_brand;
        Button btnReserve;
        //            ImageView car_editor;
        public ViewHolder(View convertView) {
            btnReserve = (Button) convertView.findViewById(R.id.mine_my_like_btn_reserve);
        }
    }
}
