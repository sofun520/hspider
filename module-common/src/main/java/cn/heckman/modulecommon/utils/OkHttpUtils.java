package cn.heckman.modulecommon.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class OkHttpUtils {

    private static Logger logger = LoggerFactory.getLogger(OkHttpUtils.class);

    public static String get(String url, Map<String, Object> requestMap) {
        url = url + "?" + ParesUrlUtils.getUrlParamsByMap(requestMap);
        String result = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .get()//默认就是GET请求，可以不写
                    .build();
            Response response = null;
            response = okHttpClient.newCall(request).execute();
            result = response.body().string();
        } catch (Exception ex) {
            logger.error("inner error", ex);
        }
        return result;
    }

    public static String get(String url) {
        String result = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .addHeader("Content-Type","application/json")
                    .url(url)
                    .get()//默认就是GET请求，可以不写
                    .build();
            Response response = null;
            response = okHttpClient.newCall(request).execute();
            result = response.body().string();
        } catch (Exception ex) {
            logger.error("inner error", ex);
        }
        return result;
    }

    /**
     * post json
     *
     * @param url
     * @param requestMap
     * @return
     */
    public static String postJson(String url, Map<String, Object> requestMap) {
        String result = null;
        try {
            String params = JSON.toJSONString(requestMap);
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params))
                    .build();
            Response response = null;
            response = okHttpClient.newCall(request).execute();
            result = response.body().string();
        } catch (Exception ex) {
            logger.error("inner error", ex);
        }
        return result;
    }

}
