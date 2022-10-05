package com.social.social.repositoriesInterfaces

import com.social.social.helper.Resource
import com.social.social.models.AuthenticationResponseModel
import com.social.social.models.UserInformationDataModel
import com.social.social.models.UserInformationResponseModel


interface UserInfoRepositoryInterface {

    suspend fun createUpdateUser(userInformationDataModel: UserInformationDataModel): Resource<UserInformationResponseModel?>
}