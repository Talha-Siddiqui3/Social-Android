package com.social.social.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.social.activities.AuthenticationVerifyFields
import com.social.social.dataObjects.FieldObject
import com.social.social.helper.DataValidator
import com.social.social.helper.Resource
import com.social.social.helper.ResourceValidation
import com.social.social.repositoriesInterfaces.AuthenticationVerifyRepositoryInterface
import kotlinx.coroutines.launch

class AuthenticationVerifyViewModel(private val authenticationVerifyRepository: AuthenticationVerifyRepositoryInterface) :
    ViewModel() {

    private val validationLiveData =
        MutableLiveData<ResourceValidation<ArrayList<FieldObject<AuthenticationVerifyFields>>>>()
    private val dataValidator = DataValidator<AuthenticationVerifyFields>()

    fun getValidationLiveData(): LiveData<ResourceValidation<ArrayList<FieldObject<AuthenticationVerifyFields>>>> {
        return validationLiveData
    }

    private val onServerResponseLiveData = MutableLiveData<Resource<String>>()
    private val onResendCodeServerResponseLiveData = MutableLiveData<Resource<String>>()

    fun getOnServerResponseLiveData(): LiveData<Resource<String>> {
        return onServerResponseLiveData
    }


    fun sendVerifyOtpRequest(phoneNumber: String) {
        onServerResponseLiveData.value = Resource.Loading()

        this.viewModelScope.launch {
            onServerResponseLiveData.value =
                authenticationVerifyRepository.sendLoginRequest(phoneNumber)
        }
    }


    fun validateAllFields(allFieldObjects: ArrayList<FieldObject<AuthenticationVerifyFields>>) {
        var allFieldsValid = true
        for (currentFieldObject in allFieldObjects) {
            dataValidator.isOtpValid(currentFieldObject)
            if (allFieldsValid && !currentFieldObject.valid) {
                allFieldsValid = false
            }
        }
        validationLiveData.value =
            if (allFieldsValid) ResourceValidation.Success(allFieldObjects) else ResourceValidation.Error(
                allFieldObjects
            )
    }
}