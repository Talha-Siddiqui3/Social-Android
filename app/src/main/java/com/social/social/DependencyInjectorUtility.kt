package com.social.social


import com.social.social.misc.AppSettings
import com.social.social.mockRepositories.*
import com.social.social.repositories.*
import com.social.social.repositoriesInterfaces.*
import com.social.social.services.RetrofitService
import com.social.social.viewModelFactoryClasses.*
import com.social.social.viewModels.ConversationsViewModel

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

    fun getConversationsViewModelFactory(): ConversationViewModelFactoryClass {
        val conversationsRepository: ConversationsRepositoryInterface =
            if (AppSettings.isLiveMode) ConversationsRepository(RetrofitService.getInstance()) else ConversationsMockRepository(RetrofitService.getInstance())
        return ConversationViewModelFactoryClass(conversationsRepository)
    }

    fun getUserContactsViewModelFactory(): UserContactsViewModelFactoryClass {
        val userContactsRepository: UserContactsRepositoryInterface =
            if (AppSettings.isLiveMode) UserContactsRepository(RetrofitService.getInstance()) else UserContactsMockRepository(RetrofitService.getInstance())
        return UserContactsViewModelFactoryClass(userContactsRepository)
    }

}