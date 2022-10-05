package com.social.social.services

import com.social.social.misc.Constants
import com.social.social.models.AuthenticationResponseModel
import com.social.social.models.AuthenticationVerifyResponseModel
import com.social.social.models.UserInformationDataModel
import com.social.social.models.UserInformationResponseModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {

    @POST("/login")
    suspend fun login(@Body body: Map<String, String>): Response<AuthenticationResponseModel>

    @POST("/verify")
    suspend fun verify(@Body body: Map<String, String>): Response<AuthenticationVerifyResponseModel>

    //creates/updates a user
    @POST("/user")
    suspend fun saveUserInformation(@Body body: UserInformationDataModel): Response<UserInformationResponseModel>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.appServiceUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

    }
}