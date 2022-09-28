package com.social.social.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.social.activities.AuthenticationFields
import com.social.social.dataObjects.FieldObject
import com.social.social.helper.DataValidator
import com.social.social.helper.Resource
import com.social.social.helper.ResourceValidation
import com.social.social.repositoriesInterfaces.AuthenticationRepositoryInterface
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val authenticationRepository: AuthenticationRepositoryInterface) :
    ViewModel() {

    private val validationLiveData =
        MutableLiveData<ResourceValidation<ArrayList<FieldObject<AuthenticationFields>>>>()

    private val dataValidator = DataValidator<AuthenticationFields>()
    private val onServerResponseLiveData = MutableLiveData<Resource<String>>()


    fun getOnServerResponseLiveData(): LiveData<Resource<String>> {
        return onServerResponseLiveData
    }

    fun getValidationLiveData(): LiveData<ResourceValidation<ArrayList<FieldObject<AuthenticationFields>>>> {
        return validationLiveData
    }


    fun loginUsingPhone(phoneNumber: String) {
        onServerResponseLiveData.value = Resource.Loading()

        this.viewModelScope.launch {
            onServerResponseLiveData.value = authenticationRepository.loginUsingPhone(phoneNumber)
        }
    }


    fun funValidateAllFields(allFieldObjects: ArrayList<FieldObject<AuthenticationFields>>) {
        var allFieldsValid = true
        for (currentFieldObject in allFieldObjects) {
            when (currentFieldObject.fieldEnum) {
                AuthenticationFields.PhoneNumber -> {
                    dataValidator.isValidMobileNumber(currentFieldObject)
                }
            }
            //if any field becomes invalid we change allFieldsValid variable to false permanently
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