package com.social.social.viewModelFactoryClasses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.social.social.repositoriesInterfaces.AuthenticationRepositoryInterface
import com.social.social.viewModels.AuthenticationViewModel

@Suppress("UNCHECKED_CAST")
class AuthenticationViewModelFactoryClass(private val authenticationRepository: AuthenticationRepositoryInterface) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthenticationViewModel(authenticationRepository) as T
    }
}