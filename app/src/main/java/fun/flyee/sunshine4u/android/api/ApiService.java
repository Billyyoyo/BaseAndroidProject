package fun.flyee.sunshine4u.android.api;

import fun.flyee.sunshine4u.android.models.JisuResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    //jisu
    @GET("/news/search")
    Observable<JisuResponse> newsSearch(@Query("appkey") String appkey, @Query("keyword") String keyword);

    //jisu
    @GET("/news/channel")
    Observable<JisuResponse> newsChannels(@Query("appkey") String appkey);

    //jisu
    @GET("/news/get")
    Observable<JisuResponse> newsList(@Query("appkey") String appkey
            , @Query("channel") String channel
            , @Query("start") Integer start
            , @Query("num") Integer num);

    //jisu
    @GET("/astro/all")
    Observable<JisuResponse> zodiacAll(@Query("appkey") String appkey);

    //jisu
    @GET("/astro/fortune")
    Observable<JisuResponse> zodiacDetail(@Query("appkey") String appkey
            , @Query("astroid") String astroid
            , @Query("date") String date);

    //tianxing
    @GET("/star")
    Observable<JisuResponse> zodiacStatus(@Query("key") String appkey
            , @Query("astro") String astro
            , @Query("y") String year
            , @Query("m") String month
            , @Query("d") String day);

    //tianxing
    @GET("/xingzuo")
    Observable<JisuResponse> zodiacMatch(@Query("key") String appkey
            , @Query("me") String me
            , @Query("he") String he);

}
