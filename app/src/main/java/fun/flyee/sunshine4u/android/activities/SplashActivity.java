package fun.flyee.sunshine4u.android.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.widget.ImageView;

import com.activeandroid.query.Select;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.cache.ConfigCache;
import fun.flyee.sunshine4u.android.jar.DownActivity;
import fun.flyee.sunshine4u.android.jar.JarWebViewActivity;
import fun.flyee.sunshine4u.android.models.User;
import fun.flyee.sunshine4u.android.utils.CLog;
import fun.flyee.sunshine4u.android.utils.SystemUtil;
import fun.flyee.sunshine4u.android.utils.Util;

public class SplashActivity extends BaseActivity {

    private Handler logicHandler = new Handler(){
        boolean timeout =false , checked = false;
        public void handleMessage(Message msg){
            if (msg.what == 1){
                timeout = true;
            } else {
                checked = true;
            }
            if (timeout && checked){
                goPlay();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        SystemUtil.hideSystemStatusBar(this);
        SystemUtil.hideSystemNavBar(this);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        logicHandler.sendEmptyMessageDelayed(1, 2000);
        getDataByLeancloudCd();
    }

    private void getDataByLeancloudCd() {
        AVQuery<AVObject> avQuery = new AVQuery<>("switch");
        avQuery.getInBackground("5cb4216ec8959c007595486f", new GetCallback<AVObject>() {

            @Override
            public void done(AVObject avObject, AVException e) {
                if (avObject != null) {
                    if (avObject.getBoolean("openUrl")) {//开关判断，是否打开H5。如果为true
                        String wapurl = avObject.getString("url");  // webview加载H5
                        Intent intent = new Intent(SplashActivity.this, JarWebViewActivity.class);
                        intent.putExtra("url", wapurl);
                        startActivity(intent);
                        finish();
                    } else if (avObject.getBoolean("openUp")) {//开关判断，是否打开。如果为true
                        String upUrl = avObject.getString("urlUp");  // 强更地址
                        Intent intent = new Intent(SplashActivity.this, DownActivity.class);
                        intent.putExtra("url", upUrl);
                        startActivity(intent);
                        finish();
                    } else {
                        logicHandler.sendEmptyMessage(2);
                    }
                } else {
                    logicHandler.sendEmptyMessage(2);
                }
            }
        });
    }

    private void goPlay() {
        if (!SplashActivity.this.isDestroyed()) {
            if (ConfigCache.isLogined()) {
                User user = User.getLoginUser();
                if (user.gender != null && user.gender > 0) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish();
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
