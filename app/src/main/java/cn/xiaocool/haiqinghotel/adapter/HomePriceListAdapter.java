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
import cn.xiaocool.haiqinghotel.utils.TimeToolUtils;

/**
 * Created by wzh on 2016/5/21.
 */
public class HomePriceListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String, String>> arrayList;
    private int count;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public HomePriceListAdapter(Context context, ArrayList<HashMap<String, String>> arrayList,int count) {
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        this.count = count;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.homepage_price_list_item, null);
        //初始化控件
        TextView tvPrice = (TextView) convertView.findViewById(R.id.home_price_list_price);
        TextView tvDate = (TextView) convertView.findViewById(R.id.home_price_list_date);
        TextView tvCount = (TextView) convertView.findViewById(R.id.home_price_list_count);
        int totalPrice = Integer.parseInt(arrayList.get(position).get("price"))*count;
        tvCount.setText(String.valueOf(count));
        tvPrice.setText(String.valueOf(totalPrice));
        tvDate.setText(arrayList.get(position).get("date"));
//        Long msTime = Long.parseLong(time);
//        String commonDay = TimeToolUtils.fromateTimeShow(msTime * 1000, "yyyy-MM-dd HH:MM:SS");
        return convertView;
    }
}
