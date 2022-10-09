package com.social.social.repositoriesInterfaces

import com.social.social.helper.Resource
import com.social.social.models.AuthenticationResponseModel
import com.social.social.models.UserInformationDataModel
import com.social.social.models.UserInformationResponseModel
import com.social.social.models.responseModels.ConversationResponseModel
import com.social.social.models.responseModels.UserContactsResponseModel
import okhttp3.MultipartBody

interface UserContactsRepositoryInterface {
    suspend fun getUserContacts(
        userID: String,
        contactsList:List<String>
    ): Resource<UserContactsResponseModel?>
}