package com.social.social.models

class AuthenticationVerifyResponseModel(
    override val statusCode: Int,
    override val message: String?,
    override val success: Boolean,
    override val error: String?,
    val accessToken:String?
) : BaseServerResponseModel() {
}