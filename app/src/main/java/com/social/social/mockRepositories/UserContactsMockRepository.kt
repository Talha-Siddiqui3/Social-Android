package com.social.social.mockRepositories


import com.social.social.helper.Resource
import com.social.social.models.ConversationModel
import com.social.social.models.UserContactsModel
import com.social.social.models.responseModels.ConversationResponseModel
import com.social.social.models.responseModels.UserContactsResponseModel
import com.social.social.printLog
import com.social.social.repositoriesInterfaces.UserContactsRepositoryInterface
import com.social.social.services.RetrofitService
import kotlinx.coroutines.delay
import java.util.*

class UserContactsMockRepository(private val retrofitService: RetrofitService) :
    UserContactsRepositoryInterface {


    override suspend fun getUserContacts(
        userID: String,
        contactsList:List<String>
    ): Resource<UserContactsResponseModel?> {
        delay(500)
        val userContactsResponseModel =
            UserContactsResponseModel(
                listOf(
                    UserContactsModel(
                        "Sam",
                        "Joe",
                        "123",
                        "+1456456123",
                        "https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8cGVyc29uYXxlbnwwfHwwfHw%3D&w=1000&q=80",
                    ),
                    UserContactsModel(
                        "Erwin",
                        "Schrödinger",
                        "123",
                        "+1456456123",
                        "https://www.nobelprize.org/images/schrodinger-12988-portrait-medium.jpg",
                    ),
                ),
                success = true,
                error = null,
                message = null
            )

        return Resource.Success(userContactsResponseModel)
    }


}