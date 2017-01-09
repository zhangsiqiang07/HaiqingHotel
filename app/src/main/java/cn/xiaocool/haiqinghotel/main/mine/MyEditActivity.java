package cn.xiaocool.haiqinghotel.main.mine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.UserRequest;
import cn.xiaocool.haiqinghotel.net.request.MineRequest;
import cn.xiaocool.haiqinghotel.ui.MyDatePickerDialog;
import cn.xiaocool.haiqinghotel.utils.LogUtils;
import cn.xiaocool.haiqinghotel.utils.NetBaseUtils;
import cn.xiaocool.haiqinghotel.utils.ToastUtils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 潘 on 2016/5/26.
*/
public class MyEditActivity extends Activity implements View.OnClickListener{
    private EditText my_edit_city;
    private EditText my_edit_email;
    private EditText my_edit_name;
    private TextView my_edit_sex,tvSubmit,my_edit_birthday;
    private String head = null;
    private CircleImageView set_head_img;
    private Context mContext;
    private UserInfo user;
    private RelativeLayout btnBack;
    private String name,sex,email,city,birthday,img;
    // 保存的文件的路径
    @SuppressLint("SdCardPath")
    private String filepath = "/sdcard/myheader";
    private String picname = "newpic";
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private static final int KEY = 4;
    private String path;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                case CommunalInterfaces.MY_INFOR:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try{
                        String status = jsonObject1.getString("status");
                        if(status.equals("success")){
                            JSONObject data = jsonObject1.optJSONObject("data");
                            name = data.optString("name");
                            sex = data.optString("sex");
                            email = data.optString("email");
                            city = data.optString("city");
                            birthday = data.optString("birthday");
                            img = data.optString("photo");
                        }
                        if(sex.equals("0")){
                            my_edit_sex.setText("女");
                        }else if(sex.equals("1")){
                            my_edit_sex.setText("男");
                        }
                        my_edit_name.setText(name);
                        my_edit_email.setText(email);
                        my_edit_city.setText(city);
                        my_edit_birthday.setText(birthday);
                        ImageLoader.getInstance().displayImage("http://hq.xiaocool.net/uploads/microblog/"+img,set_head_img);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    break;
                case KEY:
                    Log.e("111","111");
                    String key = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key);
                        String state=json.getString("status");
                        if (state.equals("success")) {
                            JSONObject js=json.getJSONObject("data");
                            path=json.getString("data");
                            LogUtils.i("UserRequestddddddddddddddd", "修改用户的头像--->" + user.getUserImg());
                            LogUtils.i("UserRequestddddddddddddddd", "修改用户的头像--->" + path);
                            user.setUserImg(path);
                            user.writeData(mContext);
                            ToastUtils.ToastShort(mContext, "头像上传成功");
                        }else{
                            ToastUtils.ToastShort(mContext, json.getString("data"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.CHANGE_INFO:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")){
                            ToastUtils.ToastShort(mContext,"修改资料成功！");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_edit);
        mContext=this;
        initdata();
        user = new UserInfo();
        user.readData(mContext);
        Log.e("userid--------",user.getUserId());
        new MineRequest(this,handler).myInfor();
    }

    private void initdata() {
        //设置头像
        set_head_img = (CircleImageView) findViewById(R.id.set_head_img);
        set_head_img.setOnClickListener(this);
        my_edit_sex = (TextView)findViewById(R.id.my_edit_sex);
        my_edit_sex.setOnClickListener(this);
        my_edit_name = (EditText)findViewById(R.id.my_edit_name);
        my_edit_email = (EditText)findViewById(R.id.my_edit_age);
        my_edit_city = (EditText)findViewById(R.id.my_edit_city);
        tvSubmit = (TextView) findViewById(R.id.my_edit_submit);
        tvSubmit.setOnClickListener(this);
        btnBack = (RelativeLayout) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        my_edit_birthday = (TextView) findViewById(R.id.my_edit_birthday);
        my_edit_birthday.setOnClickListener(this);
    }
    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        Date myData = new Date();
        cal.setTime(myData);

        //获取系统的时间
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int minute = cal.get(Calendar.MINUTE);
        final int second = cal.get(Calendar.SECOND);

        Log.e("MONTH", "year" + year);
        Log.e("MONTH", "month" + month);
        Log.e("MONTH", "day" + day);
        Log.e("MONTH", "hour" + hour);
        Log.e("MONTH", "minute" + minute);
        Log.e("MONTH", "second" + second);

        MyDatePickerDialog dlg = new MyDatePickerDialog(MyEditActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e("MONTH", "monthOfYear" + monthOfYear);
                monthOfYear += 1;//monthOfYear 从0开始

                String data = year + "-" + monthOfYear + "-" + dayOfMonth;
                my_edit_birthday.setText(data);
//                        String data_new = dataOne(data + "-" + hour + "-" + minute + "-" + second);

                //时分秒用0代替
                /*String data_new = dataOne(data + "-" + hour + "-" + minute + "-" + second);
                Log.e("--444444---", data_new);
                begintime = data_new;*/

            }
        }, year, month, day);
        dlg.show();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_head_img:
                ShowPickDialog();
                break;
            case R.id.my_edit_sex:
                ShowSexDialog();
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.my_edit_birthday:
                showDatePicker();
                break;
            case R.id.my_edit_submit:
                String etName = my_edit_name.getText().toString();
                String etSex;
                if(my_edit_sex.getText().toString().equals("女")){
                    etSex = "0";
                }else{
                    etSex = "1";
                }
                String etEmail = my_edit_email.getText().toString();
                String etCity = my_edit_city.getText().toString();
                String etBirthday =my_edit_birthday.getText().toString();
                picname = picname+".jpg";
                new MineRequest(this,handler).changeInfo(etName,etSex,etBirthday,etEmail,etCity,picname);
                break;
        }
    }

    private void ShowSexDialog() {
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT).setNegativeButton("男", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                my_edit_sex.setText("男");

            }
        }).setPositiveButton("女", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                my_edit_sex.setText("女");

            }
        }).show();
    }


    protected void ShowPickDialog() {
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT).setNegativeButton("相册", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intentFromGallery = new Intent();
                intentFromGallery.setType("image/*"); // 设置文件类型
                intentFromGallery.setAction(Intent.ACTION_PICK);
                startActivityForResult(intentFromGallery, PHOTO_REQUEST_ALBUM);

            }
        }).setPositiveButton("拍照", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用，可用进行存储
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    File file = new File(path, "newpic.jpg");
                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                }

                startActivityForResult(intentFromCapture, PHOTO_REQUEST_CAMERA);
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case PHOTO_REQUEST_CAMERA:// 相册
                    // 判断存储卡是否可以用，可用进行存储
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        File tempFile = new File(path, "newpic.jpg");
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(getApplicationContext(), "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PHOTO_REQUEST_ALBUM:// 图库
                    startPhotoZoom(data.getData());
                    break;

                case PHOTO_REQUEST_CUT: // 图片缩放完成后
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(this.getResources(), photo);
            set_head_img.setImageDrawable(drawable);
            picname = "avatar"+user.getUserId()+String.valueOf(new Date().getTime());
            storeImageToSDCARD(photo, picname, filepath);
            if(NetBaseUtils.isConnnected(mContext)){
                new UserRequest(mContext,handler).uploadavatar(head,KEY);
            }else {
                Toast.makeText(mContext,"网络问题，请稍后再试！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * storeImageToSDCARD 将bitmap存放到sdcard中
     * */
    public void storeImageToSDCARD(Bitmap colorImage, String ImageName, String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        File imagefile = new File(file, ImageName + ".jpg");
        try {
            imagefile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imagefile);
            colorImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            head = imagefile.getPath();
            user.setUserImg(head);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
