package fun.flyee.sunshine4u.android.widgets;

import android.webkit.JavascriptInterface;

import fun.flyee.sunshine4u.android.utils.CLog;

public class DefaultJsBridge extends JsBridge {

    @JavascriptInterface
    public void handle(String... param) {
        CLog.i("Receive message from js : " + param);
    }

}
