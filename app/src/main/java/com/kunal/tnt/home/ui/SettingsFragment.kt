package com.kunal.tnt.home.ui


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import com.kunal.tnt.R
import com.kunal.tnt.common.uils.SharedPrefClient
import com.kunal.tnt.enroll.ui.LoginActivity
import com.kunal.tnt.favourites.ui.FavouritesActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject


class SettingsFragment : DaggerFragment(), View.OnClickListener {

    @Inject
    lateinit var preference: SharedPrefClient

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_settings, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setProfileData()
        initOnClickListeners()
    }

    private fun setProfileData() {
        txtName.text = preference.getUsername()
        txtEmail.text = preference.getEmail()
    }

    private fun initOnClickListeners() {
        txtSignOut.setOnClickListener(this)
        txtFavourites.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            txtSignOut -> {
                preference.clearLoginSession()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

            txtFavourites -> {
                val intent = Intent(requireActivity(), FavouritesActivity::class.java)
                startActivity(intent)

            }
        }
    }
}