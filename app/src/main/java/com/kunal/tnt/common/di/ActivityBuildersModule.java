package com.kunal.tnt.common.di;

import com.kunal.tnt.createfeed.ui.CreateFeedActivity;
import com.kunal.tnt.enroll.di.AuthModule;
import com.kunal.tnt.enroll.di.AuthViewModelsModule;
import com.kunal.tnt.enroll.ui.LoginActivity;
import com.kunal.tnt.feed.ui.FeedFragmentNew;

import com.kunal.tnt.feed.di.FeedFragmentModule;
import com.kunal.tnt.home.HomeActivity;
import com.kunal.tnt.home.di.HomeModule;
import com.kunal.tnt.home.di.HomeViewModelsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {
            HomeViewModelsModule.class,
            HomeModule.class
    })
    abstract HomeActivity contributesHomeActivity();


    @ContributesAndroidInjector(modules = {
            FeedFragmentModule.class,
            HomeViewModelsModule.class,
    })
    abstract FeedFragmentNew contributesFeedFragment();

    @ContributesAndroidInjector(modules = {
            FeedFragmentModule.class,
            HomeViewModelsModule.class,
    })
    abstract CreateFeedActivity contributesCreateFeedActivity();

    @ContributesAndroidInjector(modules = {
            AuthModule.class,
            AuthViewModelsModule.class,
    })
    abstract LoginActivity contributesLoginActivity();

}
