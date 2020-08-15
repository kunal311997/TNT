package com.kunal.tnt.home.di;

import com.kunal.tnt.favourites.di.FavouritesModule;
import com.kunal.tnt.home.ui.HomeActivity;
import com.kunal.tnt.home.ui.HomeFragment;
import com.kunal.tnt.home.ui.SettingsFragment;
import com.kunal.tnt.videos.VideosFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeActivityBuilder {

    @ContributesAndroidInjector(modules = {
            HomeViewModelsModule.class,
            HomeModule.class})
    abstract HomeActivity contributesHomeActivity();

    @ContributesAndroidInjector(modules = {
            HomeViewModelsModule.class,
            HomeModule.class})
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector(modules = {
            HomeViewModelsModule.class,
            HomeModule.class})
    abstract SettingsFragment contributeSettingsFragment();

    @ContributesAndroidInjector(modules = {
            HomeViewModelsModule.class,
            HomeModule.class})
    abstract VideosFragment contributeVideosFragment();


}
