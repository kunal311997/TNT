package com.kunal.tnt.createfeed.di

import com.kunal.tnt.createfeed.adapter.KeywordsAdapter
import com.kunal.tnt.createfeed.network.CreateFeedApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class CreateFeedModule {

    @Provides
    fun provideKeywordsAdapter(): KeywordsAdapter {
        return KeywordsAdapter(listOf())
    }

    @Provides
    fun provideCreateFeedApi(retrofit: Retrofit): CreateFeedApi {
        return retrofit.create(CreateFeedApi::class.java)
    }

}