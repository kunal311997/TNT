package com.kunal.tnt.home.di;

import androidx.lifecycle.ViewModel;

import com.kunal.tnt.home.viewmodel.HomeViewModel;
import com.kunal.tnt.common.di.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class HomeViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    public abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);
}
