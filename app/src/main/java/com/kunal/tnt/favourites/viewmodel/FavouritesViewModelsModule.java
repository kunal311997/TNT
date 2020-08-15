package com.kunal.tnt.favourites.viewmodel;

import androidx.lifecycle.ViewModel;

import com.kunal.tnt.common.di.ViewModelKey;
import com.kunal.tnt.createfeed.viewmodel.CreateFeedViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class FavouritesViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(FavouritesViewModel.class)
    public abstract ViewModel bindFavouritesViewModel(FavouritesViewModel favouritesViewModel);
}
