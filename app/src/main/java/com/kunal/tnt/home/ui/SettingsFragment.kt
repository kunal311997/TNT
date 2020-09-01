package com.kunal.tnt.home.ui


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import com.google.android.play.core.review.ReviewManagerFactory
import com.kunal.tnt.R
import com.kunal.tnt.common.uils.SharedPrefClient
import com.kunal.tnt.common.uils.Utilities
import com.kunal.tnt.common.uils.Utilities.showToast
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

    private fun openSignoutDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setMessage("Are you sure, you want to sign out ?")
        alertDialogBuilder.setPositiveButton(
            "Yes"
        ) { _, _ ->

            preference.clearLoginSession()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        alertDialogBuilder.setNegativeButton(
            "No"
        ) { _, _ ->
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
    }

    private fun setProfileData() {
        txtName.text = preference.getUsername()
        txtEmail.text = preference.getEmail()
    }

    private fun initOnClickListeners() {
        txtSignOut.setOnClickListener(this)
        txtFavourites.setOnClickListener(this)
        txtNotifications.setOnClickListener(this)
        txtShareFeedback.setOnClickListener(this)
        txtInviteFriends.setOnClickListener(this)
        txtRateOurApp.setOnClickListener(this)
        txtAboutUs.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            txtSignOut -> {
                openSignoutDialog()
            }

            txtFavourites -> {
                val intent = Intent(requireActivity(), FavouritesActivity::class.java)
                startActivity(intent)
            }

            txtNotifications, txtShareFeedback, txtRateOurApp, txtAboutUs -> {
                requireActivity().showToast(resources.getString(R.string.in_development))
            }
            txtInviteFriends -> Utilities.showChooserForLinkShare(
                requireContext(),
                "Hey, Please download this amazing app."
            )
        }
    }

    fun openInAppReviewDialog() {
        val manager = ReviewManagerFactory.create(requireActivity())
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                // We got the ReviewInfo object
                val reviewInfo = request.result
            } else {
                // There was some problem, continue regardless of the result.
            }
        }
    }

}