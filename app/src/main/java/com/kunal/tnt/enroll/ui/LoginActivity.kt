package com.kunal.tnt.enroll.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kunal.tnt.R
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.common.uils.Utilities.hideProgressBar
import com.kunal.tnt.common.uils.Utilities.showProgressbar
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.enroll.viewmodel.AuthViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var providersFactory: ViewModelProvidersFactory


    lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViewModel()
        initObservers()

        txtRegister.setOnClickListener(this)
        txtNoAccount.setOnClickListener(this)
        btSubmit.setOnClickListener(this)
    }

    private fun initViewModel() {
        authViewModel = ViewModelProvider(this, providersFactory).get(
            AuthViewModel::class.java
        )
    }

    private fun initObservers() {
        authViewModel.getLoginResLiveData().observe(this, Observer { response ->
            when (response.status) {

                Resource.Status.LOADING -> progressBar.showProgressbar()

                Resource.Status.ERROR -> progressBar.hideProgressBar()

                Resource.Status.SUCCESS -> {
                    progressBar.hideProgressBar()
                }
            }
        })
    }
//Resource(status=ERROR, data=null, throwable=java.lang.IllegalArgumentException: No Retrofit annotation found. (parameter #1)
//    for method AuthApi.login)
    override fun onClick(v: View?) {
        when (v) {
            txtRegister, txtNoAccount -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }

            btSubmit -> {
                // add validations
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                authViewModel.login(email, password)
            }

        }
    }
}