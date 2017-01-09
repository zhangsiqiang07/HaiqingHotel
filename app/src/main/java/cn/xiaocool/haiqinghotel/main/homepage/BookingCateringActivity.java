package cn.xiaocool.haiqinghotel.main.homepage;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.main.MainActivity;
import cn.xiaocool.haiqinghotel.main.mine.MyOrderActivity;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;
import cn.xiaocool.haiqinghotel.net.request.HomepageRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import cn.xiaocool.haiqinghotel.ui.MyDatePickerDialog;
import cn.xiaocool.haiqinghotel.ui.ReboundScrollView;
import cn.xiaocool.haiqinghotel.utils.IntentUtils;

/**
 * Created by wzh on 2016/5/16.
 */
public class BookingCateringActivity extends Activity implements View.OnClickListener {
    private PopupWindow mPopupWindow;
    private TextView tvAdd, tvSub, tvNumber, tvTime;
    private int num = 1;
    private int count, totalPrice;
    private Button btnReserve;
    private TextView tv_title, tvCateringName, tvCateringPrice, tvSubTotal, tvTotalPrice, tvBottomPrice, tv_date;
    private EditText etName, etPhoNum, etRemark, etRoomNum;
    private RelativeLayout rlExit;
    private String cateringName, cateringPrice, cateringId,pic;
    private UserInfo user;
    private Context mContext;
    private ImageView catering_img;
    private ReboundScrollView scrollView;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.RESERVE_CATERING:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    Log.e("jsonobject", jsonObject.toString()
                    );
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            Toast.makeText(BookingCateringActivity.this, "预订成功！", Toast.LENGTH_SHORT).show();
                            if (user.getUserId().equals("0")) {
                                showPopupMenu();
                            } else {
//                                IntentUtils.getIntent((Activity) mContext, MyOrderActivity.class);
                                Intent intent = new Intent(mContext,MyOrderActivity.class);
                                intent.putExtra("type",1);
                                startActivity(intent);
                            }


//                            finish();
                        } else {
                            Toast.makeText(BookingCateringActivity.this, "预订失败！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_booking_catering);
        Intent intent = getIntent();
        cateringName = intent.getStringExtra("cateringName");
        cateringPrice = intent.getStringExtra("cateringPrice");
        cateringId = intent.getStringExtra("cateringId");
        pic = intent.getStringExtra("cateringPic");
        Log.e("intent success", cateringName + cateringPrice);
        mContext = this;
        user = new UserInfo(mContext);
        totalPrice = Integer.parseInt(cateringPrice);
        initView();
    }

    private void initView() {

        scrollView = (ReboundScrollView) findViewById(R.id.scrollView);
        catering_img = (ImageView) findViewById(R.id.catering_img);
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_loading)
                .showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();

        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + pic, catering_img, displayImageOptions);
        tv_date = (TextView) findViewById(R.id.tv_catering_date);
        tv_date.setOnClickListener(this);
        tvAdd = (TextView) findViewById(R.id.tv_add);
        tvAdd.setOnClickListener(this);
        tvSub = (TextView) findViewById(R.id.tv_sub);
        tvSub.setOnClickListener(this);
        tvNumber = (TextView) findViewById(R.id.tv_number);
        tvNumber.setText(String.valueOf(num));
        tv_title = (TextView) findViewById(R.id.top_title);
        tv_title.setText(this.getString(R.string.home_btn_reserve_now));
        rlExit = (RelativeLayout) findViewById(R.id.btn_back);
        rlExit.setOnClickListener(this);
        tvCateringName = (TextView) findViewById(R.id.tv_cateringName);
        tvCateringPrice = (TextView) findViewById(R.id.tv_cateringPrice);
        tvCateringName.setText(cateringName);
        tvCateringPrice.setText("¥" + cateringPrice + "/份");
        tvSub = (TextView) findViewById(R.id.tv_subTotal);
        tvSub.setText("¥" + cateringPrice);
        tvTotalPrice = (TextView) findViewById(R.id.tv_totalPrice);
        tvTotalPrice.setText("¥" + cateringPrice);
        tvNumber = (TextView) findViewById(R.id.tv_number);
        tvBottomPrice = (TextView) findViewById(R.id.tv_bottom_total_price);
        tvBottomPrice.setText("¥" + cateringPrice);
        etName = (EditText) findViewById(R.id.et_catering_name);
        etName.setText(user.getUserName());
        etPhoNum = (EditText) findViewById(R.id.et_catering_phoNum);
        etPhoNum.setText(user.getUserPhone());
        etRemark = (EditText) findViewById(R.id.et_remark);
