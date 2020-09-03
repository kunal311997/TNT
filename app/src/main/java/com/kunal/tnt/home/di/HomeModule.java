package com.kunal.tnt.home.di;

import android.app.Application;

import com.kunal.tnt.common.db.AppDatabase;
import com.kunal.tnt.enroll.adapters.SelectInterestsAdapter;
import com.kunal.tnt.favourites.db.FavouritesDao;
import com.kunal.tnt.home.adapter.FeedsAdapter;
import com.kunal.tnt.home.network.HomeApi;
import com.kunal.tnt.videos.adapters.VideosAdapter;

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
        return new FeedsAdapter();
    }

    @Provides
    static VideosAdapter getVideosAdapter() {
        return new VideosAdapter(Collections.emptyList());
    }

    @Provides
    static SelectInterestsAdapter getSelectInterestsAdapter() {
        return new SelectInterestsAdapter(Collections.emptyList());
    }

    @Provides
    static FavouritesDao provideFavouritesDao(Application application) {
        return AppDatabase.Companion.getDatabase(application).favouritesDao();
    }
}
