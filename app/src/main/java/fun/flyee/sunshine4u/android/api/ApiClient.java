package fun.flyee.sunshine4u.android.api;

import java.util.concurrent.TimeUnit;

import fun.flyee.sunshine4u.android.ApplicationContext;
import fun.flyee.sunshine4u.android.utils.Constant;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class ApiClient {

    private OkHttpClient mClient;
    private Retrofit mRetrofitJS;
    private Retrofit mRetrofitTX;

    public static ApiClient init(){
        ApiClient client = new ApiClient();
        client.mClient = client.provideOkHttpClient();
        client.mRetrofitJS = client.provideRetrofit(client.mClient, Constant.JISU_API_URL);
        client.mRetrofitTX = client.provideRetrofit(client.mClient, Constant.TIANXING_API_URL);
        return client;
    }

    private OkHttpClient provideOkHttpClient() {
        LogInterceptor loggingInterceptor = new LogInterceptor();
        OkHttpClient okhttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
//                .cookieJar(new CookieManager(SheepApp.getInstance()))
                .addInterceptor(new AddPublicParamsIntercept())
                .addInterceptor(new CacheInterceptor())
                .addInterceptor(loggingInterceptor)
                .build();
        return okhttpClient;
    }

    private Retrofit provideRetrofit(OkHttpClient okhttpClient, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okhttpClient)
                .baseUrl(url)
                .addConverterFactory(new StringConverterFactory())
                .addConverterFactory(FastJsonConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(EntityUtils.gson))//
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    private ApiService provideJSApiService(){
        return mRetrofitJS.create(ApiService.class);
    }
    private ApiService provideTXApiService(){
        return mRetrofitJS.create(ApiService.class);
    }

    public static ApiService jsApi(){
        return ApplicationContext.getContext().getApiClient().provideJSApiService();
    }

    public static ApiService txApi(){
        return ApplicationContext.getContext().getApiClient().provideTXApiService();
    }

}
