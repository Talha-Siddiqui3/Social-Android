package com.social.social.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import com.social.social.DependencyInjectorUtility
import com.social.social.R
import com.social.social.dataObjects.FieldObject
import com.social.social.helper.OtpTextWatcher
import com.social.social.helper.Resource
import com.social.social.helper.ResourceValidation
import com.social.social.misc.ActivityIntentConstants
import com.social.social.misc.MyBaseClass
import com.social.social.showKeyboard
import com.social.social.showToastShort
import com.social.social.viewModels.AuthenticationVerifyViewModel
import kotlinx.android.synthetic.main.activity_authentication_verify.*

enum class AuthenticationVerifyFields {
    EditText1, EditText2, EditText3, EditText4
}

class AuthenticationVerifyActivity : MyBaseClass(), View.OnClickListener {
    private lateinit var listEditTexts: ArrayList<EditText>
    private var phoneNumber: String? = null
    private val authVerifyFieldsToViewsMap = HashMap<AuthenticationVerifyFields, EditText>()

    private val viewModel: AuthenticationVerifyViewModel by viewModels {
        DependencyInjectorUtility.getAuthenticationVerifyViewModelFactory()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication_verify)
        addCommonViews(root, this)
        initFunctions()
        authVerifyOtpEditText1.showKeyboard()

    }

    private fun initFunctions() {
        phoneNumber = intent.getStringExtra(ActivityIntentConstants.userPhoneNumber)
        sentCodeOnNumberTextView.text = sentCodeOnNumberTextView.text.toString() + phoneNumber
        verifyButton.setOnClickListener(this)
        setupOtpFields()
        initAuthenticationFieldsMap()
        addOnValidationCompleteObserver()
        addOnServerResponseObserver()
    }

    private fun addOnServerResponseObserver() {
        viewModel.getOnServerResponseLiveData().observe(this) {
            when (it) {
                is Resource.Success -> {
                    hideLoading()
                    "Logged in".showToastShort(this)
                }
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Error -> {
                    hideLoading()
                    showCustomError(it.message!!)
                    Log.e("error", it.message)
                }
            }
        }
    }

    private fun addOnValidationCompleteObserver() {
        viewModel.getValidationLiveData().observe(this) {
            if (it is ResourceValidation.Success) {
                viewModel.sendVerifyOtpRequest(phoneNumber ?: "", getOtpCode())
            } else {
                // validation failed
                for (fieldObject in it.data) {
                    if (!fieldObject.valid) {
                        authVerifyFieldsToViewsMap[fieldObject.fieldEnum]?.error = fieldObject.error
                    }
                }
            }
        }
    }

    private fun initAuthenticationFieldsMap() {
        authVerifyFieldsToViewsMap[AuthenticationVerifyFields.EditText1] = listEditTexts[0]
        authVerifyFieldsToViewsMap[AuthenticationVerifyFields.EditText2] = listEditTexts[1]
        authVerifyFieldsToViewsMap[AuthenticationVerifyFields.EditText3] = listEditTexts[2]
        authVerifyFieldsToViewsMap[AuthenticationVerifyFields.EditText4] = listEditTexts[3]
    }

    private fun setupOtpFields() {
        listEditTexts = arrayListOf(
            authVerifyOtpEditText1,
            authVerifyOtpEditText2,
            authVerifyOtpEditText3,
            authVerifyOtpEditText4
        )
        authVerifyOtpEditText1.addTextChangedListener(
            OtpTextWatcher(
                authVerifyOtpEditText1,
                listEditTexts
            )
        )
        authVerifyOtpEditText2.addTextChangedListener(
            OtpTextWatcher(
                authVerifyOtpEditText2,
                listEditTexts
            )
        )
        authVerifyOtpEditText3.addTextChangedListener(
            OtpTextWatcher(
                authVerifyOtpEditText3,
                listEditTexts
            )
        )
        authVerifyOtpEditText4.addTextChangedListener(
            OtpTextWatcher(
                authVerifyOtpEditText4,
                listEditTexts
            )
        )
    }

    private fun getAllFieldObjects(): ArrayList<FieldObject<AuthenticationVerifyFields>> {
        val allFieldObjects = ArrayList<FieldObject<AuthenticationVerifyFields>>()
        allFieldObjects.add(
            FieldObject(
                AuthenticationVerifyFields.EditText1,
                listEditTexts[0].text.toString().trim()
            )
        )
        allFieldObjects.add(
            FieldObject(
                AuthenticationVerifyFields.EditText2,
                listEditTexts[1].text.toString().trim()
            )
        )
        allFieldObjects.add(
            FieldObject(
                AuthenticationVerifyFields.EditText3,
                listEditTexts[2].text.toString().trim()
            )
        )
        allFieldObjects.add(
            FieldObject(
                AuthenticationVerifyFields.EditText4,
                listEditTexts[3].text.toString().trim()
            )
        )
        return allFieldObjects
    }

    private fun getOtpCode(): String {
        return listEditTexts[0].text.toString().trim() +
                listEditTexts[1].text[1].toString().trim() +
                listEditTexts[2].text[1].toString().trim() +
                listEditTexts[3].text[1].toString().trim()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.verifyButton -> {
                viewModel.validateAllFields(getAllFieldObjects())
            }
        }
    }

}