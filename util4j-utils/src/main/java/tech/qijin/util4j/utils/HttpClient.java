package tech.qijin.util4j.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.collections.MapUtils;
import org.springframework.util.CollectionUtils;
import tech.qijin.util4j.utils.pojo.HttpResp;

public class HttpClient {

    public static void main(String[] args) {
    }


    /**
     * 使用SOCKS代理
     */
    public static class Proxy {
        public static String get(String url, String ip, int port) throws IOException {
            java.net.Proxy proxy = new java.net.Proxy(java.net.Proxy.Type.SOCKS, new InetSocketAddress(ip, port));
            OkHttpClient httpClient = new OkHttpClient.Builder().proxy(proxy).build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = httpClient.newCall(request).execute();
            return response.body().string();
        }
    }

    /**
     * get请求
     *
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
     *
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

    public static HttpResp doPost(String url, Map<String, Object> params) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), JSONObject.toJSONString(params));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = httpClient.newCall(request).execute();
        return HttpResp.builder().code(response.code())
                .data(response.body().string()).build();
    }

    public static HttpResp doPost(String url, Map<String, Object> params, Map<String, String> headers) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), JSONObject.toJSONString(params));
        Request.Builder builder = new Request.Builder();
        builder.url(url)
                .post(requestBody);
        if (MapUtils.isNotEmpty(headers)) {
            headers.entrySet().stream().forEach(entry ->
                    builder.addHeader(entry.getKey(), entry.getValue()));
        }
        Request request = builder.build();
        Response response = httpClient.newCall(request).execute();
        return HttpResp.builder().code(response.code())
                .data(response.body().string()).build();
    }
}
