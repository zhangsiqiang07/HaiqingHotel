package cn.xiaocool.haiqinghotel.main.homepage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.ui.MyCalendar;

/**
 * @author
 */
@SuppressLint("SimpleDateFormat")
public class CalenderMainActivity extends Activity implements MyCalendar.OnDaySelectListener {
    LinearLayout ll;
    MyCalendar c1;
    Date date;
    String nowday;
    long nd = 1000 * 24L * 60L * 60L;//一天的毫秒数
    SimpleDateFormat simpleDateFormat, sd1, sd2;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private long dayCount;

    private String inday, outday, sp_inday, sp_outday;
    private String inMonthNum1;
    private String inDayNum0,inDayNum1;
    private String outMonthNum1;
    private String outDayNum0,outDayNum1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("date", Context.MODE_PRIVATE);
        //本地保存
        sp_inday = sp.getString("dateIn", "");
        sp_outday = sp.getString("dateOut", "");
        editor = sp.edit();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        nowday = simpleDateFormat.format(new Date());
        sd1 = new SimpleDateFormat("yyyy");
        sd2 = new SimpleDateFormat("dd");
        ll = (LinearLayout) findViewById(R.id.ll);
        init();
    }

    private void init() {
        List<String> listDate = getDateList();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < listDate.size(); i++) {
            c1 = new MyCalendar(this);
            c1.setLayoutParams(params);
            Date date = null;
            try {
                date = simpleDateFormat.parse(listDate.get(i));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (!"".equals(sp_inday)) {
                c1.setInDay(sp_inday);
            }
            if (!"".equals(sp_outday)) {
                c1.setOutDay(sp_outday);
            }
            c1.setTheDay(date);
            c1.setOnDaySelectListener(this);
            ll.addView(c1);
        }
    }

    @Override
    public void onDaySelectListener(View view, String date) {
        //若日历日期小于当前日期，或日历日期-当前日期超过三个月，则不能点击
        try {
            if (simpleDateFormat.parse(date).getTime() < simpleDateFormat.parse(nowday).getTime()) {
                return;
            }
            long dayxc = (simpleDateFormat.parse(date).getTime() - simpleDateFormat.parse(nowday).getTime()) / nd;
            if (dayxc > 90) {
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //若以前已经选择了日期，则在进入日历后会显示以选择的日期，该部分作用则是重新点击日历时，清空以前选择的数据（包括背景图案）
        if (!"".equals(sp_inday)) {
            c1.viewIn.setBackgroundColor(Color.WHITE);
            ((TextView) c1.viewIn.findViewById(R.id.tv_calendar_day)).setTextColor(Color.BLACK);
            ((TextView) c1.viewIn.findViewById(R.id.tv_calendar)).setText("");
        }
        if (!"".equals(sp_outday)) {
            c1.viewOut.setBackgroundColor(Color.WHITE);
            ((TextView) c1.viewOut.findViewById(R.id.tv_calendar_day)).setTextColor(Color.BLACK);
            ((TextView) c1.viewOut.findViewById(R.id.tv_calendar)).setText("");
        }

        String dateDay = date.split("-")[2];
        if (Integer.parseInt(dateDay) < 10) {
            dateDay = date.split("-")[2].replace("0", "");
        }
        TextView textDayView = (TextView) view.findViewById(R.id.tv_calendar_day);
        TextView textView = (TextView) view.findViewById(R.id.tv_calendar);
        view.setBackgroundColor(Color.parseColor("#33B5E5"));
        textDayView.setTextColor(Color.WHITE);
        if (null == inday || inday.equals("")) {
            textDayView.setText(dateDay);
            textView.setText("入住");
            inday = date;
        } else {
            if (inday.equals(date)) {
                view.setBackgroundColor(Color.WHITE);
                textDayView.setText(dateDay);
                textDayView.setTextColor(Color.BLACK);
                textView.setText("");
                inday = "";
            } else {
                try {
                    if (simpleDateFormat.parse(date).getTime() < simpleDateFormat.parse(inday).getTime()) {
                        view.setBackgroundColor(Color.WHITE);
                        textDayView.setTextColor(Color.BLACK);
                        Toast.makeText(CalenderMainActivity.this, "离开日期不能小于入住日期", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                textDayView.setText(dateDay);
                textView.setText("离开");
                outday = date;
                editor.putString("dateIn", inday);
                editor.putString("dateOut", outday);
                editor.commit();

                //计算毫秒天数
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String start = inday;
                String end = outday;
                //提取需要的日期数字
                String inMonthNum0 = StringUtils.substringBetween(start, "-", "-");
                inMonthNum1 = StringUtils.remove(inMonthNum0, "0");
                inDayNum0 = StringUtils.substringAfterLast(start, "-");
                inDayNum1 = StringUtils.remove(inDayNum0, "0");
                String outMonthNum0 = StringUtils.substringBetween(end, "-", "-");
                outMonthNum1 = StringUtils.remove(outMonthNum0, "0");
                outDayNum0 = StringUtils.substringAfterLast(end, "-");
                outDayNum1 = StringUtils.remove(outDayNum0, "0");
                Log.e("inday and outday is", inday + outday);
                Log.e("dayNum and monthNum is", inMonthNum1 + inDayNum0 + outMonthNum1 + outDayNum0);
                //得到毫秒数
                long timeEnd = 0;
                long timeStart = 0;
                try {
                    timeStart = sdf.parse(start).getTime();
                    timeEnd = sdf.parse(end).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //两个日期想减得到天数
                dayCount = (timeEnd - timeStart) / (24 * 3600 * 1000);
                System.out.println("天数相减后是" + dayCount);
                Intent dateIntent = new Intent();
                dateIntent.putExtra("inDayMonth1",inMonthNum1);
                dateIntent.putExtra("inDayNum0",inDayNum0);
                dateIntent.putExtra("outMonth1",outMonthNum1);
                dateIntent.putExtra("outDayNum0",outDayNum0);
                dateIntent.putExtra("dayCount",dayCount);
                //传输毫秒值
                dateIntent.putExtra("msInDate",timeStart);
                dateIntent.putExtra("msOutDate",timeEnd);
                setResult(1,dateIntent);
                finish();
            }
        }
    }

    //根据当前日期，向后数三个月（若当前day不是1号，为满足至少90天，则需要向后数4个月）
    @SuppressLint("SimpleDateFormat")
    public List<String> getDateList() {
        List<String> list = new ArrayList<String>();
        Date date = new Date();
        int nowMon = date.getMonth() + 1;
        String yyyy = sd1.format(date);
        String dd = sd2.format(date);
        if (nowMon == 9) {
            list.add(simpleDateFormat.format(date));
            list.add(yyyy + "-10-" + dd);
            list.add(yyyy + "-11-" + dd);
            if (!dd.equals("01")) {
                list.add(yyyy + "-12-" + dd);
            }
        } else if (nowMon == 10) {
            list.add(yyyy + "-10-" + dd);
            list.add(yyyy + "-11-" + dd);
            list.add(yyyy + "-12-" + dd);
            if (!dd.equals("01")) {
                list.add((Integer.parseInt(yyyy) + 1) + "-01-" + dd);
            }
        } else if (nowMon == 11) {
            list.add(yyyy + "-11-" + dd);
            list.add(yyyy + "-12-" + dd);
            list.add((Integer.parseInt(yyyy) + 1) + "-01-" + dd);
            if (!dd.equals("01")) {
                list.add((Integer.parseInt(yyyy) + 1) + "-02-" + dd);
            }
        } else if (nowMon == 12) {
            list.add(yyyy + "-12-" + dd);
            list.add((Integer.parseInt(yyyy) + 1) + "-01-" + dd);
            list.add((Integer.parseInt(yyyy) + 1) + "-02-" + dd);
            if (!dd.equals("01")) {
                list.add((Integer.parseInt(yyyy) + 1) + "-03-" + dd);
            }
        } else {
            list.add(yyyy + "-" + getMon(nowMon) + "-" + dd);
            list.add(yyyy + "-" + getMon((nowMon + 1)) + "-" + dd);
            list.add(yyyy + "-" + getMon((nowMon + 2)) + "-" + dd);
            if (!dd.equals("01")) {
                list.add(yyyy + "-" + getMon((nowMon + 3)) + "-" + dd);
            }
        }
        return list;
    }

    public String getMon(int mon) {
        String month = "";
        if (mon < 10) {
            month = "0" + mon;
        } else {
            month = "" + mon;
        }
        return month;
    }



}