package cn.xiaocool.haiqinghotel.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.adapter.FacilityListAdapter;
import cn.xiaocool.haiqinghotel.bean.FacilityType;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.request.FacilityRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import cn.xiaocool.haiqinghotel.ui.list.PullToRefreshBase;
import cn.xiaocool.haiqinghotel.ui.list.PullToRefreshListView;
import cn.xiaocool.haiqinghotel.utils.ToastUtils;

/**
 * Created by wzh on 2016/4/28.
 */
public class FacilityFragment extends Fragment implements View.OnClickListener {
    private PullToRefreshListView lv_comprehensive;
    private Context context;
    private ListView listView;
    private ArrayList<FacilityType.FacilityTypeData> facilitytypeDataList;
    private ArrayList<FacilityType.FacilityTypeData> newsDataList;
    private TextView tvTitle;
    private RelativeLayout btnExit;
    private String[]  id ,name, pic;
    private FacilityListAdapter facilityListAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.FACILITY_LIST:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    if (NetUtil.isConnnected(context)) {
                        try {
                            String status = jsonObject.getString("status");
                            if (status.equals("success")) {
                                facilitytypeDataList.clear();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                Log.e("jsonArray",jsonObject.getString("data"));
                                int length = jsonArray.length();
                                JSONObject dataObject;
                                name = new String[length];
                                pic = new String[length];
                                id = new String[length];
                                for (int i = 0; i < length; i++) {
                                    dataObject = (JSONObject) jsonArray.get(i);
                                    FacilityType.FacilityTypeData facilitytypeData = new FacilityType.FacilityTypeData();
                                    facilitytypeData.setTitle(dataObject.getString("name"));
                                    facilitytypeData.setId(dataObject.getString("id"));
                                    facilitytypeData.setPhoto(dataObject.getString("picture"));
                                    facilitytypeData.setType("sheshi");
                                    facilitytypeDataList.add(facilitytypeData);
                                }
                                for (int i = 0;i<newsDataList.size();i++){
                                    facilitytypeDataList.add(newsDataList.get(i));
                                }
                                if (facilityListAdapter!=null){
                                    facilityListAdapter.notifyDataSetChanged();
                                }else {
                                    facilityListAdapter = new FacilityListAdapter(context, facilitytypeDataList);
                                    listView.setAdapter(facilityListAdapter);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case CommunalInterfaces.NEW_LIST:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    if (NetUtil.isConnnected(context)) {
                        try {
                            String status = jsonObject1.getString("status");
                            if (status.equals("success")) {
                                for (int i = 0;i<newsDataList.size();i++){
                                    facilitytypeDataList.remove(newsDataList.get(i));
                                }
                                newsDataList.clear();
                                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                                Log.e("jsonArraynewsDataList",jsonObject1.getString("data"));
                                int length = jsonArray.length();
                                JSONObject dataObject;
                                name = new String[length];
                                pic = new String[length];
                                id = new String[length];
                                for (int i = 0; i < length; i++) {
                                    dataObject = (JSONObject) jsonArray.get(i);
                                    FacilityType.FacilityTypeData facilitytypeData = new FacilityType.FacilityTypeData();
                                    facilitytypeData.setTitle(dataObject.getString("title"));
                                    facilitytypeData.setId(dataObject.getString("id"));
                                    facilitytypeData.setPhoto(dataObject.getString("photo"));
                                    facilitytypeData.setCreate_time(dataObject.optString("creat_time"));
                                    facilitytypeData.setExcerpt(dataObject.optString("excerpt"));
                                    facilitytypeData.setType("zixun");
                                    newsDataList.add(facilitytypeData);
                                }
                                for (int i = 0;i<newsDataList.size();i++){
                                    facilitytypeDataList.add(newsDataList.get(i));
                                }
                                if (facilityListAdapter!=null){
                                    facilityListAdapter.notifyDataSetChanged();
                                }else {
                                    facilityListAdapter = new FacilityListAdapter(context, facilitytypeDataList);
                                    listView.setAdapter(facilityListAdapter);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facility, container, false);
        lv_comprehensive = (PullToRefreshListView) view
                .findViewById(R.id.facility_listview);
        lv_comprehensive.setPullLoadEnabled(true);
        lv_comprehensive.setScrollLoadEnabled(false);
        listView = lv_comprehensive.getRefreshableView();
        lv_comprehensive.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (NetUtil.isConnnected(getActivity()) == true) {
                    new FacilityRequest(context, handler).facilityList();
                    new FacilityRequest(context,handler).newsList();

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

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
        new FacilityRequest(context, handler).facilityList();
        new FacilityRequest(context,handler).newsList();
//        setOnItemClick();
    }

//    private void setOnItemClick() {
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                HashMap<String, Object> hashMap= (HashMap<String, Object>) facilityListAdapter.getItem(position);
//                String facilityId = (String) hashMap.get("id");
//                String name = (String) hashMap.get("name");
//                Intent intent = new Intent();
//                intent.putExtra("facilityId",facilityId);
//                intent.putExtra("name",name);
//                Log.e("facility id is ",facilityId);
//                intent.setClass(context, FacilityRoomDetailsActivity.class);
//                startActivity(intent);
//
//            }
//        });
//    }

    private void initView() {
        facilitytypeDataList = new ArrayList<FacilityType.FacilityTypeData>();
        newsDataList = new ArrayList<>();
//        listView = (ListView) getView().findViewById(R.id.facility_listview);
        tvTitle = (TextView) getView().findViewById(R.id.top_title);
        tvTitle.setText(this.getString(R.string.facility_title));
        btnExit = (RelativeLayout) getView().findViewById(R.id.btn_back);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