//        tvTime = (TextView) findViewById(R.id.tv_catering_time);
//        tvTime.setOnClickListener(this);
        etRoomNum = (EditText) findViewById(R.id.et_roomNum);
        btnReserve = (Button) findViewById(R.id.btn_reserve_catering_now);
        btnReserve.setOnClickListener(this);

        final Calendar cal = Calendar.getInstance();
        Date myData = new Date();
        cal.setTime(myData);

        //获取系统的时间
        final int todayyear = cal.get(Calendar.YEAR);
        final int todaymonth = cal.get(Calendar.MONTH)+1;
        final int todayday = cal.get(Calendar.DAY_OF_MONTH);

        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int minute = cal.get(Calendar.MINUTE);
        final int second = cal.get(Calendar.SECOND);
//
//        Log.e("MONTH", "year" + year);
//        Log.e("MONTH", "month" + month);
//        Log.e("MONTH", "day" + day);
        Log.e("MONTH", "hour" + hour);
        Log.e("MONTH", "minute" + minute);
        Log.e("MONTH", "second" + second);
        Log.e("=====",todayyear + "-" + todaymonth + "-" + todayday);
        tv_date.setText(todayyear + "-" + todaymonth + "-" + todayday);

        scrollView.post(new Runnable() {
            public void run() {

                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    /**
     * 未登录时，显示选择菜单
     */
    private void showPopupMenu() {

        //自定义布局
        View layout = LayoutInflater.from(mContext).inflate(R.layout.pop_not_login_book, null);
        //初始化popwindow
        final PopupWindow popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //设置弹出位置
        popupWindow.showAtLocation(btnReserve, Gravity.BOTTOM, 0, 0);

        final RelativeLayout back_home = (RelativeLayout) layout.findViewById(R.id.back_home);
        RelativeLayout phone_tell = (RelativeLayout) layout.findViewById(R.id.phone_tell);
        RelativeLayout btn_exit = (RelativeLayout) layout.findViewById(R.id.btn_exit);


        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = BookingCateringActivity.this.getWindow().getAttributes();
        lp.alpha = 0.7f;
        BookingCateringActivity.this.getWindow().setAttributes(lp);
        //监听popwindow消失事件，取消遮盖层
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1.0f;
                BookingCateringActivity.this.getWindow().setAttributes(lp);
            }
        });

        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backhome();
            }
        });
        phone_tell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone();
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });


    }


    /**
     * 拨打酒店电话
     */
    private void phone() {
        Log.e("whywhuy", "sssssssssss");
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + "053285969888");
        intent.setData(data);
        startActivity(intent);
    }


    /**
     * 回主页
     */
    private void backhome() {
        IntentUtils.getIntents(mContext, MainActivity.class);
    }

    private void showTimePopupWindow() {
        View contentView = LayoutInflater.from(BookingCateringActivity.this).inflate(R.layout.catering_popuplayout, null);
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        TextView tv1 = (TextView) contentView.findViewById(R.id.pop_roomCount1);
        TextView tv2 = (TextView) contentView.findViewById(R.id.pop_roomCount2);
        TextView tv3 = (TextView) contentView.findViewById(R.id.pop_roomCount3);
        TextView tv4 = (TextView) contentView.findViewById(R.id.pop_roomCount4);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);

        View rootview = LayoutInflater.from(BookingCateringActivity.this).inflate(R.layout.home_booking_catering, null);
        mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }

    private void showDatePicker() {
        final Calendar cal = Calendar.getInstance();
        Date myData = new Date();
        cal.setTime(myData);

        //获取系统的时间
        final int todayyear = cal.get(Calendar.YEAR);
        final int todaymonth = cal.get(Calendar.MONTH);
        final int todayday = cal.get(Calendar.DAY_OF_MONTH);

        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int minute = cal.get(Calendar.MINUTE);
        final int second = cal.get(Calendar.SECOND);
//
//        Log.e("MONTH", "year" + year);
//        Log.e("MONTH", "month" + month);
//        Log.e("MONTH", "day" + day);
        Log.e("MONTH", "hour" + hour);
        Log.e("MONTH", "minute" + minute);
        Log.e("MONTH", "second" + second);
        Log.e("=====",todayyear + "-" + todaymonth + "-" + todayday);
        tv_date.setText(todayyear + "-" + todaymonth + "-" + todayday);
        MyDatePickerDialog dlg = new MyDatePickerDialog(BookingCateringActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e("MONTH", "monthOfYear" + monthOfYear);
                monthOfYear += 1;//monthOfYear 从0开始
                String data = year + "-" + monthOfYear + "-" + dayOfMonth;
                Log.e("data", data);
                Log.e("data",todayyear + "-" + todaymonth + "-" + todayday);

                if (year < todayyear) {
                    Toast.makeText(mContext, "请输入正确的时期", Toast.LENGTH_SHORT).show();
                    tv_date.setText(todayyear + "-" + todaymonth + "-" + todayday);
                } else if (year == todayyear && monthOfYear < todaymonth) {
                    Toast.makeText(mContext, "请输入正确的时期", Toast.LENGTH_SHORT).show();
                    tv_date.setText(todayyear + "-" + todaymonth + "-" + todayday);
                } else if (year == todayyear && monthOfYear == todaymonth && dayOfMonth < todayday) {
                    Toast.makeText(mContext, "请输入正确的时期", Toast.LENGTH_SHORT).show();
                    tv_date.setText(todayyear + "-" + todaymonth + "-" + todayday);
                } else {
                    tv_date.setText(data);
                }

//                        String data_new = dataOne(data + "-" + hour + "-" + minute + "-" + second);

                //时分秒用0代替
                /*String data_new = dataOne(data + "-" + hour + "-" + minute + "-" + second);
                Log.e("--444444---", data_new);
                begintime = data_new;*/

            }
        }, todayyear, todaymonth, todayday);

        dlg.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                if (num < 9) {
                    num += 1;
                }
