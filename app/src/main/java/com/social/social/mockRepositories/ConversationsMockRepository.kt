package com.social.social.mockRepositories


import com.social.social.createPartFromString
import com.social.social.helper.Resource
import com.social.social.models.ConversationModel
import com.social.social.models.UserInformationDataModel
import com.social.social.models.UserInformationResponseModel
import com.social.social.models.responseModels.ConversationResponseModel
import com.social.social.printLog
import com.social.social.repositoriesInterfaces.ConversationsRepositoryInterface
import com.social.social.services.RetrofitService
import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.*


class ConversationsMockRepository(private val retrofitService: RetrofitService) :
    ConversationsRepositoryInterface {


    override suspend fun getConversations(
        userID: String,
    ): Resource<ConversationResponseModel?> {
        delay(100)
        val conversationResponseModel =
            ConversationResponseModel(
                listOf(
                    ConversationModel(
                        "Richard",
                        "Lee",
                        "123",
                        "https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8cGVyc29uYXxlbnwwfHwwfHw%3D&w=1000&q=80",
                        "Hey!, how are you?",
                        "123",
                        Date(),
                        true
                    ),
                    ConversationModel(
                        "Jhon",
                        "Cena",
                        "123",
                        "https://resizing.flixster.com/CbqVJ1ytK31FEiKPnndNscCvYTo=/218x280/v2/https://flxt.tmsimg.com/assets/487578_v9_ba.jpg",
                        "Can you see me?",
                        "123",
                        Date(),
                    )
                ),
                success = true,
                error = null,
                message = null
            )

        return Resource.Success(conversationResponseModel)
    }


}