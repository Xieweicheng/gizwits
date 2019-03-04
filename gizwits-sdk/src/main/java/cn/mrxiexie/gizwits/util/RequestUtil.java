package cn.mrxiexie.gizwits.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author mrxiexie
 * @date 3/1/19 3:41 PM
 */
@Slf4j
public class RequestUtil {

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    @Autowired
    private OkHttpClient okHttpClient;

    /**
     * 异步get请求
     *
     * @param url      请求地址
     * @param callback 回调
     */
    public void getAsyn(String url, Callback callback) {
        getAsyn(url, null, callback);
    }

    /**
     * 异步get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 回调
     */

    public void getAsyn(String url, Map<String, String> params, Callback callback) {
        getAsyn(url, params, null, callback);
    }

    /**
     * 异步get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param headers  请求头
     * @param callback 回调
     */
    public void getAsyn(String url, Map<String, String> params, Headers headers, Callback callback) {
        Request request = createGetRequest(url, params, headers);
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 同步get请求
     *
     * @param url 请求地址
     */
    public String getSyn(String url) {
        return getSyn(url, null);
    }

    /**
     * 同步get请求
     *
     * @param url    请求地址
     * @param params 请求参数
     */
    public String getSyn(String url, Map<String, String> params) {
        return getSyn(url, params, null);
    }

    /**
     * 同步get请求
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param headers 请求头
     */
    public String getSyn(String url, Map<String, String> params, Headers headers) {
        return getResponseStr(url, params, headers);
    }

    public String getSyn(String url, JSONObject obj, Headers headers) {
        if (obj != null && obj.names() != null) {
            url += "?";
            StringBuilder urlBuilder = new StringBuilder(url);
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                log.info("key : " + key);
                urlBuilder.append(key).append("=").append(obj.get(key)).append("&");
            }
            url = urlBuilder.deleteCharAt(urlBuilder.lastIndexOf("&")).toString();
        }
        Request request = new Request.Builder()
                .get()
                .url(url)
                .headers(headers)
                .build();
        return exec(request);
    }

    /**
     * 异步post请求
     *
     * @param url      请求地址
     * @param json     请求参数json
     * @param callback 回调
     */
    public void postAsyn(String url, String json, Callback callback) {
        postAsyn(url, json, null, callback);
    }

    /**
     * 异步post请求
     *
     * @param url      请求地址
     * @param json     请求参数json
     * @param headers  请求头
     * @param callback 回调
     */
    public void postAsyn(String url, String json, Headers headers, Callback callback) {
        Request request = createJsonRequest(url, json, headers, POST);
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 同步post请求
     *
     * @param url  请求地址
     * @param json 请求参数json
     */
    public String postSyn(String url, String json) {
        return postSyn(url, json, null);
    }

    /**
     * 同步post请求
     *
     * @param url     请求地址
     * @param json    请求参数json
     * @param headers 请求头
     */
    public String postSyn(String url, String json, Headers headers) {
        return getResponseStr(url, json, headers, POST);
    }

    /**
     * 异步delete请求
     *
     * @param url      请求地址
     * @param json     请求参数json
     * @param callback 回调
     */
    public void deleteAsyn(String url, String json, Callback callback) {
        deleteAsyn(url, json, null, callback);
    }

    /**
     * 异步delete请求
     *
     * @param url      请求地址
     * @param json     请求参数json
     * @param headers  请求头
     * @param callback 回调
     */
    public void deleteAsyn(String url, String json, Headers headers, Callback callback) {
        Request request = createJsonRequest(url, json, headers, DELETE);
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 同步delete请求
     *
     * @param url  请求地址
     * @param json 请求参数json
     */
    public String deleteSyn(String url, String json) {
        return deleteSyn(url, json, null);
    }

    /**
     * 同步delete请求
     *
     * @param url     请求地址
     * @param json    请求参数json
     * @param headers 请求头
     */
    public String deleteSyn(String url, String json, Headers headers) {
        return getResponseStr(url, json, headers, DELETE);
    }

    /**
     * 异步put请求
     *
     * @param url      请求地址
     * @param json     请求参数json
     * @param callback 回调
     */
    public void putAsyn(String url, String json, Callback callback) {
        putAsyn(url, json, null, callback);
    }

    /**
     * 异步put请求
     *
     * @param url      请求地址
     * @param json     请求参数json
     * @param headers  请求头
     * @param callback 回调
     */
    public void putAsyn(String url, String json, Headers headers, Callback callback) {
        Request request = createJsonRequest(url, json, headers, PUT);
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 同步put请求
     *
     * @param url  请求地址
     * @param json 请求参数json
     */
    public String putSyn(String url, String json) {
        return putSyn(url, json, null);
    }

    /**
     * 同步put请求
     *
     * @param url     请求地址
     * @param json    请求参数json
     * @param headers 请求头
     */
    public String putSyn(String url, String json, Headers headers) {
        return getResponseStr(url, json, headers, PUT);
    }

    private Request createGetRequest(String url, Map<String, String> params, Headers headers) {
        if (params != null) {
            url += "?";
            Set<Map.Entry<String, String>> entries = params.entrySet();
            StringBuilder urlBuilder = new StringBuilder(url);
            for (Map.Entry<String, String> entry : entries) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            url = urlBuilder.deleteCharAt(urlBuilder.lastIndexOf("&")).toString();
        }

        Request.Builder builder = new Request.Builder().get().url(url);
        if (headers != null) {
            builder.headers(headers);
        }
        return builder.build();
    }

    private String getResponseStr(String url, Map<String, String> params, Headers headers) {
        Request request = createGetRequest(url, params, headers);
        return exec(request);
    }

    private String getResponseStr(String url, String json, Headers headers, String type) {
        Request request = createJsonRequest(url, json, headers, type);
        return exec(request);
    }

    private String exec(Request request) {
        String result = null;
        try {
            Response execute = okHttpClient.newCall(request).execute();
            result = execute.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Request createJsonRequest(String url, String json, Headers headers, String type) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        Request.Builder builder = new Request.Builder().url(url);

        switch (type) {
            case POST:
                builder.post(requestBody);
                break;
            case PUT:
                builder.put(requestBody);
                break;
            case DELETE:
                builder.delete(requestBody);
                break;
            default:
        }

        if (headers != null) {
            builder.headers(headers);
        }
        return builder.build();
    }
}
