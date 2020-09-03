package com.kunal.tnt.common.di;

import com.kunal.tnt.createfeed.di.CreateFeedModule;
import com.kunal.tnt.createfeed.di.CreateFeedViewModelsModule;
import com.kunal.tnt.createfeed.ui.CreateFeedActivity;
import com.kunal.tnt.enroll.di.AuthModule;
import com.kunal.tnt.enroll.di.AuthViewModelsModule;
import com.kunal.tnt.enroll.ui.LoginActivity;
import com.kunal.tnt.enroll.ui.SignUpActivity;
import com.kunal.tnt.enroll.ui.SplashActivity;
import com.kunal.tnt.enroll.ui.WalkThroughActivity;
import com.kunal.tnt.favourites.di.FavouritesModule;
import com.kunal.tnt.favourites.ui.FavouritesActivity;
import com.kunal.tnt.favourites.viewmodel.FavouritesViewModelsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract SplashActivity contributesSplashActivity();

    @ContributesAndroidInjector(modules = {
            AuthModule.class,
            AuthViewModelsModule.class,
    })
    abstract LoginActivity contributesLoginActivity();

    @ContributesAndroidInjector
    abstract WalkThroughActivity contributesWalkThroughActivity();

    @ContributesAndroidInjector(modules = {
            AuthModule.class,
            AuthViewModelsModule.class,
    })
    abstract SignUpActivity contributesSignUpActivity();

    @ContributesAndroidInjector(modules = {
            CreateFeedModule.class,
            CreateFeedViewModelsModule.class,
    })
    abstract CreateFeedActivity contributesCreateFeedActivity();

    @ContributesAndroidInjector(modules = {
            FavouritesModule.class,
            FavouritesViewModelsModule.class
    })
    abstract FavouritesActivity contributesFavouritesActivity();


}
