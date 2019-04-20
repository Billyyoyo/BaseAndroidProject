package fun.flyee.sunshine4u.android.widgets;

import android.webkit.JavascriptInterface;

import fun.flyee.sunshine4u.android.utils.CLog;

public abstract class JsBridge {

    @JavascriptInterface
    public abstract void handle(String... param);

}
