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
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.bean.FacilityType;
import cn.xiaocool.haiqinghotel.main.facility.FacilityRoomDetailsActivity;
import cn.xiaocool.haiqinghotel.main.facility.WebShowDetailActivity;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;

/**
 * Created by wzh on 2016/5/22.
 */
public class FacilityListAdapter extends BaseAdapter {
    private ArrayList<FacilityType.FacilityTypeData> facilitytypeDataList;
    private LayoutInflater layoutInflater;
//    private ArrayList<HashMap<String, String>> arrayList;
    private DisplayImageOptions displayImageOptions;
    private Context mContext;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public FacilityListAdapter(Context context, ArrayList<FacilityType.FacilityTypeData> facilitytypeDataList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.facilitytypeDataList = facilitytypeDataList;

        this.mContext=context;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return facilitytypeDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return facilitytypeDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final FacilityType.FacilityTypeData facilitytypeData = facilitytypeDataList.get(position);
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.facility_item,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tvName.setText(facilitytypeData.getTitle());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (facilitytypeData.getType().equals("sheshi")){
                    //                HashMap<String, Object> hashMap= (HashMap<String, Object>) facilityListAdapter.getItem(position);
                    String facilityId = (String) facilitytypeDataList.get(position).getId();
                    String name = (String) facilitytypeDataList.get(position).getTitle();
                    Intent intent = new Intent();
                    intent.putExtra("facilityId", facilityId);
                    intent.putExtra("name", name);
                    Log.e("facility id is ", facilityId);
                    intent.setClass(mContext, FacilityRoomDetailsActivity.class);
                    mContext.startActivity(intent);
                }else if (facilitytypeData.getType().equals("zixun")){

                    Intent intent = new Intent();
                    intent.putExtra("facilityId", facilitytypeDataList.get(position).getId());
                    intent.putExtra("Title",facilitytypeDataList.get(position).getTitle());
                    intent.setClass(mContext, WebShowDetailActivity.class);
                    mContext.startActivity(intent);
                }

            }
        });
        if (facilitytypeData.getType().equals("zixun")){
            imageLoader.displayImage("http://hq.xiaocool.net/data/upload/" + facilitytypeData.getPhoto(), holder.imageView, displayImageOptions);
        }else {
            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + facilitytypeData.getPhoto(), holder.imageView, displayImageOptions);
        }


        return convertView;
    }
    class ViewHolder{
        TextView tvName,tvExcerpt;
        ImageView imageView;
        public ViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.iv_facility_pic);
            tvName = (TextView) convertView.findViewById(R.id.facility_tv_name);
        }
    }
}
