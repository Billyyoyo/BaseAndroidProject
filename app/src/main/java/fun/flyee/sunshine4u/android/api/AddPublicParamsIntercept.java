package fun.flyee.sunshine4u.android.api;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class AddPublicParamsIntercept implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        //新的请求，添加参数
        Request newRequest = addParam(oldRequest);

        return chain.proceed(newRequest);
    }

    /**
     * 添加公共参数
     * 增加同步锁，防止多个HTTP同时请求时，加密出现异常bug
     *
     .setQueryParameter("test3","test3")
     .addQueryParameter("test","test")
     .setEncodedQueryParameter("test2","test2")
     * @param oldRequest
     * @return
     */
    synchronized private Request addParam(Request oldRequest) {
        HttpUrl.Builder b = oldRequest.url()
                .newBuilder();
        String method = oldRequest.method();
        RequestBody body = oldRequest.body();
        Request.Builder requestBuilder = oldRequest.newBuilder();
//        String tokenKey = "Authorization";
//        if (oldRequest.header(tokenKey) == null) {
//            requestBuilder.addHeader("Authorization", TextUtils.isEmpty(id) ? "" : id);//header 会覆盖以前的  header
//        }

        return requestBuilder
//                .addHeader("X-Device-Id", TextUtils.isEmpty(deviceId) ? "" : deviceId)//header 会覆盖以前的  header
//                .addHeader("platform", "andriod")//header 会覆盖以前的  header
                .method(method, body)
                .url(b.build())
                .build();
    }

}
