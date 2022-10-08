package com.social.social.repositories


import com.social.social.createPartFromString
import com.social.social.helper.Resource
import com.social.social.models.UserInformationDataModel
import com.social.social.models.UserInformationResponseModel
import com.social.social.printLog
import com.social.social.repositoriesInterfaces.UserInfoRepositoryInterface
import com.social.social.services.RetrofitService
import okhttp3.MultipartBody
import okhttp3.RequestBody


class UserInfoRepository(private val retrofitService: RetrofitService) :
    UserInfoRepositoryInterface {


    override suspend fun updateUser(
        userInformationDataModel: UserInformationDataModel,
        profilePictureFile: MultipartBody.Part?
    ): Resource<UserInformationResponseModel?> {
        "userInformationDataModel".printLog("logginh now")
        "userInformationDataModel".printLog(userInformationDataModel)
        try {
            val formDataMap = mutableMapOf<String,RequestBody?>()
            formDataMap["firstName"] = createPartFromString(userInformationDataModel.firstName)
            formDataMap["lastName"] = createPartFromString(userInformationDataModel.lastName)
            formDataMap["id"] = createPartFromString(userInformationDataModel.id)
            val response =
                retrofitService.updateUser(profilePictureFile, formDataMap)
            "response-updateUser".printLog(response.toString())
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