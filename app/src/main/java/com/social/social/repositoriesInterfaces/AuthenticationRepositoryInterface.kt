package com.social.social.repositoriesInterfaces

import com.social.social.helper.Resource


interface AuthenticationRepositoryInterface {

    suspend fun loginUsingPhone(phoneNumber: String): Resource<String>
}