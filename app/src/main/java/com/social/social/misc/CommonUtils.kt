package com.social.social.misc

import android.content.Context

object CommonUtils {

    fun saveUserDataLocally(
        context: Context,
        key: String?,
        data: String?
    ) {
        val sharedPrefName = "MySharedPref"
        val pref = context.getSharedPreferences(
            sharedPrefName,
            Context.MODE_PRIVATE
        ) // 0 - for private mode
        val editor = pref.edit()
        editor.putString(key, data) // Storing data
        editor.apply()
    }

    fun getUserDataLocally(context: Context, key: String?): String? {
        val result: String?
        val sharedPrefName = "MySharedPref"
        val pref =
            context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        result = pref.getString(key, null)
        return result
    }


}