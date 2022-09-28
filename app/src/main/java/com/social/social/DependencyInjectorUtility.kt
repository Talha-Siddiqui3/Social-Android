package com.social.social


import com.social.social.misc.AppSettings
import com.social.social.mockRepositories.AuthenticationMockRepository
import com.social.social.repositories.AuthenticationRepository
import com.social.social.repositoriesInterfaces.AuthenticationRepositoryInterface
import com.social.social.viewModelFactoryClasses.AuthenticationViewModelFactoryClass

object DependencyInjectorUtility {
    fun getAuthenticationViewModelFactory(): AuthenticationViewModelFactoryClass {
        val authRepository: AuthenticationRepositoryInterface =
            if (AppSettings.isLiveMode) AuthenticationRepository() else AuthenticationMockRepository()
        return AuthenticationViewModelFactoryClass(authRepository)
    }

//    fun getAuthenticationVerifyViewModelFactory(): AuthenticationVerifyViewModelFactoryClass {
//        val authRepository: AuthenticationVerifyRepositoryInterface =
//            if (AppSettings.isLiveMode) AuthenticationVerifyRepository() else AuthenticationVerifyMockRepository()
//        return AuthenticationVerifyViewModelFactoryClass(authRepository)
//    }


}