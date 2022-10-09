package com.social.social.services

import com.social.social.misc.Constants
import com.social.social.models.AuthenticationResponseModel
import com.social.social.models.AuthenticationVerifyResponseModel
import com.social.social.models.UserInformationResponseModel
import com.social.social.models.responseModels.ConversationResponseModel
import com.social.social.models.responseModels.UserContactsResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {

    @POST("/auth/login")
    suspend fun login(@Body body: Map<String, String>): Response<AuthenticationResponseModel>

    @POST("/auth/verify")
    suspend fun verify(@Body body: Map<String, String>): Response<AuthenticationVerifyResponseModel>

    @Multipart()
    @PUT("/user")
    suspend fun updateUser(
        @Part profilePictureFile: MultipartBody.Part?,
        @PartMap() partMap: MutableMap<String, RequestBody?>
    ): Response<UserInformationResponseModel>

    @GET("/conversations")
    suspend fun getConversations(@Query("userID") userID: String): Response<ConversationResponseModel?>

    @JvmSuppressWildcards
    @POST("/conversations/userContacts")
    suspend fun getUserContacts(@Body body: Map<String, Any>): Response<UserContactsResponseModel?>

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