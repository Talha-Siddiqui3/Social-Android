package com.social.social.models.responseModels

import com.google.gson.annotations.SerializedName
import com.social.social.models.UserModel

class AuthenticationVerifyResponseModel(
    override val message: String?,
    override val success: Boolean,
    override val error: String?,
    val accessToken:String?,
    @SerializedName("userData")
    val userModel: UserModel?
) : BaseServerResponseModel() {
}