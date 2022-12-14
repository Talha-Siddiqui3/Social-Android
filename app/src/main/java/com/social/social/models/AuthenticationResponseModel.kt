package com.social.social.models

import com.social.social.models.responseModels.BaseServerResponseModel

class AuthenticationResponseModel(
    override val message: String?,
    override val success: Boolean,
    override val error: String?
) : BaseServerResponseModel() {
}