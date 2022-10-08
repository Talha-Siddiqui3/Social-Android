package com.social.social.repositoriesInterfaces

import com.social.social.helper.Resource
import com.social.social.models.AuthenticationResponseModel
import com.social.social.models.UserInformationDataModel
import com.social.social.models.UserInformationResponseModel
import okhttp3.MultipartBody


interface UserInfoRepositoryInterface {

    suspend fun updateUser(
        userInformationDataModel: UserInformationDataModel,
        profilePictureFile: MultipartBody.Part?
    ): Resource<UserInformationResponseModel?>
}