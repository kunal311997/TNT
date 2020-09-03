package com.kunal.tnt.common.uils

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SharedPrefClient @Inject constructor(private val sharedPreferences: SharedPreferences) {


    fun clearLoginSession() {
        sharedPreferences.edit(commit = true) {
            this.clear().apply()
        }
    }

    private val USER_LOGGED_IN = "user_logged_in"

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(USER_LOGGED_IN, false)
    }

    private val WALK_THROUGH_DONE = "walk_through_done"

    fun updateWalkThroughDone(data: Boolean) {
        sharedPreferences.edit(commit = true) {
            putBoolean(WALK_THROUGH_DONE, data)
        }
    }

    fun isWalkThroughDone(): Boolean {
        return sharedPreferences.getBoolean(WALK_THROUGH_DONE, false)
    }

    fun updateIsUserLoggedIn(data: Boolean) {
        sharedPreferences.edit(commit = true) {
            putBoolean(USER_LOGGED_IN, data)
        }
    }

    private val KEY_BEARER_TOKEN = "b_token"

    fun getBearerToken(): String {
        return sharedPreferences.getString(KEY_BEARER_TOKEN, "") ?: ""
    }

    fun updateBearerToken(token: String) {
        sharedPreferences.edit(commit = true) {
            putString(KEY_BEARER_TOKEN, token)
        }
    }

    private val KEY_USERNAME = "username"

    fun getUsername(): String {
        return sharedPreferences.getString(KEY_USERNAME, "") ?: ""
    }

    fun updateUsername(data: String) {
        sharedPreferences.edit(commit = true) {
            putString(KEY_USERNAME, data)
        }
    }

    private val KEY_EMAIL = "email"

    fun getEmail(): String {
        return sharedPreferences.getString(KEY_EMAIL, "") ?: ""
    }

    fun updateEmail(data: String) {
        sharedPreferences.edit(commit = true) {
            putString(KEY_EMAIL, data)
        }
    }

}