package cn.lueans.shuhui.retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.lueans.shuhui.App;
import cn.lueans.shuhui.constant.AppConstant;
import cn.lueans.shuhui.constant.UserConstant;
import cn.lueans.shuhui.entity.User;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 24277 on 2017/5/22.
 */

public class ShuhuiRetrofit {


    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new UserInterceptor())  //添加过滤器
            .connectTimeout(8, TimeUnit.SECONDS)
            .build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(AppConstant.APP_HTTP)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    public <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }

    private static class UserInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();
            if (User.getInstance(App.getInstance()).isLogin()) {
                Request newRequest = oldRequest.newBuilder()
                        .addHeader(UserConstant.COOKIE_KEY, User.getInstance(App.getInstance()).getCookie())
                        .build();
                return chain.proceed(newRequest);
            }

            return chain.proceed(oldRequest);
        }
    }
}
