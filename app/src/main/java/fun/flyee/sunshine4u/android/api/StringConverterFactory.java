package fun.flyee.sunshine4u.android.api;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import fun.flyee.sunshine4u.android.models.NewsResponse;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class StringConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return new StringConverter();
        } else if(type == NewsResponse.class) {
            //其它类型我们不处理，返回null就行
            return new BaseMessageConverter();
        }
        return null;
    }

    public class StringConverter implements Converter<ResponseBody, String> {

        @Override
        public String convert(ResponseBody value) throws IOException {
            return value.string();
        }
    }

    public class BaseMessageConverter implements Converter<ResponseBody, NewsResponse> {

        @Override
        public NewsResponse convert(ResponseBody value) throws IOException {
            String json = value.string();
//            String json = decrypt(string);
            NewsResponse newsResponse = JSONObject.parseObject(json, NewsResponse.class);
            return newsResponse;
        }

    }

}
