package cn.xiaocool.haiqinghotel.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
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
import cn.xiaocool.haiqinghotel.net.request.MineRequest;
import cn.xiaocool.haiqinghotel.utils.TimeToolUtils;

/**
 * Created by wzh on 2016/5/21.
 */
public class MineCateringOrderAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String, String>> arrayList;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Handler handler;
    private Context context;
    public MineCateringOrderAdapter(Context context, ArrayList<HashMap<String, String>> arrayList,Handler handler) {
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        this.handler = handler;
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
        convertView = layoutInflater.inflate(R.layout.mine_catering_order_item, null);
        //初始化控件
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ig_mine_order_room);
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_mine_order_name);
        TextView tvCount = (TextView) convertView.findViewById(R.id.tv_mine_order_count);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tv_mine_order_price);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tv_mine_order_time);
        TextView quxiao = (TextView) convertView.findViewById(R.id.quxiao);
        String pic = arrayList.get(position).get("pic");
        String name = arrayList.get(position).get("name");
        String count = arrayList.get(position).get("count");
        String price = arrayList.get(position).get("price");
        String time = arrayList.get(position).get("time");

//        long msTime = Long.parseLong(time);
        Long msTime = Long.parseLong(time);
        String commonDay = TimeToolUtils.fromateTimeShow(msTime * 1000,"yyyy-MM-dd HH:MM:SS");
//        Date d = new Date(msTime);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//
//        String date = sdf.format(d);
        tvTime.setText(commonDay);
        tvName.setText(name);
        tvCount.setText(count);
        tvPrice.setText(price);
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + pic, imageView, displayImageOptions);

        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MineRequest(context, handler).cancelorder(arrayList.get(position).get("orderNum"));
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
