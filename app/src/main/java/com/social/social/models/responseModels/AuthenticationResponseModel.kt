package com.social.social.models.responseModels

class AuthenticationResponseModel(
    override val message: String?,
    override val success: Boolean,
    override val error: String?
) : BaseServerResponseModel() {
}