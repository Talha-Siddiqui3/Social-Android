package com.social.social.mockRepositories

import com.social.social.helper.Resource
import com.social.social.models.AuthenticationResponseModel
import com.social.social.repositoriesInterfaces.AuthenticationRepositoryInterface
import kotlinx.coroutines.delay

class AuthenticationMockRepository : AuthenticationRepositoryInterface {
    override suspend fun loginUsingPhone(phoneNumber: String): Resource<AuthenticationResponseModel?> {
        delay(5000)
        return Resource.Success(AuthenticationResponseModel(200, null, true, null))
    }
}