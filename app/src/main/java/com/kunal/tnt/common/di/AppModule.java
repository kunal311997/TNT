package com.kunal.tnt.common.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kunal.tnt.common.uils.Constant;
import com.kunal.tnt.common.uils.SharedPrefClient;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Dispatchers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private static Long connectTimeUnit = 10L;
    private static Long readTimeUnit = 40L;
    private static Long writeTimeUnit = 10L;

    @Provides
    @Singleton
    static Context getProvideContext(Application application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("TCPPreference", Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    SharedPrefClient provideSharedPreferenceClient(SharedPreferences sharedPreferences) {
        return new SharedPrefClient(sharedPreferences);
    }


    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(Context context, SharedPrefClient clientPref) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeUnit, TimeUnit.SECONDS)
                .readTimeout(readTimeUnit, TimeUnit.SECONDS)
                .writeTimeout(writeTimeUnit, TimeUnit.SECONDS)
                .addInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new ChuckInterceptor(context))
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Request.Builder newRequest = request.newBuilder();

                    if (!clientPref.getBearerToken().equals("")) {
                        newRequest.addHeader(Constant.AUTH_HEADER, Constant.BEARER_SUFFIX + clientPref.getBearerToken());
                    }
                    return chain.proceed(newRequest.build());
                });

        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
    }

    /*@Singleton
    @Provides
    static Picasso providePicassoInstance(Application application, OkHttp3Downloader okHttp3Downloader) {
        return new Picasso.Builder(application.getApplicationContext())
                .downloader(okHttp3Downloader)
                .indicatorsEnabled(true)
                .build();
    }

    @Singleton
    @Provides
    static OkHttp3Downloader getOkHttp3Downloader(OkHttpClient okHttpClient) {
        return new OkHttp3Downloader(okHttpClient);
    }*/

    @Provides
    @Named("IO")
    static public CoroutineDispatcher provideIOCoroutineDispatcher() {
        return Dispatchers.getIO();
    }

    @Provides
    @Named("MAIN")
    static public CoroutineDispatcher provideMainCoroutineDispatcher() {
        return Dispatchers.getMain();
    }
}
