package com.kunal.tnt.enroll.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kunal.tnt.R
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.common.uils.SharedPrefClient
import com.kunal.tnt.common.uils.Utilities.hideProgressBar
import com.kunal.tnt.common.uils.Utilities.isValidEmail
import com.kunal.tnt.common.uils.Utilities.showProgressbar
import com.kunal.tnt.common.uils.Utilities.showToast
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.enroll.viewmodel.AuthViewModel
import com.kunal.tnt.home.ui.HomeActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var providersFactory: ViewModelProvidersFactory

    private lateinit var authViewModel: AuthViewModel

    @Inject
    lateinit var preference: SharedPrefClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViewModel()
        initObservers()
        initOnClickListeners()
    }

    private fun initOnClickListeners() {
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

                Resource.Status.ERROR -> {
                    progressBar.hideProgressBar()
                    this.showToast(response.data?.message.toString())
                }

                Resource.Status.SUCCESS -> {
                    progressBar.hideProgressBar()
                    preference.updateEmail(response.data?.email.toString())
                    preference.updateUsername(response.data?.username.toString())
                    preference.updateBearerToken(response.data?.token.toString())
                    preference.updateIsUserLoggedIn(true)

                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        })
    }

    override fun onClick(v: View?) {

        when (v) {

            txtRegister, txtNoAccount -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }

            btSubmit -> {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()

                when {
                    !email.isValidEmail() -> this.showToast(resources.getString(R.string.invalid_email))
                    password.isEmpty() -> this.showToast(resources.getString(R.string.invalid_password))
                    email.isValidEmail() && password.isNotEmpty() ->
                        authViewModel.login(email, password)
                }

            }

        }
    }
}