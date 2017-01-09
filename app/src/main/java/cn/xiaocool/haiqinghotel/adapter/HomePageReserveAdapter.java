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
import cn.xiaocool.haiqinghotel.main.homepage.BookingNowActivity;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;
import cn.xiaocool.haiqinghotel.utils.ToastUtils;

/**
 * Created by wzh on 2016/5/18.
 */
public class HomePageReserveAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String, String>> arrayList;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    private String bedsize;
    private String repast;
    private String acreage;
    private String name;
    private String price;
    private String oprice;
    private String adtitle;
    private String inDay, outDay;
    private long msInDay, msOutDay, dayCount;
    private String id;
    private int count;

    public HomePageReserveAdapter(Context context, ArrayList<HashMap<String, String>> arrayList,
                                  String inDay, String outDay, long msInDay, long msOutDay, long dayCount) {
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        this.context = context;
        this.inDay = inDay;
        this.outDay = outDay;
        this.msInDay = msInDay;
        this.msOutDay = msOutDay;
        this.dayCount = dayCount;
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.homepage_reserve_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String pic = arrayList.get(position).get("pic").toString();
        name = arrayList.get(position).get("name");
        id = arrayList.get(position).get("id");
        price = arrayList.get(position).get("price");
        oprice = arrayList.get(position).get("oprice");
        acreage = arrayList.get(position).get("acreage");
        repast = arrayList.get(position).get("repast");
        bedsize = arrayList.get(position).get("bedsize");
        adtitle = arrayList.get(position).get("adtitle");
        final String iscanbook = arrayList.get(position).get("iscanbook");
        String floor = arrayList.get(position).get("floor");
        holder.tvName.setText(name);
        holder.tvPrice.setText("¥" + price);
        int intPrice = Integer.parseInt(price);
        count = (int) (dayCount * intPrice);
        Log.e("count is", String.valueOf(count));
        holder.tvOprice.setText("¥" + oprice);
        holder.tvInfor.setText(acreage + " " + bedsize + "  " + adtitle + "cm" + "  " + floor);
        if (repast.equals("0")) {
            holder.tvRepast.setText("无早");
        } else if (repast.equals("1")) {
            holder.tvRepast.setText("单早");
        } else if (repast.equals("2")) {
            holder.tvRepast.setText("双早");
        }
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + pic, holder.imageView, displayImageOptions);
        holder.btnHomeReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tvPrice.getText().equals("¥0")||iscanbook.equals("0")) {
                    ToastUtils.ToastShort(context, "暂无房源");
                } else {
                    Log.e("11111", "2222");
                    Intent intent = new Intent(context, BookingNowActivity.class);
                    int intPrice = Integer.parseInt(arrayList.get(position).get("price"));
                    count = (int) (dayCount * intPrice);
                    intent.putExtra("tvRepast",holder.tvRepast.getText());
                    intent.putExtra("name",name);
                    intent.putExtra("pic",pic);
                    intent.putExtra("totalPrice", "¥" + count);
                    intent.putExtra("bedsize", arrayList.get(position).get("bedsize"));
                    intent.putExtra("network", "wifi");
                    intent.putExtra("name", arrayList.get(position).get("name"));
                    intent.putExtra("price", arrayList.get(position).get("price"));
                    intent.putExtra("textCheckIn", inDay);
                    intent.putExtra("textCheckOut", outDay);
                    intent.putExtra("roomId", arrayList.get(position).get("id"));
                    intent.putExtra("msInDay", msInDay);
                    intent.putExtra("msOutDay", msOutDay);
                    intent.putExtra("dayCount", dayCount);
                    String totalPrice = String.valueOf(count);
                    intent.putExtra("totalPrice", totalPrice);
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    @SuppressWarnings("rawtypes")
    public void getBundle(Class clazz) {
//        Car.CarData cardataInfo = carDataList.get(position);
        Bundle bundle = new Bundle();
//        bundle.putSerializable(key, cardataInfo);

        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        int intPrice = Integer.parseInt(price);
        count = (int) (dayCount * intPrice);
        intent.putExtra("totalPrice", "¥" + count);

        intent.putExtra("bedsize", bedsize);
        intent.putExtra("network", "wifi");
        intent.putExtra("name", name);
        intent.putExtra("price", price);
        intent.putExtra("textCheckIn", inDay);
        intent.putExtra("textCheckOut", outDay);
        intent.putExtra("roomId", id);
        intent.putExtra("msInDay", msInDay);
        intent.putExtra("msOutDay", msOutDay);
        intent.putExtra("dayCount", dayCount);
        String totalPrice = String.valueOf(count);
        intent.putExtra("totalPrice", totalPrice);
        context.startActivity(intent);
    }

    class ViewHolder {
        //            TextView car_number,car_brand;
        Button btnHomeReserve;
        ImageView imageView;
        TextView tvName, tvRepast, tvInfor, tvPrice, tvOprice;

        //            ImageView car_editor;
        public ViewHolder(View convertView) {
            btnHomeReserve = (Button) convertView.findViewById(R.id.home_item_btn_reserve);
            imageView = (ImageView) convertView.findViewById(R.id.iv_home_reserve);
            tvName = (TextView) convertView.findViewById(R.id.tv_home_roomName);
            tvRepast = (TextView) convertView.findViewById(R.id.tv_home_repast);
            tvInfor = (TextView) convertView.findViewById(R.id._tv_home_reserve_infor);
            tvPrice = (TextView) convertView.findViewById(R.id.tv_home_reserve_price);
            tvOprice = (TextView) convertView.findViewById(R.id.tv_home_reserve_oprice);
        }
    }
}
