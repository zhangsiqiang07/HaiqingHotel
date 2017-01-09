package cn.xiaocool.haiqinghotel.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.adapter.HomeOnsaleListAdapter;
import cn.xiaocool.haiqinghotel.adapter.lib.CycleViewPager;
import cn.xiaocool.haiqinghotel.adapter.lib.ViewFactory;
import cn.xiaocool.haiqinghotel.bean.ADInfo;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.main.homepage.CalenderMainActivity;
import cn.xiaocool.haiqinghotel.main.homepage.ContactUsActivity;
import cn.xiaocool.haiqinghotel.main.homepage.HomeReserveNowActivity;
import cn.xiaocool.haiqinghotel.main.homepage.HotelInfoActivity;
import cn.xiaocool.haiqinghotel.main.homepage.SearchActivity;
import cn.xiaocool.haiqinghotel.net.request.HomepageRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import cn.xiaocool.haiqinghotel.ui.list.PullToRefreshBase;
import cn.xiaocool.haiqinghotel.ui.list.PullToRefreshListView;
import cn.xiaocool.haiqinghotel.utils.IntentUtils;
import cn.xiaocool.haiqinghotel.utils.ToastUtils;

/**
 * Created by wzh on 2016/4/28.
 */
public class HomePageFragment extends Fragment implements View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private LinearLayout llSearch;
    private ImageLoader imageLoader;
    private Date date;
    private TextView viewpage;
    private RelativeLayout btnLocation, btnContact, btn_Details, rlInday, rlOutday;
    private Context context;
    private PullToRefreshListView lv_comprehensive;
    private ListView onsaleList;
    private Button btnReserveNow;
    private TextView tvInday, tvOutday, tvdaycount;
    private HomeOnsaleListAdapter homeOnsaleListAdapter;
    private String[] picName, name, intro, price, id, type, slide_name, slide_pic;
    private long msInDay, msOutDay;
    private ArrayList<HashMap<String, Object>> arrayList;
    private String textInday;
    private String textOutday;
    private View viewH = null;
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager;
    private SliderLayout mSlider;
    private HashMap<String, String> file_maps;

    private String[] imageUrls = {"http://hq.xiaocool.net/uploads/microblog/sp1.jpg",
            "http://hq.xiaocool.net/uploads/microblog/sp2.jpg",
            "http://hq.xiaocool.net/uploads/microblog/sp3.jpg",
            "http://hq.xiaocool.net/uploads/microblog/sp4.jpg"
    };
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.ONSALE_LIST:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    Log.e("jsonObject 获取成功", jsonObject.toString());
                    if (NetUtil.isConnnected(context)) {
                        try {
                            String status = jsonObject.getString("status");
                            if (status.equals("success")) {
                                arrayList.clear();
                                JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                                Log.e("data",jsonObject.getString("data"));
                                JSONObject object;
                                int length = jsonArray.length();
                                picName = new String[length];
                                name = new String[length];
                                intro = new String[length];
                                price = new String[length];
                                id = new String[length];
                                type = new String[length];
                                for (int i = 0; i < length; i++) {
                                    object = (JSONObject) jsonArray.get(i);
                                    picName[i] = object.getString("picture");
                                    name[i] = object.getString("name");
                                    intro[i] = object.getString("type");
                                    price[i] = object.getString("price");
                                    id[i] = object.getString("id");
                                    type[i] = object.getString("type");
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("picName", picName[i]);
                                    hashMap.put("name", name[i]);
                                    hashMap.put("intro", intro[i]);
                                    hashMap.put("price", price[i]);
                                    hashMap.put("id", id[i]);
                                    hashMap.put("type", type[i]);
                                    arrayList.add(hashMap);
                                }
                                homeOnsaleListAdapter = new HomeOnsaleListAdapter(context, arrayList);
                                onsaleList.setAdapter(homeOnsaleListAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "网络连接有问题", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case CommunalInterfaces.ONPIC_LIST:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    Log.e("jsonObject 获取成功", jsonObject1.toString());
                    if (NetUtil.isConnnected(context)) {
                        try {
                            String status = jsonObject1.getString("status");
                            if (status.equals("success")) {
                                JSONArray jsonArray = (JSONArray) jsonObject1.get("data");
                                JSONObject object;
                                int length = jsonArray.length();
                                slide_name = new String[length];
                                slide_pic = new String[length];
                                file_maps = new HashMap<>();
                                for (int i = 0; i < length; i++) {
                                    object = (JSONObject) jsonArray.get(i);
                                    slide_name[i] = object.optString("slide_name");
                                    slide_pic[i] = object.optString("slide_pic");
                                    Log.e("hello------", slide_name[i]);
                                    Log.e("hello-=----", slide_pic[i]);
                                    file_maps.put(slide_name[i], "http://hq.xiaocool.net" + slide_pic[i]);
                                }
                                setImages(file_maps);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "网络连接有问题", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    private long dayCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        lv_comprehensive = (PullToRefreshListView) view
                .findViewById(R.id.lv_comprehensive);
        lv_comprehensive.setPullLoadEnabled(true);
        lv_comprehensive.setScrollLoadEnabled(false);
        onsaleList = lv_comprehensive.getRefreshableView();
        lv_comprehensive.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (NetUtil.isConnnected(getActivity()) == true) {

                    new HomepageRequest(context, handler).onsaleList();
                } else {
                    ToastUtils.ToastShort(getActivity(), "暂无网络");
                }
                /**
                 * 过1秒结束下拉刷新
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lv_comprehensive.onPullDownRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lv_comprehensive.onPullUpRefreshComplete();
                    }
                }, 1000);
            }
        });
        arrayList = new ArrayList<>();
        viewH = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_homepage_top,
                null);
        //图片轮播控件
        mSlider = (SliderLayout) viewH.findViewById(R.id.slider);
        //设置图片轮播控件图片
        new HomepageRequest(context, handler).onPicList();
        onsaleList.addHeaderView(viewH);
        return view;
    }

    private void setImages(HashMap<String, String> file_maps) {
        /*HashMap<String, String> file_maps = new HashMap<String, String>();
        file_maps.put("餐饮环境", "http://hq.xiaocool.net/uploads/microblog/sp1.jpg");
        file_maps.put("公共泳池", "http://hq.xiaocool.net/uploads/microblog/sp2.jpg");
        file_maps.put("前台环境", "http://hq.xiaocool.net/uploads/microblog/sp3.jpg");
        file_maps.put("整体风格", "http://hq.xiaocool.net/uploads/microblog/sp4.jpg");*/

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mSlider.addSlider(textSliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        new HomepageRequest(context, handler).onsaleList();
        imageLoader = ImageLoader.getInstance();
        initView();
        configImageLoader();
        //initialize();
    }


    //    private void setItemOnclick() {
//        onsaleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                HashMap<String, Object> intentMap = (HashMap<String, Object>) homeOnsaleListAdapter.getItem(position);
//                String goodId = (String) intentMap.get("id");
//                String goodName = (String) intentMap.get("name");
//                String type = (String) intentMap.get("type");
//                if (type.equals("1")) {
//                    Intent intent = new Intent();
//                    intent.setClass(context, RoomIntroActivity.class);
//                    intent.putExtra("roomId", goodId);
//                    intent.putExtra("roomName", goodName);
//                    startActivity(intent);
//                } else {
////                    Toast.makeText(context,"跳转餐饮",Toast.LENGTH_SHORT).show();//此处须跳转餐饮activity
//                    if (type.equals("2")) {
//                        Intent intent = new Intent();
//                        intent.setClass(context, CateringIntroActivity.class);
//                        intent.putEx
//                        intent.putExtra("cateringName", goodName);
//                        startActivity(intent);
//                    }
//                }
//            }
//        });
//    }
    @SuppressLint("NewApi")
    private void initialize() {

        //cycleViewPager = (CycleViewPager) getFragmentManager()
        //.findFragmentById(R.id.fragment_cycle_viewpager_content);

        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            infos.add(info);
        }

        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(context, infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(context, infos.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(context, infos.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        //设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
                Toast.makeText(context,
                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
                        .show();
            }

        }

    };

    /**
     * 配置ImageLoder
     */
    private void configImageLoader() {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub).showImageOnLoading(R.drawable.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }


    private void initView() {
        /*iv_top = (ImageView) getView().findViewById(R.id.homepage_iv_top);
        imageLoader.displayImage("http://hq.xiaocool.net/uploads/microblog/sp1.jpg", iv_top);
        iv_top.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv_top.setAdjustViewBounds(true);*/
        viewpage = (TextView) getView().findViewById(R.id.viewpager);
        viewpage.setOnClickListener(this);
        llSearch = (LinearLayout) getView().findViewById(R.id.homepage_ll_search);
        llSearch.setOnClickListener(this);
        btnLocation = (RelativeLayout) getView().findViewById(R.id.home_btn_location);
        btnLocation.setOnClickListener(this);
        btnContact = (RelativeLayout) getView().findViewById(R.id.home_btn_contact_us);
        btnContact.setOnClickListener(this);
        btn_Details = (RelativeLayout) getView().findViewById(R.id.home_btn_details);
        btn_Details.setOnClickListener(this);
//        onsaleList = (ListView) getView().findViewById(R.id.lv_comprehensive);
        arrayList = new ArrayList<>();
        btnReserveNow = (Button) getView().findViewById(R.id.home_btn_reserve_now);
        btnReserveNow.setOnClickListener(this);
        rlInday = (RelativeLayout) getView().findViewById(R.id.home_rl_inputDate);
        rlInday.setOnClickListener(this);
        rlOutday = (RelativeLayout) getView().findViewById(R.id.home_rl_outputDate);
        rlOutday.setOnClickListener(this);
        tvInday = (TextView) getView().findViewById(R.id.tv_inday);
        tvOutday = (TextView) getView().findViewById(R.id.tv_outday);
        tvdaycount = (TextView) getView().findViewById(R.id.tv_daycount);
        //填充基本数据
        Calendar cal = Calendar.getInstance();

        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        Date dNow = new Date();
        cal.setTime(dNow);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        String indayMonNum = String.valueOf(month);
        String indayDayNum = String.valueOf(day);
        day = cal.get(Calendar.DATE);
        month = cal.get(Calendar.MONTH) + 1;
        year = cal.get(Calendar.YEAR);
        String outdayMonNum = String.valueOf(month);
        String outdayDayNum = String.valueOf(day);
//        //计算毫秒天数
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            msInDay = sdf.parse(String.valueOf(date)).getTime();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            calendar.add(Calendar.DATE, 1);
//            msOutDay = sdf.parse(String.valueOf(calendar.getTime())).getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Log.e("msInDay = ", String.valueOf(msInDay));
//        Log.e("msOutDay = ", String.valueOf(msOutDay));
        dayCount = 1;
        msInDay = new Date().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        msOutDay =calendar.getTime().getTime();
        Log.e("五个数：", indayMonNum + indayDayNum + outdayMonNum + outdayDayNum + "ccc" + dayCount);
        textInday = indayMonNum + "月" + indayDayNum + "日";
        tvInday.setText(indayMonNum + "月" + indayDayNum + "日");
        textOutday = outdayMonNum + "月" + outdayDayNum + "日";
        tvOutday.setText(outdayMonNum + "月" + outdayDayNum + "日");
        tvdaycount.setText(String.valueOf(dayCount) + "天");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            try {
                String indayMonNum = data.getStringExtra("inDayMonth1");
                String indayDayNum = data.getStringExtra("inDayNum0");
                String outdayMonNum = data.getStringExtra("outMonth1");
                String outdayDayNum = data.getStringExtra("outDayNum0");
                dayCount = data.getLongExtra("dayCount", 1);
                Log.e("this daycount", String.valueOf(dayCount));
                msInDay = data.getLongExtra("msInDate", 1);
                msOutDay = data.getLongExtra("msOutDate", 1);
                Log.e("ms in out day is", String.valueOf(msInDay + "bbb" + msOutDay));
                Log.e("五个数：", indayMonNum + indayDayNum + outdayMonNum + outdayDayNum + "ccc" + dayCount);
                textInday = indayMonNum + "月" + indayDayNum + "日";
                textOutday = outdayMonNum + "月" + outdayDayNum + "日";
                tvInday.setText(textInday);
                tvOutday.setText(textOutday);
                tvdaycount.setText(String.valueOf(dayCount) + "天");
                int intPrice = Integer.parseInt(String.valueOf(price));
                int count = (int) (dayCount * intPrice);
                Log.e("count is", String.valueOf(count));
            } catch (Exception ex) {
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_btn_location:
                try {
//                    Intent i = new Intent(
//                            Intent.ACTION_VIEW,Uri.parse("http://ditu.google.cn/maps?hl=zh&mrt=loc&q=31.249351,121.45905"));
                    Intent intent = Intent.getIntent("intent://map/marker?location=36.063906,120.413867&title=青岛海情大酒店&src=青岛海情大酒店|青岛海情大酒店#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                    startActivity(intent);

                } catch (Exception ex) {
                    Toast.makeText(context, "未找到你手机中的百度地图", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(
                            Intent.ACTION_VIEW, Uri.parse("http://ditu.google.cn/maps?hl=zh&mrt=loc&q=36.063906,120.413867"));
//                            Intent intent = Intent.getIntent("intent://map/marker?location=40.047669,116.313082&title=我的位置&content=青岛海情大酒店&src=青岛海情大酒店|青岛海情大酒店#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                    startActivity(intent);
                }
                //调起百度地图客户端
//                try {
//                    Intent intent = new Intent();
//                    intent = Intent.getIntent("intent://map/marker?location=120.413867,36.063906&title=我的位置&content=青岛海情大酒店&src=青岛海情大酒店|青岛海情大酒店#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
//                    if(isInstallByread("com.baidu.BaiduMap")){
//                        startActivity(intent); //启动调用
//                        Log.e("GasStation", "百度地图客户端已经安装") ;
//                    }else{
//                        Toast.makeText(context,"没有安装百度地图客户端",Toast.LENGTH_SHORT).show();
//                        Log.e("GasStation", "没有安装百度地图客户端") ;
//                    }
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
                break;
            case R.id.home_btn_contact_us:
                IntentUtils.getIntent((Activity) context, ContactUsActivity.class);
                break;
            case R.id.home_btn_details:
                IntentUtils.getIntent((Activity) context, HotelInfoActivity.class);
                break;
            case R.id.home_btn_reserve_now:
                if (msInDay == 0 || msOutDay == 0) {
                    Toast.makeText(context, "请选择日期", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent2 = new Intent();
                    intent2.setClass(context, HomeReserveNowActivity.class);
                    intent2.putExtra("msInDay", msInDay);
                    intent2.putExtra("msOutDay", msOutDay);
                    intent2.putExtra("textCheckIn", "入住：" + textInday);
                    intent2.putExtra("textCheckOut", "离店:" + textOutday);
                    intent2.putExtra("roomId", id);
                    intent2.putExtra("dayCount", dayCount);
                    startActivity(intent2);
                }
//                IntentUtils.getIntent((Activity) context, HomeReserveNowActivity.class);
                break;
            case R.id.home_rl_inputDate:
                Intent intent0 = new Intent(context, CalenderMainActivity.class);
                startActivityForResult(intent0, 1);
                break;
            case R.id.home_rl_outputDate:
//                Intent intent1 = new Intent();
                break;
            case R.id.homepage_ll_search:
                Intent intent3 = new Intent();
                intent3.setClass(context, SearchActivity.class);
                intent3.putExtra("msInDay", msInDay);
                intent3.putExtra("msOutDay", msOutDay);
                intent3.putExtra("textCheckIn", "入住：" + textInday);
                intent3.putExtra("textCheckOut", "离店:" + textOutday);
                intent3.putExtra("roomId", id);
                intent3.putExtra("dayCount", dayCount);
                startActivity(intent3);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
