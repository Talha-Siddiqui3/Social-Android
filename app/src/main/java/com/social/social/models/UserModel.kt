package com.social.social.models

class UserModel(
    val id: String,
    val firstName: String?,
    val lastName: String?,
    val profilePicture: String?,
    val bio: String?,
    val phoneNumber: String,
    val isActive: Boolean?
)