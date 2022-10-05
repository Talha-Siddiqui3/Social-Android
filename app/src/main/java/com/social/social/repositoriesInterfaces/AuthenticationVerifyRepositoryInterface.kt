package com.social.social.repositoriesInterfaces

import com.social.social.helper.Resource
import com.social.social.models.AuthenticationVerifyResponseModel

interface AuthenticationVerifyRepositoryInterface {
    suspend fun verifyCode(phoneNumber: String, code: String): Resource<AuthenticationVerifyResponseModel?>
}