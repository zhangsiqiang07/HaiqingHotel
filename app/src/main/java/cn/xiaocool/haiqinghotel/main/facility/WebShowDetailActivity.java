package cn.xiaocool.haiqinghotel.main.facility;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;


public class WebShowDetailActivity extends Activity {

    private String itemid, title,type;
    private TextView title_bar_name;
    private WebView webView;
    private ImageView btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_teacher_info_web_detail);
        initview();
    }

    private void initview() {
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        itemid = getIntent().getStringExtra("facilityId");
        title = getIntent().getStringExtra("Title");
        title_bar_name = (TextView) findViewById(R.id.title_bar_name);
        webView = (WebView) findViewById(R.id.webView);
        title_bar_name.setText(title);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        String a = "index";


        webView.loadUrl(NetBaseConstant.NET_H5_HOST + "&a="+a+"&id="+itemid);
        Log.e("webView.loadUrl", NetBaseConstant.NET_H5_HOST + "&a="+a+"&id="+itemid);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                //返回的是true，说明是使用webview打开的网页。否则使用系统默认的浏览器
                return true;
            }
        });



    }
}