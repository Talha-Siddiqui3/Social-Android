package com.social.social.repositories


import com.social.social.helper.Resource
import com.social.social.models.AuthenticationResponseModel
import com.social.social.printLog
import com.social.social.repositoriesInterfaces.AuthenticationRepositoryInterface
import com.social.social.services.RetrofitService


class AuthenticationRepository(private val retrofitService: RetrofitService) :
    AuthenticationRepositoryInterface {

    override suspend fun loginUsingPhone(phoneNumber: String): Resource<AuthenticationResponseModel?> {
        try {
            val response = retrofitService.login(mapOf("phoneNumber" to phoneNumber))
            "response-loginUsingPhone".printLog(response.toString())
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