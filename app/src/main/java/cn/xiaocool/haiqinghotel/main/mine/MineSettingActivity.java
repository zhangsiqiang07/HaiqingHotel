package cn.xiaocool.haiqinghotel.main.mine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.app.ExitApplication;
import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.main.LoginActivity;
import cn.xiaocool.haiqinghotel.main.MainActivity;
import cn.xiaocool.haiqinghotel.utils.IntentUtils;
import cn.xiaocool.haiqinghotel.utils.ToastUtils;

/**
 * Created by wzh on 2016/5/22.
 */
public class MineSettingActivity extends Activity implements View.OnClickListener {
    private Button btnExit;
    private TextView tvTitle;
    private Context mContext;
    private LinearLayout llPassword,llClean,llAbout;
    private RelativeLayout btn_back;
    private UserInfo user;
    private SharedPreferences sp;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mine_setting);
        mContext=this;
        user = new UserInfo();
        ExitApplication.getInstance().addActivity(this);
        sp = mContext.getSharedPreferences("list", mContext.MODE_PRIVATE);
        dialog = new ProgressDialog(mContext, AlertDialog.THEME_HOLO_LIGHT);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitApplication.getInstance().removeActivity(this);
    }
    private void initView() {
        btn_back = (RelativeLayout)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        btnExit = (Button) findViewById(R.id.btn_mine_quit);
        btnExit.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText(this.getString(R.string.mine_title_setting));
        llPassword = (LinearLayout) findViewById(R.id.mine_ll_password);
        llPassword.setOnClickListener(this);
        llClean = (LinearLayout) findViewById(R.id.mine_ll_clean);
        llClean.setOnClickListener(this);
        llAbout = (LinearLayout) findViewById(R.id.mine_ll_about);
        llAbout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_ll_password:
                IntentUtils.getIntent(this,ChangePasswordActivity.class);
                break;
            case R.id.mine_ll_clean:
                ToastUtils.ToastShort(mContext, "缓存已清理！");
                break;
            case R.id.mine_ll_about:
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_mine_quit:
                user.clearDataExceptPhone(mContext);
                SharedPreferences.Editor e=sp.edit();
                e.clear();
                e.commit();
                IntentUtils.getIntent(MineSettingActivity.this, MainActivity.class);
                MineSettingActivity.this.finish();
                ExitApplication.getInstance().exit();
                user.setUserId(null);
                break;
        }
    }
    /**
     * 退出确认对话框
     */
    private void getDialog() {
        LayoutInflater inflaterDl = LayoutInflater.from(MineSettingActivity.this);
        FrameLayout layout = (FrameLayout) inflaterDl.inflate(
                R.layout.quit_dialog, null);

        // 对话框
        final Dialog dialog = new AlertDialog.Builder(MineSettingActivity.this)
                .create();
        dialog.show();
        dialog.getWindow().setContentView(layout);

        // 取消按钮
        Button btnCancel = (Button) layout.findViewById(R.id.dialog_ok);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.clearDataExceptPhone(mContext);
                SharedPreferences.Editor e=sp.edit();
                e.clear();
                e.commit();
                IntentUtils
                        .getIntent(MineSettingActivity.this, LoginActivity.class);
                MineSettingActivity.this.finish();
                ExitApplication.getInstance().exit();
            }
        });

        // 确定按钮
        Button btnOK = (Button) layout.findViewById(R.id.dialog_cancel);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}