package com.kunal.tnt.home.di;

import com.kunal.tnt.home.adapter.FeedsAdapter;
import com.kunal.tnt.home.network.HomeApi;

import java.util.Collections;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class HomeModule {

    @Provides
    static HomeApi provideHomeApi(Retrofit retrofit) {
        return retrofit.create(HomeApi.class);
    }

    @Provides
    static FeedsAdapter getFeedsAdapter() {
        return new FeedsAdapter(Collections.emptyList());
    }


}