//                count = Integer.valueOf(tvNumber.getText().toString()).intValue();
                totalPrice = num * (Integer.valueOf(cateringPrice).intValue());
                tvTotalPrice.setText("¥" + totalPrice);
                tvBottomPrice.setText("¥" + totalPrice);
                tvNumber.setText(String.valueOf(num));
                break;
            case R.id.tv_sub:
                if (num > 0) {
                    num -= 1;
                }
//                count = Integer.valueOf(tvNumber.getText().toString()).intValue();
                totalPrice = num * (Integer.valueOf(cateringPrice).intValue());
                tvTotalPrice.setText("¥" + totalPrice);
                tvBottomPrice.setText("¥" + totalPrice);
                tvNumber.setText(String.valueOf(num));
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_reserve_catering_now:
                String price = tvBottomPrice.getText().toString();
                String name = etName.getText().toString();
                String count = tvNumber.getText().toString();
                String phoNum = etPhoNum.getText().toString();
                String remark = etRemark.getText().toString();
//                String time = tvTime.getText().toString();
                String roomNum = etRoomNum.getText().toString();
                String date = tv_date.getText().toString();
                Log.e("date--------", date);
                if (NetUtil.isConnnected(this)) {
                    user.readData(mContext);
                    if (user.getUserId().equals("")) {
                        user.setUserId("0");
                    }
                    if (etPhoNum.getText().length() != 11) {
                        Toast.makeText(mContext, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                    } else if (etRoomNum.getText().length()<1){
                        Toast.makeText(mContext, "请输入房间号", Toast.LENGTH_SHORT).show();
                    }else if (tv_date.getText().length()<1){
                        Toast.makeText(mContext, "请选择用餐日期", Toast.LENGTH_SHORT).show();
                    }else {
                        new HomepageRequest(this, handler).reserveCatering(Integer.parseInt(user.getUserId()), cateringId, null, name, count
                                , phoNum, remark, price, date,roomNum, String.valueOf(totalPrice));
                    }

                }
                break;
//            case R.id.tv_catering_time:
//                showTimePopupWindow();
//                break;
            case R.id.pop_roomCount1:
                tvTime.setText("6:00前");
                mPopupWindow.dismiss();
                break;
            case R.id.pop_roomCount2:
                tvTime.setText("8:00前");
                mPopupWindow.dismiss();
                break;
            case R.id.pop_roomCount3:
                tvTime.setText("10:00前");
                mPopupWindow.dismiss();
                break;
            case R.id.pop_roomCount4:
                tvTime.setText("12:00前");
                mPopupWindow.dismiss();
                break;
            case R.id.tv_catering_date:
                showDatePicker();
        }
    }
}
