package com.social.social.repositories


import com.social.social.createPartFromString
import com.social.social.helper.Resource
import com.social.social.models.UserInformationDataModel
import com.social.social.models.UserInformationResponseModel
import com.social.social.models.responseModels.ConversationResponseModel
import com.social.social.printLog
import com.social.social.repositoriesInterfaces.ConversationsRepositoryInterface
import com.social.social.services.RetrofitService
import okhttp3.MultipartBody
import okhttp3.RequestBody


class ConversationsRepository(private val retrofitService: RetrofitService) :
    ConversationsRepositoryInterface {


    override suspend fun getConversations(
        userID: String,
    ): Resource<ConversationResponseModel?> {
        try {
            val response =
                retrofitService.getConversations(userID)
            "response-getConversations".printLog(response.toString())
            if (response.body() != null && response.body()?.success == true) {
                return Resource.Success(response.body()!!)
            }
            return Resource.Error(response.body()?.error, response?.body())
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(null, null)
        }
    }


}