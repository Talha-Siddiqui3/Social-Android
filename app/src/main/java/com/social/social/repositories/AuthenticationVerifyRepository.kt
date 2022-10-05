package com.social.social.repositories


import com.social.social.helper.Resource
import com.social.social.models.AuthenticationVerifyResponseModel
import com.social.social.printLog
import com.social.social.repositoriesInterfaces.AuthenticationVerifyRepositoryInterface
import com.social.social.services.RetrofitService

class AuthenticationVerifyRepository(private val retrofitService: RetrofitService) :
    AuthenticationVerifyRepositoryInterface {

    override suspend fun verifyCode(
        phoneNumber: String,
        code: String
    ): Resource<AuthenticationVerifyResponseModel?> {
        try {
            val response =
                retrofitService.verify(mapOf("phoneNumber" to phoneNumber, "otpCode" to code))
            "response-verifyCode".printLog(response.toString())
            "response-verifyCode".printLog(response.body()?.accessToken)
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