package com.social.social.mockRepositories

import com.social.social.helper.Resource
import com.social.social.models.UserInformationDataModel
import com.social.social.models.UserInformationResponseModel
import com.social.social.printLog
import com.social.social.repositoriesInterfaces.UserInfoRepositoryInterface
import kotlinx.coroutines.delay
import okhttp3.MultipartBody

class UserInfoMockRepository : UserInfoRepositoryInterface {
    override suspend fun updateUser(
        userInformationDataModel: UserInformationDataModel,
        profilePictureFile: MultipartBody.Part?
    ): Resource<UserInformationResponseModel?> {
        "userInformationDataModel".printLog("Mocking")
        delay(5000)
        return Resource.Success(UserInformationResponseModel(null, true, null))
    }
}