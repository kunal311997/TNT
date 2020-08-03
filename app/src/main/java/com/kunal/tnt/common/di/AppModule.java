package com.kunal.tnt.common.di;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kunal.tnt.common.uils.Constant;
import com.readystatesoftware.chuck.ChuckInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Dispatchers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    @Singleton
    static Context getProvideContext(Application application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(Context context) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

                )
                .addInterceptor(new ChuckInterceptor(context));

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
