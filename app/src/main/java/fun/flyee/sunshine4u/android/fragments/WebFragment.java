package fun.flyee.sunshine4u.android.fragments;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.activeandroid.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import fun.flyee.sunshine4u.android.ApplicationContext;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.activities.WebActivity;
import fun.flyee.sunshine4u.android.utils.CLog;
import fun.flyee.sunshine4u.android.utils.FileUtil;
import fun.flyee.sunshine4u.android.widgets.DefaultJsBridge;
import fun.flyee.sunshine4u.android.widgets.JsBridge;
import fun.flyee.sunshine4u.android.widgets.Toaster;
import fun.flyee.sunshine4u.android.widgets.AdvancedWebView;

public class WebFragment extends BaseFragment {

    protected boolean needJs = false;
    protected String title, url, content, src, time;
    @BindView(R.id.web)
    protected AdvancedWebView mWebView;

    public WebFragment() {

    }

    public static WebFragment newWebFragment(boolean needJs, String url) {
        WebFragment fragment = new WebFragment();
        fragment.url = url;
        fragment.needJs = needJs;
        return fragment;
    }

    public static WebFragment newWebFragment(String title, String content, String src, String time) {
        WebFragment fragment = new WebFragment();
        fragment.title = title;
        fragment.content = content;
        fragment.src = src;
        fragment.time = time;
        fragment.needJs = true;
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_web;
    }

    @Override
    public void onViewCreated(View v, Bundle b) {
        super.onViewCreated(v, b);
        mWebView.setListener(this, new WebListener());
        mWebView.setGeolocationEnabled(false);
        mWebView.setMixedContentAllowed(true);
        mWebView.setCookiesEnabled(true);
        mWebView.setThirdPartyCookiesEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

            }

        });
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (getActivity() instanceof WebActivity) {
                    if (!TextUtils.isEmpty(WebFragment.this.title)) {
                        ((WebActivity) getActivity()).getSupportActionBar().setTitle(WebFragment.this.title);
                    } else {
                        ((WebActivity) getActivity()).getSupportActionBar().setTitle(title);
                    }
                }
            }

        });
        mWebView.addHttpHeader("X-Requested-With", "");
        if (needJs) {
            mWebView.addJavascriptInterface(getJsBridge(), "DefaultJsBridge");
        }
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        } else {
            try {
                InputStream in = getContext().getAssets().open("article.tpl");
                String html = FileUtil.convertStreamToString(in);
                html = html.replaceAll("\\%\\%title\\%\\%", title);
                html = html.replaceAll("\\%\\%content\\%\\%", content);
                html = html.replaceAll("\\%\\%source\\%\\%", src);
                html = html.replaceAll("\\%\\%time\\%\\%", time);
                mWebView.loadHtml(html);
            }catch (IOException ioe){
                ioe.printStackTrace();
                mWebView.loadHtml(content);
            }
        }
    }

    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    public void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    public void onDestroy() {
        mWebView.onDestroy();
        super.onDestroy();
    }

    public boolean onBackPressed() {
        return !mWebView.onBackPressed();
    }

    public JsBridge getJsBridge() {
        return new DefaultJsBridge();
    }

    private class WebListener implements AdvancedWebView.Listener {
        @Override
        public void onPageStarted(String url, Bitmap favicon) {
            mWebView.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onPageFinished(String url) {
            mWebView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageError(int errorCode, String description, String failingUrl) {
            Toaster.show("onPageError(errorCode = " + errorCode + ",  description = " + description + ",  failingUrl = " + failingUrl + ")");
        }

        @Override
        public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
            Toaster.show("onDownloadRequested(url = " + url + ",  suggestedFilename = " + suggestedFilename + ",  mimeType = " + mimeType + ",  contentLength = " + contentLength + ",  contentDisposition = " + contentDisposition + ",  userAgent = " + userAgent + ")");
        }

        @Override
        public void onExternalPageRequest(String url) {
            Toaster.show("onExternalPageRequest(url = " + url + ")");
        }
    }

    //    callJs(String.format("functionInJs('%s', '%s')", "adc", "蛮王"));
    protected void callJs(String func) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.evaluateJavascript("javascript:" + func, (ret) -> {
                CLog.i("Js Return: " + ret);
            });
        } else {
            mWebView.loadUrl("javascript:" + func);

        }
    }
}