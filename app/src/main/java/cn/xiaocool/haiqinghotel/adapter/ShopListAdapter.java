package cn.xiaocool.haiqinghotel.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout;
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
public class ShopListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String, String>> arrayList;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context mContext;
    private ShopGridViewAdapter shopGridViewAdapter;

    public ShopListAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        this.mContext = context;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
        if (shopGridViewAdapter == null) {
            shopGridViewAdapter = new ShopGridViewAdapter(this.mContext, this.arrayList);
        } else {
            shopGridViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if (arrayList.size() % 2 == 0) {
            return arrayList.size() / 2;
        } else {
            return arrayList.size() / 2 + 1;
        }

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

//        String picName = arrayList.get(position).get("pic");
//        ImageView imageView = (ImageView) convertView.findViewById(R.id.shop_iv_pic);
//        TextView tvName = (TextView) convertView.findViewById(R.id.shop_tv_name);
//        TextView tvDescr = (TextView) convertView.findViewById(R.id.shop_tv_descrip);
//        TextView tvPrice = (TextView) convertView.findViewById(R.id.shop_tv_price);
//        tvName.setText(arrayList.get(position).get("name"));
//        tvDescr.setText(arrayList.get(position).get("description"));
//        tvPrice.setText("¥" + arrayList.get(position).get("price"));
//        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + picName, imageView, displayImageOptions);
        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.shop_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.e("显示", String.valueOf(arrayList.size() % 2));

        if (arrayList.size() % 2 != 0) {
            Log.e("显示", String.valueOf(arrayList.size() / 2 + 1));
            Log.e("显示", String.valueOf(position));
            if (position == arrayList.size() / 2 ) {
                viewHolder.shop_two.setVisibility(View.GONE);
                viewHolder.show_white.setVisibility(View.VISIBLE);
                Log.e("显示","ddddddddddd");
            }

        } else {
            viewHolder.shop_two.setVisibility(View.VISIBLE);
            viewHolder.show_white.setVisibility(View.GONE);
            viewHolder.shop_title2.setText(arrayList.get(position * 2 + 1).get("name"));
            viewHolder.shop_content2.setText(arrayList.get(position * 2 + 1).get("description"));
            viewHolder.shop_price2.setText("¥" + arrayList.get(position * 2 + 1).get("price"));
            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayList.get(position * 2 + 1).get("pic"), viewHolder.shop_img2, displayImageOptions);
        }

        viewHolder.shop_title.setText(arrayList.get(position * 2).get("name"));
        viewHolder.shop_content.setText(arrayList.get(position * 2).get("description"));
        viewHolder.shop_price.setText("¥" + arrayList.get(position * 2).get("price"));
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayList.get(position * 2).get("pic"), viewHolder.shop_img, displayImageOptions);



        viewHolder.shop_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, GoodIntroActivity.class);
                intent.putExtra("shopId",arrayList.get(position * 2).get("id"));
                mContext.startActivity(intent);
            }
        });

        viewHolder.shop_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodIntroActivity.class);
                intent.putExtra("shopId",arrayList.get(position * 2+1).get("id"));
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView shop_img, shop_img2;
        TextView shop_title, shop_content, shop_price, shop_title2, shop_content2, shop_price2;
        LinearLayout shop_one, shop_two;
        FrameLayout show_white;
        public ViewHolder(View convertView) {
            show_white = (FrameLayout) convertView.findViewById(R.id.show_white);
            shop_img = (ImageView) convertView.findViewById(R.id.shop_img);
            shop_title = (TextView) convertView.findViewById(R.id.shop_title);
            shop_content = (TextView) convertView.findViewById(R.id.shop_content);
            shop_price = (TextView) convertView.findViewById(R.id.shop_price);
            shop_img2 = (ImageView) convertView.findViewById(R.id.shop_img2);
            shop_title2 = (TextView) convertView.findViewById(R.id.shop_title2);
            shop_content2 = (TextView) convertView.findViewById(R.id.shop_content2);
            shop_price2 = (TextView) convertView.findViewById(R.id.shop_price2);
            shop_one = (LinearLayout) convertView.findViewById(R.id.shop_one);
            shop_two = (LinearLayout) convertView.findViewById(R.id.shop_two);
        }

    }
}
