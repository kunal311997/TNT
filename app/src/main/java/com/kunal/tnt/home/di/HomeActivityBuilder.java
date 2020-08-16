package com.kunal.tnt.home.di;

import com.kunal.tnt.categories.CategoriesFragment;
import com.kunal.tnt.home.ui.HomeActivity;
import com.kunal.tnt.home.ui.HomeFragment;
import com.kunal.tnt.home.ui.SettingsFragment;
import com.kunal.tnt.videos.ui.VideosFragment;

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

    @ContributesAndroidInjector(modules = {
            HomeViewModelsModule.class,
            HomeModule.class})
    abstract CategoriesFragment contributeCategoriesFragment();


}
