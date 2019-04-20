package fun.flyee.sunshine4u.android.jar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.just.library.AgentWeb;
import com.just.library.AgentWebSettings;
import com.just.library.ChromeClientCallbackManager;
import com.just.library.DownLoadResultListener;
import com.just.library.IWebLayout;
import com.just.library.PermissionInterceptor;
import com.just.library.WebDefaultSettingsManager;

import java.util.List;

import fun.flyee.sunshine4u.android.R;


public class JarWebViewActivity extends AppCompatActivity {
    private WebView webView;
    WebView mWebView;
    String url = "";
    String qq = "";
    String qqkey = "";

    private LinearLayout lin;
    protected AgentWeb mAgentWeb;

    private RadioButton mRb1, mRb2, mRb3, mRb4, mRb5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jar_web_view);

        lin = (LinearLayout) this.findViewById(R.id.lin);
//        StatusBarCompat.compat(this, lin, new StatusBarValue(true, R.color.colorPrimary11));

        webView = (WebView) findViewById(R.id.webView);

        mRb1 = (RadioButton) findViewById(R.id.mRb1);

        mRb2 = (RadioButton) findViewById(R.id.mRb2);

        mRb3 = (RadioButton) findViewById(R.id.mRb3);

        mRb4 = (RadioButton) findViewById(R.id.mRb4);

        mRb5 = (RadioButton) findViewById(R.id.mRb5);

//        webView.clearHistory();
        url = getIntent().getStringExtra("url");
        qq = getIntent().getStringExtra("qq");
        qqkey = getIntent().getStringExtra("qqkey");
//        Toast.makeText(JarWebViewActivity.this, "ShowWeb_URL:" + url, Toast.LENGTH_LONG).show();

//        Toast.makeText(this,"url:"+url,Toast.LENGTH_LONG).show();
        buildAgentWeb();
    }    //Web视图

    protected void buildAgentWeb() {
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(webView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//
                .useDefaultIndicator()//
                .setIndicatorColorWithHeight(Color.parseColor("#ffffff"), 1)
//                .setReceivedTitleCallback(getReceivedTitleCallback())----
                .setWebChromeClient(getWebChromeClient())
                .setWebViewClient(getWebViewClient())
                .setWebView(getWebView())
                .setPermissionInterceptor(getPermissionInterceptor())
                .setWebLayout(getWebLayout())
                .addDownLoadResultListener(mDownLoadResultListener)
                .setAgentWebSettings(getAgentWebSettings())
                .setSecutityType(AgentWeb.SecurityType.strict)
                .openParallelDownload()//打开并行下载 , 默认串行下载。
                .setNotifyIcon(R.drawable.ic_file_download_white_48dp) //下载通知图标。

//                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
//                .interceptUnkownScheme() //拦截找不到相关页面的Scheme AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .go(url);

        mWebView = mAgentWeb.getWebCreator().get();
        mWebView.setDownloadListener(new MyWebViewDownLoadListener());

        mRb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mWebView.clearHistory();
                mWebView.clearCache(true);
                mWebView.loadUrl(url);
                mWebView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.clearHistory();
                    }
                }, 1000);
            }
        });
        mRb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mWebView.goBack();

            }
        });
        mRb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mWebView.goForward();

            }

        });
        mRb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.loadUrl(qqkey);
//                if (isQQClientAvailable(JarWebViewActivity.this)) {
////                    Toast.makeText(JarWebViewActivity.this, "qqkey:"+qqkey, Toast.LENGTH_LONG).show();
//
//                    joinQQGroup(qqkey);
////                    String urlQQ = "mqqwpa://im/chat?chat_type=group&uin="+qq+"&version=1";
//////                    String url = "mqqwpa://im/chat?chat_type=group&uin=" + qq + "&version=1&src_type=web&web_src=http:://wpa.b.qq.com";
////                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                } else {
//                    Toast.makeText(JarWebViewActivity.this, "您的手机暂未安装QQ客户端", Toast.LENGTH_LONG).show();
//                }

            }
        });
        mRb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建dialog构造器
                AlertDialog.Builder normalDialog = new AlertDialog.Builder(JarWebViewActivity.this);
                //设置title
                normalDialog.setTitle("清除缓存");
                //设置icon
