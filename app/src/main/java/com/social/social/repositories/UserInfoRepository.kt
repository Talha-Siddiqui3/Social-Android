package com.social.social.repositories


import com.social.social.helper.Resource
import com.social.social.models.AuthenticationResponseModel
import com.social.social.models.UserInformationDataModel
import com.social.social.models.UserInformationResponseModel
import com.social.social.printLog
import com.social.social.repositoriesInterfaces.AuthenticationRepositoryInterface
import com.social.social.repositoriesInterfaces.UserInfoRepositoryInterface
import com.social.social.services.RetrofitService


class UserInfoRepository(private val retrofitService: RetrofitService) :
    UserInfoRepositoryInterface {


    override suspend fun createUpdateUser(userInformationDataModel: UserInformationDataModel): Resource<UserInformationResponseModel?> {
        try {
            val response =
                retrofitService.saveUserInformation(userInformationDataModel)
            "response-createUpdateUser".printLog(response.toString())
            if (response.body() != null && response.body()?.success == true) {
                return Resource.Success(response.body())
            }
            return Resource.Error(response.body()?.error, response.body())
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(null, null)
        }
    }


}