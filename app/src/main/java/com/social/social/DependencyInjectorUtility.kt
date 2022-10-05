package com.social.social


import com.social.social.misc.AppSettings
import com.social.social.mockRepositories.AuthenticationMockRepository
import com.social.social.mockRepositories.AuthenticationVerifyMockRepository
import com.social.social.mockRepositories.UserInfoMockRepository
import com.social.social.repositories.AuthenticationRepository
import com.social.social.repositories.AuthenticationVerifyRepository
import com.social.social.repositories.UserInfoRepository
import com.social.social.repositoriesInterfaces.AuthenticationRepositoryInterface
import com.social.social.repositoriesInterfaces.AuthenticationVerifyRepositoryInterface
import com.social.social.repositoriesInterfaces.UserInfoRepositoryInterface
import com.social.social.services.RetrofitService
import com.social.social.viewModelFactoryClasses.AuthenticationVerifyViewModelFactoryClass
import com.social.social.viewModelFactoryClasses.AuthenticationViewModelFactoryClass
import com.social.social.viewModelFactoryClasses.UserInfoViewModelFactoryClass

object DependencyInjectorUtility {
    fun getAuthenticationViewModelFactory(): AuthenticationViewModelFactoryClass {
        val authRepository: AuthenticationRepositoryInterface =
            if (AppSettings.isLiveMode) AuthenticationRepository(RetrofitService.getInstance()) else AuthenticationMockRepository()
        return AuthenticationViewModelFactoryClass(authRepository)
    }

    fun getAuthenticationVerifyViewModelFactory(): AuthenticationVerifyViewModelFactoryClass {
        val authRepository: AuthenticationVerifyRepositoryInterface =
            if (AppSettings.isLiveMode) AuthenticationVerifyRepository(RetrofitService.getInstance()) else AuthenticationVerifyMockRepository()
        return AuthenticationVerifyViewModelFactoryClass(authRepository)
    }

    fun getUserInfoViewModelFactory(): UserInfoViewModelFactoryClass {
        val userInfoRepository: UserInfoRepositoryInterface =
            if (AppSettings.isLiveMode) UserInfoRepository(RetrofitService.getInstance()) else UserInfoMockRepository()
        return UserInfoViewModelFactoryClass(userInfoRepository)
    }


}