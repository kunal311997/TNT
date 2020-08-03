package com.kunal.tnt.createfeed.di;

import androidx.lifecycle.ViewModel;

import com.kunal.tnt.common.di.ViewModelKey;
import com.kunal.tnt.createfeed.viewmodel.CreateFeedViewModel;
import com.kunal.tnt.home.viewmodel.HomeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class CreateFeedViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(CreateFeedViewModel.class)
    public abstract ViewModel bindCreateFeedViewModel(CreateFeedViewModel createFeedViewModel);
}
