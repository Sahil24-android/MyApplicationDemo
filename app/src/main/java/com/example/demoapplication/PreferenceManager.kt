package com.example.demoapplication

import android.content.Context
import android.content.SharedPreferences
import com.example.demoapplication.model.RegisterUserResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferenceManager(context: Context) {


    companion object {
        private const val PREF_NAME = "UserSession"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val USERDATA = "userData"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()



    fun clearSession() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun setUserData(user: RegisterUserResponse) {
        val editor = sharedPreferences.edit()
        val jsonString = gson.toJson(user)
        editor.putString(USERDATA, jsonString)
        editor.apply()
    }

    fun getUserData(): RegisterUserResponse? {
        val jsonString = sharedPreferences.getString(USERDATA, null) ?: return null
        return gson.fromJson(jsonString, object : TypeToken<RegisterUserResponse>() {}.type)
    }

    fun setLoginStatus(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }
}