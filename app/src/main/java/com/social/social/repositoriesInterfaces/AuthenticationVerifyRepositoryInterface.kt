package com.social.social.repositoriesInterfaces

import com.social.social.helper.Resource

interface AuthenticationVerifyRepositoryInterface {
    suspend fun verifyCode(phoneNumber: String, code: String): Resource<String>
    suspend fun resendCode(phoneNumber: String): Resource<String>
    suspend fun sendLoginRequest(phoneNumber: String): Resource<String>
}