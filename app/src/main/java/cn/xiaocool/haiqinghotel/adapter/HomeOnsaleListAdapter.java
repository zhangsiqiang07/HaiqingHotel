package cn.xiaocool.haiqinghotel.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.main.homepage.CateringIntroActivity;
import cn.xiaocool.haiqinghotel.main.homepage.RoomIntroActivity;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;

/**
 * Created by wzh on 2016/5/8.
 */
public class HomeOnsaleListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String, Object>> arrayList;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public HomeOnsaleListAdapter(Context context, ArrayList<HashMap<String, Object>> arrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        this.mContext = context;

        displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_loading)
                .showImageOnFail(R.mipmap.default_loading)
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
        ViewHolder holder;
        View view;
        final HashMap<String, Object> array = arrayList.get(position);
        if(convertView==null){
            view = layoutInflater.inflate(R.layout.home_onsale_list_item, null);
            holder = new ViewHolder();
            holder.onsalePic = (ImageView) view.findViewById(R.id.home_onsale_pic);
            holder.onsaleName = (TextView) view.findViewById(R.id.home_onsale_name);
            holder.onsalePrice = (TextView) view.findViewById(R.id.home_onsale_price);
            view.setTag(holder);
        }else{
            view = convertView;
            holder=(ViewHolder)view.getTag();
        }
        //加载布局,绑定组件

        final String picName = arrayList.get(position).get("picName").toString();
        holder.onsaleName.setText(arrayList.get(position).get("name").toString());
//        onsaleIntro.setText(arrayList.get(position).get("intro").toString());
        holder.onsalePrice.setText(arrayList.get(position).get("price").toString());
        holder.onsalePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goodId = (String) array.get("id");
                String goodName = (String) array.get("name");
                String type = (String) array.get("type");
                if (type.equals("1")) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, RoomIntroActivity.class);
                    intent.putExtra("roomId", goodId);
                    intent.putExtra("roomName", goodName);
                    intent.putExtra("pic",picName);
                    mContext.startActivity(intent);
                } else {
//                    Toast.makeText(context,"跳转餐饮",Toast.LENGTH_SHORT).show();//此处须跳转餐饮activity
                    if (type.equals("2")) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, CateringIntroActivity.class);
                        intent.putExtra("cateringId", goodId);
                        intent.putExtra("cateringName", goodName);
                        intent.putExtra("pic",picName);
                        mContext.startActivity(intent);
                    }
                }

            }
        });
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + picName, holder.onsalePic, displayImageOptions);
        return view;
    }
    class ViewHolder{
        ImageView onsalePic;
        TextView onsaleName , onsalePrice ;
    }
}
