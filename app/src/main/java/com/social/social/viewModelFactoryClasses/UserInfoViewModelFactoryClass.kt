package com.social.social.viewModelFactoryClasses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.social.social.repositories.UserInfoRepository
import com.social.social.repositoriesInterfaces.AuthenticationVerifyRepositoryInterface
import com.social.social.repositoriesInterfaces.UserInfoRepositoryInterface
import com.social.social.viewModels.AuthenticationVerifyViewModel
import com.social.social.viewModels.UserInfoViewModel

@Suppress("UNCHECKED_CAST")
class UserInfoViewModelFactoryClass(private val userInfoRepository: UserInfoRepositoryInterface) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserInfoViewModel(userInfoRepository) as T
    }
}