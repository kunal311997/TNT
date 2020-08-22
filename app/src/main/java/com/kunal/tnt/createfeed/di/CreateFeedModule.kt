package com.kunal.tnt.createfeed.di

import com.kunal.tnt.createfeed.adapter.KeywordsAdapter
import com.kunal.tnt.createfeed.network.CreateFeedApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

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

    @Provides
    @Named("imgur_instance")
    fun provideCreateFeedImgurApi(@Named("imgur_instance") retrofit: Retrofit): CreateFeedApi {
        return retrofit.create(CreateFeedApi::class.java)
    }

}