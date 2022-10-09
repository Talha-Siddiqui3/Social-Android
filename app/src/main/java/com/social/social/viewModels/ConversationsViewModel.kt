package com.social.social.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.social.helper.Resource
import com.social.social.models.ConversationModel
import com.social.social.repositoriesInterfaces.ConversationsRepositoryInterface
import kotlinx.coroutines.launch

class ConversationsViewModel(private val conversationsRepository: ConversationsRepositoryInterface) :
    ViewModel() {

    private val onServerResponseLiveData = MutableLiveData<Resource<List<ConversationModel?>>>()


    fun getOnServerResponseLiveData(): LiveData<Resource<List<ConversationModel?>>> {
        return onServerResponseLiveData
    }


    fun getConversations(userID: String) {
        onServerResponseLiveData.value = Resource.Loading()

        this.viewModelScope.launch {
            val response =
                conversationsRepository.getConversations(userID)
            if (response is Resource.Success) {
                onServerResponseLiveData.value =
                    Resource.Success(response.data?.conversations ?: listOf())
                return@launch
            }
            onServerResponseLiveData.value = Resource.Error(response.message, null)
        }
    }


}