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

import java.util.List;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.bean.Facility;
import cn.xiaocool.haiqinghotel.main.facility.WebShowDetailActivity;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;

/**
 * Created by wzh on 2016/5/22.
 */
public class FacilityListClickAdapter extends BaseAdapter {
    private List<Facility.FacilityData> facilityDataList;
    private LayoutInflater layoutInflater;
    private Context mContext;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public FacilityListClickAdapter(Context context, List<Facility.FacilityData> facilityDataList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.mContext=context;
        this.facilityDataList=facilityDataList;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return facilityDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return facilityDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final Facility.FacilityData facilityDataData = facilityDataList.get(position);
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.facility_item_click_item,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tvName.setText(facilityDataData.getTitle());
        holder.tvExcerpt.setText(facilityDataData.getExcerpt());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facilityId = (String) facilityDataData.getId();
                String photo = (String) facilityDataData.getPhoto();
                String Content = (String) facilityDataData.getContent();
                String title = (String) facilityDataData.getTitle();
                Intent intent = new Intent();
                intent.putExtra("facilityId", facilityDataData.getId());
                intent.putExtra("Title", title);
                intent.setClass(mContext, WebShowDetailActivity.class);
                mContext.startActivity(intent);
            }
        });

        imageLoader.displayImage("http://hq.xiaocool.net/data/upload/" + facilityDataList.get(position).getPhoto(), holder.imageView, displayImageOptions);
        Log.e("NetBaseConstX ", NetBaseConstant.NET_PIC_PREFIX + facilityDataList.get(position).getPhoto());
        return convertView;
    }
    class ViewHolder{
        TextView tvName,tvExcerpt;
        ImageView imageView;
        public ViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.facility_iv_itemPic);
            tvName = (TextView) convertView.findViewById(R.id.facility_tv_itemName);
            tvExcerpt = (TextView) convertView.findViewById(R.id.facility_tv_itemExcerpt);
        }
    }
}
