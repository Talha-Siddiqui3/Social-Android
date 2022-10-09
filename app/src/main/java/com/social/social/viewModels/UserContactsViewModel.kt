package com.social.social.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.social.helper.Resource
import com.social.social.models.ConversationModel
import com.social.social.models.UserContactsModel
import com.social.social.repositoriesInterfaces.UserContactsRepositoryInterface
import kotlinx.coroutines.launch

class UserContactsViewModel(private val userContactsRepository: UserContactsRepositoryInterface) :
    ViewModel() {

    private val onServerResponseLiveData = MutableLiveData<Resource<List<UserContactsModel?>>>()


    fun getOnServerResponseLiveData(): LiveData<Resource<List<UserContactsModel?>>> {
        return onServerResponseLiveData
    }


    fun getUserContacts(userID: String, contactsList: ArrayList<String>) {
        onServerResponseLiveData.value = Resource.Loading()

        this.viewModelScope.launch {
            val response =
                userContactsRepository.getUserContacts(userID, contactsList)
            if (response is Resource.Success) {
                onServerResponseLiveData.value =
                    Resource.Success(response.data?.userContacts ?: listOf())
                return@launch
            }
            onServerResponseLiveData.value = Resource.Error(response.message, null)
        }
    }


}