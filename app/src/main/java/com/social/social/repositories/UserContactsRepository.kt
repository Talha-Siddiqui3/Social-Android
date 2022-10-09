package com.social.social.repositories


import com.social.social.helper.Resource
import com.social.social.models.responseModels.UserContactsResponseModel
import com.social.social.printLog
import com.social.social.repositoriesInterfaces.UserContactsRepositoryInterface
import com.social.social.services.RetrofitService

class UserContactsRepository(private val retrofitService: RetrofitService) :
    UserContactsRepositoryInterface {


    override suspend fun getUserContacts(
        userID: String,
        contactsList: List<String>
    ): Resource<UserContactsResponseModel?> {
        try {
            val response =
                retrofitService.getUserContacts(
                    mapOf(
                        "userID" to userID,
                        "contactsList" to contactsList
                    )
                )
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