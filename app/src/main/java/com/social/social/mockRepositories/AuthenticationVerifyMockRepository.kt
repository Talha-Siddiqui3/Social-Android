package com.social.social.mockRepositories


import com.social.social.helper.Resource
import com.social.social.repositoriesInterfaces.AuthenticationVerifyRepositoryInterface

class AuthenticationVerifyMockRepository : AuthenticationVerifyRepositoryInterface {
    override suspend fun verifyCode(phoneNumber: String, code: String): Resource<String> {
        return Resource.Success("")
    }

    override suspend fun resendCode(phoneNumber: String): Resource<String> {
        return Resource.Success("")
    }

    override suspend fun sendLoginRequest(phoneNumber: String): Resource<String> {
        return Resource.Success("Mock Success")
    }

}