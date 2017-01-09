package cn.xiaocool.haiqinghotel.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.main.mine.MineRoomDetailsActivity;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;
import cn.xiaocool.haiqinghotel.net.request.MineRequest;
import cn.xiaocool.haiqinghotel.utils.TimeToolUtils;

/**
 * Created by wzh on 2016/5/21.
 */
public class MineRoomOrderAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String, String>> arrayList;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Handler handler;
    private Context context;

    public MineRoomOrderAdapter(Context context, ArrayList<HashMap<String, String>> arrayList ,Handler handler) {
        this.context = context;
        this.handler = handler;
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
        final HashMap<String, String> array = arrayList.get(position);
        if(convertView==null) {
            convertView = layoutInflater.inflate(R.layout.mine_room_order_item, null);
            holder = new ViewHolder();
            holder.mine_delete_order = (TextView)convertView.findViewById(R.id.mine_delete_order);
            holder.mine_room_order_item = (LinearLayout)convertView.findViewById(R.id.mine_room_order_item);
            holder.imageView = (ImageView) convertView.findViewById(R.id.ig_mine_order_room);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_mine_order_name);
            holder.tvCount = (TextView) convertView.findViewById(R.id.tv_mine_order_count);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_mine_order_price);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_mine_order_time);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        //初始化控件
        String pic = arrayList.get(position).get("pic");
        String name = arrayList.get(position).get("name");
        String count = arrayList.get(position).get("count");
        String price = arrayList.get(position).get("price");
        String time = arrayList.get(position).get("time");
        Long msTime = Long.parseLong(time);
        String commonDay = TimeToolUtils.fromateTimeShow(msTime * 1000,"yyyy-MM-dd HH:MM:SS");
        holder.tvTime.setText(commonDay);
        holder.tvName.setText(name);
        holder.tvCount.setText(count);
        holder.tvPrice.setText(price);
        holder.mine_delete_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MineRequest(context, handler).cancelorder(array.get("orderNum"));
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.mine_room_order_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderNum = array.get("orderNum");//订单号
                String name = array.get("name");//房型
                String peoName = array.get("peoName");//入住人
                String count = array.get("count");//数量
                String remark = array.get("remark");//备注
                String phoNum = array.get("phoNum");// 联系方式
                String begintime = TimeToolUtils.fromateTimeShow(Long.parseLong(array.get("begintime")) * 1000, "yyyy-MM-dd");
                String endtime = TimeToolUtils.fromateTimeShow(Long.parseLong(array.get("endtime"))*1000, "yyyy-MM-dd");
                String arrivetime = array.get("arrivetime");
//                String one_price = (String) array.get("one_price");
                Intent intent = new Intent();
                intent.putExtra("orderNum", orderNum);
                intent.putExtra("name", name);
                intent.putExtra("peoName", peoName);
                intent.putExtra("count", count);
                intent.putExtra("remark", remark);
                intent.putExtra("phoNum", phoNum);
                intent.putExtra("begintime",begintime);
                intent.putExtra("endtime",endtime);
                intent.putExtra("arrivetime",arrivetime);
//                intent.putExtra("one_price",one_price);
                intent.setClass(context, MineRoomDetailsActivity.class);
                context.startActivity(intent);
            }
        });
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + pic, holder.imageView, displayImageOptions);
        return convertView;
    }
    class ViewHolder{
        LinearLayout mine_room_order_item;
        ImageView imageView;
        TextView tvName , tvCount , tvPrice , tvTime ,mine_delete_order;
    }
}
