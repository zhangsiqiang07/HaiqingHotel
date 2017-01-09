package cn.xiaocool.haiqinghotel.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;

/**
 * Created by Administrator on 2016/6/22.
 */
public class ShopGridViewAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String, String>> arrayList;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public ShopGridViewAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
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
    public HashMap getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyGridViewHolder viewHolder;
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.item_grid_shop, parent, false);
            viewHolder = new MyGridViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyGridViewHolder) convertView.getTag();
        }
       viewHolder.shop_title.setText(arrayList.get(position).get("name"));
        viewHolder.shop_content.setText(arrayList.get(position).get("description"));
        viewHolder.shop_price.setText("¥" + arrayList.get(position).get("price"));
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayList.get(position).get("pic"), viewHolder.shop_img, displayImageOptions);

        return convertView;
    }

    class MyGridViewHolder {
        ImageView shop_img;
        TextView shop_title,shop_content,shop_price;
        public MyGridViewHolder(View convertView) {
            shop_img = (ImageView)convertView.findViewById(R.id.shop_img);
            shop_title = (TextView) convertView.findViewById(R.id.shop_title);
            shop_content = (TextView) convertView.findViewById(R.id.shop_content);
            shop_price = (TextView) convertView.findViewById(R.id.shop_price);
        }

    }
}
