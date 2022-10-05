package com.social.social.mockRepositories

import com.social.social.helper.Resource
import com.social.social.models.UserInformationDataModel
import com.social.social.models.UserInformationResponseModel
import com.social.social.repositoriesInterfaces.UserInfoRepositoryInterface
import kotlinx.coroutines.delay

class UserInfoMockRepository : UserInfoRepositoryInterface {
    override suspend fun createUpdateUser(userInformationDataModel: UserInformationDataModel): Resource<UserInformationResponseModel?> {
        delay(5000)
        return Resource.Success(UserInformationResponseModel(200, null, true, null))
    }
}