package fun.flyee.sunshine4u.android.jar;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import fun.flyee.sunshine4u.android.R;


/**
 * 开屏页面
 *
 * @pengqian
 */
public class DownActivity extends AppCompatActivity {
    private String url = "";
    private static Context ctx;

    private static double totalSize = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jar_activity_down);
        ctx = this;
        url = getIntent().getStringExtra("url");


        downLoadFile(url);
    }

    public void downLoadFile(String url) {
        Log.i("下载","apk地址："+url);
        InstallUtils.updateApk(this,url);
    }

}
