package com.example.recipeapp.authintication

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("USER_PREF", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    // Save login status
    fun saveLoginStatus(isLoggedIn: Boolean) {
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    // Check if user is logged in
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    // Clear login status
    fun logout() {
        editor.clear()
        editor.apply()
    }

    companion object {
        @Volatile
        private var instance: SharedPrefManager? = null

        @JvmStatic
        fun getInstance(context: Context): SharedPrefManager {
            return instance ?: synchronized(this) {
                instance ?: SharedPrefManager(context).also { instance = it }
            }
        }
    }
}
