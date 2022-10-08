package com.social.social.models

import com.google.gson.annotations.SerializedName

class AuthenticationVerifyResponseModel(
    override val message: String?,
    override val success: Boolean,
    override val error: String?,
    val accessToken:String?,
    @SerializedName("userData")
    val userModel: UserModel?
) : BaseServerResponseModel() {
}