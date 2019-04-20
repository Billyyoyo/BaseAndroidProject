package fun.flyee.sunshine4u.android;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.avos.avoscloud.AVOSCloud;
import com.bumptech.glide.Glide;
import com.stormagain.easycache.EasyCacheManager;

import fun.flyee.sunshine4u.android.api.ApiClient;
import fun.flyee.sunshine4u.android.cache.ConfigCache;

public class ApplicationContext extends Application {

    private static ApplicationContext mContext;
    private ApiClient mApiClient;

    public static ApplicationContext getContext() {
        return mContext;
    }

    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        mContext = this;
    }

    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, "Ws3GkiYDUPXzvFBsPsDS0oE7-gzGzoHsz", "moSPAzWcsgH6iGGkO2nETkDv");
        ActiveAndroid.initialize(this, true);
        mApiClient = ApiClient.init();
        EasyCacheManager.getInstance().setup(this);
        ConfigCache.get().APP = "asdf";
        ConfigCache.save();
    }

    public void onTerminate(Context context) {
        ActiveAndroid.dispose();
    }

    public ApiClient getApiClient(){
        return mApiClient;
    }

}