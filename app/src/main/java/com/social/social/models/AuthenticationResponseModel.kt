package com.social.social.models

class AuthenticationResponseModel(
    override val message: String?,
    override val success: Boolean,
    override val error: String?
) : BaseServerResponseModel() {
}