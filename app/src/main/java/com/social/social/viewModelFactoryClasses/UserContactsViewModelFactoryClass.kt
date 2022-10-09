package com.social.social.viewModelFactoryClasses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.social.social.repositoriesInterfaces.UserContactsRepositoryInterface
import com.social.social.viewModels.ConversationsViewModel
import com.social.social.viewModels.UserContactsViewModel

@Suppress("UNCHECKED_CAST")
class UserContactsViewModelFactoryClass(private val userContactRepository: UserContactsRepositoryInterface) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserContactsViewModel(userContactRepository) as T
    }
}