package fun.flyee.sunshine4u.android.api;

import android.text.TextUtils;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import fun.flyee.sunshine4u.android.utils.CLog;
import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

public class LogInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    public LogInterceptor() {
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;
        Connection connection = chain.connection();
        String requestStartMessage = "--> " + request.method() + ' ' + request.url() + (connection != null ? " " + connection.protocol() : "");
        if (hasRequestBody) {
            requestStartMessage = requestStartMessage + " (" + requestBody.contentLength() + "-byte body)";
        }

        CLog.i(requestStartMessage);
        if (hasRequestBody) {
            if (requestBody.contentType() != null) {
                CLog.i("Content-Type: " + requestBody.contentType());
            }

            if (requestBody.contentLength() != -1L) {
                CLog.i("Content-Length: " + requestBody.contentLength());
            }
        }

        Headers headers = request.headers();
        int i = 0;

        for (int count = headers.size(); i < count; ++i) {
            String name = headers.name(i);
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                CLog.i(name + ": " + headers.value(i));
            }
        }

        if (hasRequestBody) {
            if (this.bodyHasUnknownEncoding(request.headers())) {
                CLog.i("--> END " + request.method() + " (encoded body omitted)");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = null;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                if (charset == null)
                    charset = UTF8;

                CLog.i("");
                if (isPlaintext(buffer)) {
                    String result = buffer.readString(charset);
                    CLog.i(result);
                    CLog.i("--> END " + request.method() + " (" + requestBody.contentLength() + "-byte body)");
                } else {
                    CLog.i("--> END " + request.method() + " (binary " + requestBody.contentLength() + "-byte body omitted)");
                }
            }
        } else {
            CLog.i("--> END " + request.method());
        }


        long startNs = System.nanoTime();

        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception var27) {
            CLog.i("<-- HTTP FAILED: " + var27);
            throw var27;
        }

        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1L ? contentLength + "-byte" : "unknown-length";
        CLog.i("<-- " + response.code() + (response.message().isEmpty() ? "" : ' ' + response.message()) + ' ' + response.request().url() + " (" + tookMs + "ms" + (", " + bodySize + " body") + ')');
        headers = response.headers();
        i = 0;

        for (int count = headers.size(); i < count; ++i) {
            CLog.i(headers.name(i) + ": " + headers.value(i));
        }

        if (HttpHeaders.hasBody(response)) {
            if (this.bodyHasUnknownEncoding(response.headers())) {
                CLog.i("<-- END HTTP (encoded body omitted)");
            } else {
                BufferedSource source = responseBody.source();
                source.request(9223372036854775807L);
                Buffer buffer = source.buffer();
                Long gzippedLength = null;
                if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
                    gzippedLength = buffer.size();
                    GzipSource gzippedResponseBody = null;

                    try {
                        gzippedResponseBody = new GzipSource(buffer.clone());
                        buffer = new Buffer();
                        buffer.writeAll(gzippedResponseBody);
                    } finally {
                        if (gzippedResponseBody != null) {
                            gzippedResponseBody.close();
                        }

                    }
                }

                Charset charset = null;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                if (charset == null)
                    charset = UTF8;

                if (!isPlaintext(buffer)) {
                    CLog.i("");
                    CLog.i("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                    return response;
                }

                if (contentLength != 0L) {
                    CLog.i("");
                    String result = buffer.clone().readString(charset);
                    CLog.i("\n" + result + "\n");
                }

                if (gzippedLength != null) {
                    CLog.i("<-- END HTTP (" + buffer.size() + "-byte, " + gzippedLength + "-gzipped-byte body)");
                } else {
                    CLog.i("<-- END HTTP (" + buffer.size() + "-byte body)");
                }
            }
        } else {
            CLog.i("<-- END HTTP");
        }


        return response;
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64L ? buffer.size() : 64L;
            buffer.copyTo(prefix, 0L, byteCount);

            for (int i = 0; i < 16 && !prefix.exhausted(); ++i) {
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }

            return true;
        } catch (EOFException var6) {
            return false;
        }
    }

    private boolean bodyHasUnknownEncoding(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity") && !contentEncoding.equalsIgnoreCase("gzip");
    }

    //规定每段显示的长度
    private static int LOG_MAXLENGTH = 2000;

}
