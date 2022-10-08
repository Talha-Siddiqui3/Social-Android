package com.social.social.models

import com.social.social.getSharedPrefString
import com.social.social.misc.MyApplication
import com.social.social.misc.SharedPrefKeys
import com.social.social.services.AuthService

object UserObject {
    var id: String
    var firstName: String? = null
    var lastName: String? = null
    var profilePicture: String? = null
    var bio: String? = null
    lateinit var phoneNumber: String
    var isActive: Boolean? = null;

    init {
        id = AuthService.getUserID()!!
    }

    fun init(userModel: UserModel) {
        id = userModel.id
        firstName = userModel.firstName
        lastName = userModel.lastName
        profilePicture = userModel.profilePicture
        bio = userModel.bio
        phoneNumber = userModel.phoneNumber
        isActive = userModel.isActive
    }


}