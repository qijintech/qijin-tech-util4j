package tech.qijin.util4j.utils.http;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class HttpClient {

    public static void main(String[] args){
    }

    /**
     * get请求
     * @param url
     * @return
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * post请求
     * @param url
     * @param params 请求参数
     * @return
     * @throws IOException
     */
    public static String post(String url, Map params) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), JSONObject.toJSONString(params));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }
}
