package com.kunal.tnt.home.di;

import com.kunal.tnt.home.network.HomeApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Dispatchers;
import retrofit2.Retrofit;

@Module
public class HomeModule {

    @Provides
    static HomeApi provideHomeApi(Retrofit retrofit) {
        return retrofit.create(HomeApi.class);
    }


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