//                normalDialog.setIcon(R.mipmap.ic_launcher);
                //设置内容
                normalDialog.setMessage("是否清除");
                //设置按钮
                normalDialog.setPositiveButton("清除"
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //String urlQQ = "mqqwpa://im/chat?chat_type=group&uin=qqnumber&version=1";
                                // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlQQ)));
                                mAgentWeb.getWebCreator().get().clearHistory();
                                mAgentWeb.getWebCreator().get().clearCache(true);
//                mAgentWeb.getWebCreator().get().clearFormData();
                                //                mWebView.reload();
                                dialog.dismiss();
                                Toast.makeText(JarWebViewActivity.this, "清除成功"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        });
                normalDialog.setNegativeButton("取消"
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });
                //创建并显示
                normalDialog.create().show();

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            if (!mAgentWeb.getWebCreator().get().canGoBack()) {
                return false;
            }
        }
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    /**
     * 是否退出
     */
    private boolean isExit;

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再点击一次退出程序", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    isExit = false;

                }
            }, 2000);

        } else {
            Intent it = new Intent(Intent.ACTION_MAIN);
            it.addCategory(Intent.CATEGORY_HOME);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(it);

        }

    }


    public
    @Nullable
    AgentWebSettings getAgentWebSettings() {
        return WebDefaultSettingsManager.getInstance();
    }

    //WebViewClient
    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            if (url.startsWith("http") || url.startsWith("https")) {
                super.onPageStarted(view, url, favicon);
            } else {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(JarWebViewActivity.this, "您没有安装相应的程序", Toast.LENGTH_LONG).show();
                    //当手机上没有安装对应应用时报出异常
                }
            }

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.startsWith("http") || url.startsWith("https")) {
                view.loadUrl(url);
                return false;
            } else {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(JarWebViewActivity.this, "您没有安装相应的程序", Toast.LENGTH_LONG).show();
                    //当手机上没有安装对应应用时报出异常
                }
                return true;

            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Log.d("SslError:", error.toString());
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
        }

    };
//    //WebChromeClient
//    private WebChromeClient mWebChromeClient=new WebChromeClient(){
//        @Override
//        public void onProgressChanged(WebView view, int newProgress) {
//            view.getUrl();
//        }
//    };

    protected
    @Nullable
    WebChromeClient getWebChromeClient() {
        return null;
    }

    protected DownLoadResultListener mDownLoadResultListener = new DownLoadResultListener() {
        @Override
        public void success(String path) {
            //do you work

        }

        @Override
        public void error(String path, String resUrl, String cause, Throwable e) {
            //do you work
        }
    };

    protected
    @Nullable
    WebViewClient getWebViewClient() {
        return mWebViewClient;
    }


    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    protected
    @Nullable
    WebView getWebView() {
        return null;
    }

    protected
    @Nullable
    IWebLayout getWebLayout() {
        return null;
    }

    protected PermissionInterceptor getPermissionInterceptor() {
        return null;
    }

    protected
    @Nullable
    DownLoadResultListener getDownLoadResultListener() {
        return mDownLoadResultListener;
    }

    private
    @Nullable
    ChromeClientCallbackManager.ReceivedTitleCallback getReceivedTitleCallback() {
        return new ChromeClientCallbackManager.ReceivedTitleCallback() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
            }
        };
    }

    @Override
    protected void onPause() {
//        mAgentWeb.getWebLifeCycle().onPause(); //暂停应用内所有 WebView ， 需谨慎。
        super.onPause();

    }

    @Override
    protected void onResume() {
//        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /****************
     *
     * 发起添加群流程。群号：APP上架ios上架安卓(106848134) 的 key 为： ZJOaRGB7K5CjsUhtzqQUB1tt9YDY8tsT
     * 调用 joinQQGroup(ZJOaRGB7K5CjsUhtzqQUB1tt9YDY8tsT) 即可发起手Q客户端申请加群 APP上架ios上架安卓(106848134)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }
}
