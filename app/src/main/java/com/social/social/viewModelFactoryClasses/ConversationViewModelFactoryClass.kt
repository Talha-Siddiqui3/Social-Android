package com.social.social.viewModelFactoryClasses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.social.social.repositoriesInterfaces.ConversationsRepositoryInterface
import com.social.social.viewModels.ConversationsViewModel
import com.social.social.viewModels.UserInfoViewModel

@Suppress("UNCHECKED_CAST")
class ConversationViewModelFactoryClass(private val conversationsRepository: ConversationsRepositoryInterface) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ConversationsViewModel(conversationsRepository) as T
    }
}