package com.kunal.tnt.enroll.di

import androidx.lifecycle.ViewModel
import com.kunal.tnt.common.di.ViewModelKey
import com.kunal.tnt.enroll.viewmodel.AuthViewModel
import com.kunal.tnt.home.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel?): ViewModel?
}
