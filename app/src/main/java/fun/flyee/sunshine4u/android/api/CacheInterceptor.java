package fun.flyee.sunshine4u.android.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheInterceptor implements Interceptor {

    public Response intercept(Chain chain){
        Request request = chain.request();
        try {
            //在proceed之后做数据缓存
            return chain.proceed(request);
        } catch (Exception var27) {
            var27.printStackTrace();
            return null;
        }
    }

}
