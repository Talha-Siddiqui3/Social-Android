package com.social.social.models

import okhttp3.MultipartBody

class UserInformationDataModel(
    val firstName: String,
    val lastName: String?,
    val image: MultipartBody.Part?
)