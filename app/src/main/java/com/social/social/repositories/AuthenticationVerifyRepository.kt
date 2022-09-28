package com.social.social.repositories


import com.social.social.helper.Resource
import com.social.social.repositoriesInterfaces.AuthenticationVerifyRepositoryInterface
import kotlinx.coroutines.suspendCancellableCoroutine

class AuthenticationVerifyRepository : AuthenticationVerifyRepositoryInterface {

    override suspend fun verifyCode(phoneNumber: String, code: String): Resource<String> =
        suspendCancellableCoroutine { coroutine ->

        }

    override suspend fun resendCode(phoneNumber: String): Resource<String> {
        return Resource.Success("Success")
    }

    override suspend fun sendLoginRequest(phoneNumber: String): Resource<String> {
        return Resource.Success("")
    }
}