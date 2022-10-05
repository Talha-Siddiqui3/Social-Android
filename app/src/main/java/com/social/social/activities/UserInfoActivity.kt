package com.social.social.activities

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import com.social.social.DependencyInjectorUtility
import com.social.social.R
import com.social.social.dataObjects.FieldObject
import com.social.social.helper.ResourceValidation
import com.social.social.misc.MyBaseClass
import com.social.social.scrollToBottom
import com.social.social.viewModels.UserInfoViewModel
import kotlinx.android.synthetic.main.activity_authentication.*
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.activity_user_info.root
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

enum class UserInformationFields {
    FirstName
}

class UserInfoActivity : MyBaseClass(), View.OnClickListener {

    private val viewModel: UserInfoViewModel by viewModels {
        DependencyInjectorUtility.getUserInfoViewModelFactory()
    }

    private val userInfoFieldsToViewsMap = HashMap<UserInformationFields, EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        addCommonViews(root, this)
        setKeyboardListener()
        setOnClickListeners()
        initUserInfoFieldsMap()
        addOnValidationCompleteObserver()
    }

    private fun initUserInfoFieldsMap() {
        userInfoFieldsToViewsMap[UserInformationFields.FirstName] = firstNameEditText
    }

    private fun setOnClickListeners() {
        saveButton.setOnClickListener(this)
        addImageButton.setOnClickListener(this)
    }

    private fun setKeyboardListener() {
        KeyboardVisibilityEvent.setEventListener(
            this,
            object : KeyboardVisibilityEventListener {
                override fun onVisibilityChanged(isOpen: Boolean) {
                    if (isOpen) {
                        rootScrollView.scrollToBottom()
                    }
                }
            })
    }

    private fun addOnValidationCompleteObserver() {
        viewModel.getValidationLiveData().observe(this) {
            if (it is ResourceValidation.Success) {
                //viewModel.loginUsingPhone(getPhoneNumber())
            } else {
                //Validation was not successful due to at least one field having incorrect data
                //We will now display errors on all the fields having incorrect data
                for (fieldObject in it.data) {
                    if (!fieldObject.valid) {
                        userInfoFieldsToViewsMap[fieldObject.fieldEnum]?.error =
                            fieldObject.error //using our hashmap to get our view from the enum
                    }
                }
            }
        }
    }

    private fun getAllFieldObjects(): ArrayList<FieldObject<UserInformationFields>> {
        val allFieldObjects = ArrayList<FieldObject<UserInformationFields>>()
        allFieldObjects.add(
            FieldObject(
                UserInformationFields.FirstName,
                firstNameEditText.text.toString()
            )
        )
        return allFieldObjects
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.addImageButton -> {

            }
            R.id.saveButton -> {
                viewModel.validateAllFields(getAllFieldObjects())
            }
        }
    }
}