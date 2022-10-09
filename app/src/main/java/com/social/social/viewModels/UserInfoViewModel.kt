package com.social.social.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.social.activities.UserInformationFields
import com.social.social.dataObjects.FieldObject
import com.social.social.helper.DataValidator
import com.social.social.helper.Resource
import com.social.social.helper.ResourceValidation
import com.social.social.misc.MyApplication
import com.social.social.models.UserInformationDataModel
import com.social.social.models.UserInformationResponseModel
import com.social.social.printLog
import com.social.social.repositoriesInterfaces.UserInfoRepositoryInterface
import com.social.social.setSharedPrefBoolean
import com.social.social.setSharedPrefString
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class UserInfoViewModel(private val userInfoRepository: UserInfoRepositoryInterface) :
    ViewModel() {

    private val validationLiveData =
        MutableLiveData<ResourceValidation<ArrayList<FieldObject<UserInformationFields>>>>()

    private val dataValidator = DataValidator<UserInformationFields>()
    private val onServerResponseLiveData = MutableLiveData<Resource<UserInformationResponseModel?>>()


    fun getOnServerResponseLiveData(): LiveData<Resource<UserInformationResponseModel?>> {
        return onServerResponseLiveData
    }

    fun getValidationLiveData(): LiveData<ResourceValidation<ArrayList<FieldObject<UserInformationFields>>>> {
        return validationLiveData
    }


    fun saveUserInformation(userInformationDataModel: UserInformationDataModel, profilePictureFile: MultipartBody.Part?) {
        onServerResponseLiveData.value = Resource.Loading()

        this.viewModelScope.launch {
            val response = userInfoRepository.updateUser(userInformationDataModel, profilePictureFile)
            if(response.data?.success == true){
                MyApplication.applicationContext.setSharedPrefBoolean("userInfoFilled", true)
            }
            onServerResponseLiveData.value = userInfoRepository.updateUser(userInformationDataModel, profilePictureFile)
        }
    }


    fun validateAllFields(allFieldObjects: ArrayList<FieldObject<UserInformationFields>>) {
        var allFieldsValid = true
        for (currentFieldObject in allFieldObjects) {
            when (currentFieldObject.fieldEnum) {
                UserInformationFields.FirstName -> {
                    dataValidator.isValidName(currentFieldObject)
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