package com.social.social.mockRepositories


import com.social.social.helper.Resource
import com.social.social.models.AuthenticationVerifyResponseModel
import com.social.social.models.UserModel
import com.social.social.models.UserObject
import com.social.social.repositoriesInterfaces.AuthenticationVerifyRepositoryInterface
import kotlinx.coroutines.delay

class AuthenticationVerifyMockRepository : AuthenticationVerifyRepositoryInterface {
    override suspend fun verifyCode(
        phoneNumber: String,
        code: String
    ): Resource<AuthenticationVerifyResponseModel?> {
        delay(5000)
        return Resource.Success(
            AuthenticationVerifyResponseModel(
                null,
                true,
                null,
                "abc",
                UserModel("123", null, null, null, null, "123", null)
            )
        )
    }

}