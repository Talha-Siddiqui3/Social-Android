package com.social.social.models

class UserInformationResponseModel(
    override val statusCode: Int,
    override val message: String?,
    override val success: Boolean,
    override val error: String?
) : BaseServerResponseModel() {
}