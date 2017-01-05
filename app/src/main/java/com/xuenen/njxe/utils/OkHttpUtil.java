package com.xuenen.njxe.utils;


import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class OkHttpUtil {
    private static OkHttpClient client = null;

    static {
//        File cacheDir = new File(((MyApplication) MyApplication.getAppContext()).getAppCacheDir(), C.dir.SUB_CACHE_DIR);
//        if (!cacheDir.exists()) {
//            cacheDir.mkdirs();
//        }
        client = new OkHttpClient.Builder()
//                .cookieJar(new CookiesManager())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
//                .cache(new Cache(cacheDir, C.num.cache_size))
                .build();
    }

    /**
     * GET请求，利用异步请求获取数据，调用此方法时不需要再开启线程
     *
     * @param url      网络请求地址
     * @param callback 回调接口
     */
    public static void doGet(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    /**
     * POST请求，利用异步请求获取数据，调用此方法时不需要再开启线程
     *
     * @param url      网络请求地址
     * @param callback 回调接口
     */
    public static void doPost(String url, Map<String, String> param, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        System.out.println(url);
        Set<String> keySet = param.keySet();
        for (String name : keySet) {
            String value = param.get(name);
            builder.add(name, value);
            System.out.println(name + "   " + value);
        }
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        System.out.println(body + "");

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    //更改用户头像 的上传操作
    public static void doPostFile(String url, Map<String, String> param, Callback callback) {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        MediaType type = MediaType.parse("img/*");
        File f = null;
        Set<String> keySet = param.keySet();
        for (String name : keySet) {
            String value = param.get(name);
            builder.addFormDataPart(name, value);
            System.out.println(name + "   " + value);
            if ("path".equals(name)) {
                f = new File(value);
                if (f != null) {
                    builder.addFormDataPart("img", f.getName(), RequestBody.create(type, f));
                }
            }
        }
        MultipartBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        System.out.println(url + "   ");

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
