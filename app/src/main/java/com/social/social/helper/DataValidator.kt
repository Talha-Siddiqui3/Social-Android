package com.social.social.helper


import com.social.social.activities.AuthenticationVerifyFields
import com.social.social.dataObjects.FieldObject
import com.social.social.misc.ErrorsAndMessages
import java.util.regex.Pattern

class DataValidator<T : Enum<T>> {

    // Don't need it for now, but we might use it later
    fun isValidEmail(fieldObject: FieldObject<T>) {
        fieldObject.valid =
            android.util.Patterns.EMAIL_ADDRESS.matcher(fieldObject.value).matches()
        if (!fieldObject.valid) fieldObject.error = ErrorsAndMessages.noEmailError
    }

    fun isValidName(fieldObject: FieldObject<T>) {
        fieldObject.valid = fieldObject.value.isNotEmpty()
        if (!fieldObject.valid) fieldObject.error = ErrorsAndMessages.noNameError
    }

    //We can add different validations for different countries as well later
    fun isValidMobileNumber(fieldObject: FieldObject<T>) {
        fieldObject.valid =
            (!Pattern.matches("[a-zA-Z]+", fieldObject.value)) && fieldObject.value.length in 7..13

        if (!fieldObject.valid) fieldObject.error = ErrorsAndMessages.noPhoneNumberError
    }

    fun isOtpValid(fieldObject: FieldObject<T>) {
        if (fieldObject.fieldEnum == AuthenticationVerifyFields.EditText1) {
            fieldObject.valid = (Pattern.matches("[0-9]", fieldObject.value))
        } else {
            fieldObject.valid =
                (!Pattern.matches("[a-zA-Z]+", fieldObject.value)) && fieldObject.value.length == 2
        }
        if (!fieldObject.valid) fieldObject.error = ErrorsAndMessages.noOtpError

    }

}