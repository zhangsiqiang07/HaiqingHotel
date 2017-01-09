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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.adapter.MineRoomOrderAdapter;
import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.request.MineRequest;

/**
 * Created by wzh on 2016/5/21.
 */
public class MineOrderRoomFragment extends Fragment implements View.OnClickListener {
    private Context context;

    private ListView listView;
    private ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    private String[] orderNum, roomType, peoName, pic, name, count, price, time, remark, phoNum, begintime, endtime, arrivetime, one_price;
    private RelativeLayout btnback;
    private TextView quxiao;
    private UserInfo userInfo;
    private MineRoomOrderAdapter mineRoomOrderAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.MINE_ROOM_ORDER:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            orderNum = new String[length];
                            roomType = new String[length];
                            pic = new String[length];
                            peoName = new String[length];
                            name = new String[length];
                            count = new String[length];
                            price = new String[length];
                            time = new String[length];
                            remark = new String[length];
                            phoNum = new String[length];
                            begintime = new String[length];
                            endtime = new String[length];
                            arrivetime = new String[length];
                            one_price = new String[length];
                            for (int i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                orderNum[i] = dataObject.getString("order_num");
                                pic[i] = dataObject.getString("picture");
                                name[i] = dataObject.getString("name");
                                peoName[i] = dataObject.getString("peoplename");
                                count[i] = dataObject.getString("number");
                                price[i] = dataObject.getString("money");
                                time[i] = dataObject.getString("time");
                                remark[i] = dataObject.getString("remarks");
                                phoNum[i] = dataObject.getString("mobile");
                                begintime[i] = dataObject.getString("begintime");
                                endtime[i] = dataObject.getString("endtime");
                                arrivetime[i] = dataObject.getString("arrivetime");
//                                one_price[i] = dataObject.optString("price");
//
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("orderNum", orderNum[i]);
                                hashMap.put("pic", pic[i]);
                                hashMap.put("name", name[i]);
                                hashMap.put("peoName", peoName[i]);
                                hashMap.put("count", count[i]);
                                hashMap.put("price", price[i]);
                                hashMap.put("time", time[i]);
                                hashMap.put("remark", remark[i]);
                                hashMap.put("phoNum", phoNum[i]);
                                hashMap.put("begintime", begintime[i]);
                                hashMap.put("endtime", endtime[i]);
                                hashMap.put("arrivetime", arrivetime[i]);
//                                hashMap.put("one_price", one_price[i]);
                                arrayList.add(hashMap);
                            }
                            mineRoomOrderAdapter = new MineRoomOrderAdapter(context, arrayList, handler);
                            listView.setAdapter(mineRoomOrderAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.CANCEL_ORDER:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")) {
                            Log.e("删除订单成功", "success");
                            Toast.makeText(context, "删除订单成功", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_room_order, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        userInfo = new UserInfo(context);
        userInfo.readData(context);
        initView();
        new MineRequest(context, handler).myRoomOrder();

    }

    private void initView() {
        listView = (ListView) getView().findViewById(R.id.lv_mine_roomorder);

//        setItemClick();
    }
//
//    private void setItemClick() {
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                HashMap<String, Object> hashMap = (HashMap<String, Object>) mineRoomOrderAdapter.getItem(position);
//                String orderNum = (String) hashMap.get("orderNum");//订单号
//                String name = (String) hashMap.get("name");//房型
//                String peoName = (String) hashMap.get("peoName");//入住人
//                String count = (String) hashMap.get("count");//数量
//                String remark = (String) hashMap.get("remark");//备注
//                String phoNum = (String) hashMap.get("phoNum");// 联系方式
//                Intent intent = new Intent();
//                intent.putExtra("orderNum", orderNum);
//                intent.putExtra("name", name);
//                intent.putExtra("peoName", peoName);
//                intent.putExtra("count", count);
//                intent.putExtra("remark", remark);
//                intent.putExtra("phoNum", phoNum);
//                intent.setClass(context, MineRoomDetailsActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
