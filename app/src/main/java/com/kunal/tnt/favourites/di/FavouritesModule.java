package com.kunal.tnt.favourites.di;

import android.app.Application;

import com.kunal.tnt.common.db.AppDatabase;
import com.kunal.tnt.favourites.adapters.FavouritesAdapter;
import com.kunal.tnt.favourites.db.FavouritesDao;

import java.util.Collections;

import dagger.Module;
import dagger.Provides;

@Module
public class FavouritesModule {


    @Provides
    static FavouritesAdapter getFavouritesAdapter() {
        return new FavouritesAdapter(Collections.emptyList());
    }

}
