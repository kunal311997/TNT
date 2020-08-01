package com.kunal.tnt.common.di;

import androidx.lifecycle.ViewModelProvider;

import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProvidersFactory viewModelProvidersFactory);
}
