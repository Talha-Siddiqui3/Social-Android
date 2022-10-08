package com.social.social.services

import com.social.social.getSharedPrefString
import com.social.social.misc.MyApplication
import com.social.social.misc.SharedPrefKeys
import com.social.social.setSharedPrefString


object AuthService {

    private var accessToken: String? = null
    private var userID: String? = null

    fun getAccessToken(): String? {
        if (accessToken == null) {
            accessToken = MyApplication.applicationContext.getSharedPrefString(SharedPrefKeys.accessToken)
        }
        return accessToken
    }

    fun storeAccessToken(accessToken:String) {
        MyApplication.applicationContext.setSharedPrefString(SharedPrefKeys.accessToken, accessToken)
    }

    fun getUserID(): String? {
        if (userID == null) {
            userID = MyApplication.applicationContext.getSharedPrefString(SharedPrefKeys.userID)
        }
        return userID
    }

    fun storeUserID(userID:String) {
        MyApplication.applicationContext.setSharedPrefString(SharedPrefKeys.userID, userID)
    }

}