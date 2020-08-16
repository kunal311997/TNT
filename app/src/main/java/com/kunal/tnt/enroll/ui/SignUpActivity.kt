package com.kunal.tnt.enroll.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kunal.tnt.R
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.common.uils.SharedPrefClient
import com.kunal.tnt.common.uils.Utilities.gone
import com.kunal.tnt.common.uils.Utilities.isValidEmail
import com.kunal.tnt.common.uils.Utilities.visible
import com.kunal.tnt.common.uils.Utilities.showToast
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.enroll.viewmodel.AuthViewModel
import com.kunal.tnt.home.ui.HomeActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject

class SignUpActivity : DaggerAppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var providersFactory: ViewModelProvidersFactory

    private lateinit var authViewModel: AuthViewModel

    @Inject
    lateinit var preference: SharedPrefClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initViewModel()
        initObservers()
        initOnClickListeners()
    }

    private fun initViewModel() {
        authViewModel = ViewModelProvider(this, providersFactory).get(
            AuthViewModel::class.java
        )
    }

    private fun initObservers() {
        authViewModel.getLoginResLiveData().observe(this, Observer { response ->
            when (response.status) {

                Resource.Status.LOADING -> progressBar.visible()

                Resource.Status.ERROR -> {
                    progressBar.gone()
                    this.showToast(response.data?.message.toString())
                }

                Resource.Status.SUCCESS -> {
                    progressBar.gone()
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

    private fun initOnClickListeners() {
        txtLogin.setOnClickListener(this)
        txtNoAccount.setOnClickListener(this)
        btSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            txtLogin, txtNoAccount -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            btSubmit -> {
                val username = edtName.text.toString()
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()

                when {
                    username.isEmpty() -> this.showToast(resources.getString(R.string.invalid_username))
                    !email.isValidEmail() -> this.showToast(resources.getString(R.string.invalid_email))
                    password.isEmpty() -> this.showToast(resources.getString(R.string.invalid_password))
                    email.isValidEmail() && password.isNotEmpty() && username.isNotEmpty() ->
                        authViewModel.signUp(username, email, password)
                }


            }
        }
    }
}