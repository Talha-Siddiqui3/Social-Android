package com.social.social.repositoriesInterfaces

import com.social.social.helper.Resource
import com.social.social.models.AuthenticationResponseModel


interface AuthenticationRepositoryInterface {

    suspend fun loginUsingPhone(phoneNumber: String): Resource<AuthenticationResponseModel?>
}