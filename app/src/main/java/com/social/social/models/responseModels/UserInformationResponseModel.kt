package com.social.social.models.responseModels

class UserInformationResponseModel(
    override val message: String?,
    override val success: Boolean,
    override val error: String?,
    var id: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var profilePicture: String? = null,
    var bio: String? = null,
    var phoneNumber: String? = null,
    var isActive: Boolean? = null,
) : BaseServerResponseModel() {
}