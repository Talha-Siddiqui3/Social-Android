package com.social.social.models.responseModels

import com.social.social.models.UserContactsModel

class UserContactsResponseModel(
    val userContacts: List<UserContactsModel?>?,
    override val message: String?,
    override val success: Boolean,
    override val error: String?
) : BaseServerResponseModel() {
}