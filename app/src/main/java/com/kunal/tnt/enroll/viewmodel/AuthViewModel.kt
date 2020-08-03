package com.kunal.tnt.enroll.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.enroll.repository.AuthRepository
import com.kunal.tnt.enroll.models.LoginResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @Named("IO") private val ioDispatcher: CoroutineDispatcher,
    @Named("MAIN") private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {


    private val loginResLiveData = MutableLiveData<Resource<LoginResponse>>()


    fun getLoginResLiveData(): LiveData<Resource<LoginResponse>> {
        return loginResLiveData
    }


    fun login(email: String, password: String) {
        loginResLiveData.value = Resource.loading(null)
        var feedResponse: Resource<LoginResponse>? = null

        viewModelScope.launch {
            withContext(ioDispatcher) {
                feedResponse = authRepository.login(email, password)
            }
            withContext(mainDispatcher) {
                feedResponse?.let {
                    loginResLiveData.value = it
                }
            }
        }
    }
}