package com.social.social.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.social.social.DependencyInjectorUtility
import com.social.social.R
import com.social.social.customViews.CountryPickerDialog
import com.social.social.dataObjects.FieldObject
import com.social.social.helper.Resource
import com.social.social.helper.ResourceValidation
import com.social.social.misc.ActivityIntentConstants
import com.social.social.misc.MyBaseClass
import com.social.social.printLog
import com.social.social.showKeyboard
import com.social.social.viewModels.AuthenticationViewModel
import kotlinx.android.synthetic.main.activity_authentication.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil


enum class AuthenticationFields {
    PhoneNumber
}

class AuthenticationActivity : MyBaseClass(), View.OnClickListener {

    var pickerDialog: CountryPickerDialog? = null
    private val authenticationFieldsToViewsMap = HashMap<AuthenticationFields, EditText>()
    private val viewModel: AuthenticationViewModel by viewModels {
        DependencyInjectorUtility.getAuthenticationViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        addCommonViews(root, this)

        userAuthPhone.showKeyboard()
        addCountryPicker()
        setOnClickListeners()
        initAuthenticationFieldsMap()
        addOnValidationCompleteObserver()
        addOnServerResponseObserver()


    }

    private fun initAuthenticationFieldsMap() {
        authenticationFieldsToViewsMap[AuthenticationFields.PhoneNumber] = userAuthPhone
    }

    private fun addOnValidationCompleteObserver() {
        viewModel.getValidationLiveData().observe(this) {
            if (it is ResourceValidation.Success) {
                viewModel.loginUsingPhone(getPhoneNumber())
            } else {
                //Validation was not successful due to at least one field having incorrect data
                //We will now display errors on all the fields having incorrect data
                for (fieldObject in it.data) {
                    if (!fieldObject.valid) {
                        authenticationFieldsToViewsMap[fieldObject.fieldEnum]?.error =
                            fieldObject.error //using our hashmap to get our view from the enum
                    }
                }
            }
        }
    }

    private fun addOnServerResponseObserver() {
        viewModel.getOnServerResponseLiveData().observe(this) {
            when (it) {
                is Resource.Success -> {
                    hideLoading()
                    startActivity(Intent(this, AuthenticationVerifyActivity::class.java).apply {
                        putExtra(ActivityIntentConstants.userPhoneNumber, getPhoneNumber())
                    })
                }
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Error -> {
                    hideLoading()
                    showCustomError(it.message!!)
                    "error".printLog(it.message)
                }
            }
        }
    }

    private fun setOnClickListeners() {
        userAuthCountryCode.setOnClickListener(this)
        authSelectedCountry.setOnClickListener(this)
        sendCodeButton.setOnClickListener(this)
    }

    private fun addCountryPicker() {
        root.post {
            pickerDialog =
                CountryPickerDialog(this@AuthenticationActivity, blurViewDismissible!!)
            root.addView(pickerDialog)
        }
    }

    fun updateFlag(flag: Int) {
        Glide.with(this).load(flag).into(authSelectedCountry)
    }

    private fun getPhoneNumber(): String {
        return userAuthCountryCode.text.toString().trim() + userAuthPhone.text.toString().trim()
    }

    private fun getAllFieldObjects(): ArrayList<FieldObject<AuthenticationFields>> {
        val allFieldObjects = ArrayList<FieldObject<AuthenticationFields>>()
        allFieldObjects.add(FieldObject(AuthenticationFields.PhoneNumber, getPhoneNumber()))
        return allFieldObjects
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.userAuthCountryCode -> {
                pickerDialog?.showDialog()
            }
            R.id.authSelectedCountry -> {
                pickerDialog?.showDialog()
            }
            R.id.sendCodeButton -> {
                UIUtil.hideKeyboard(this)
                viewModel.funValidateAllFields(getAllFieldObjects())
            }
        }
    }
}

