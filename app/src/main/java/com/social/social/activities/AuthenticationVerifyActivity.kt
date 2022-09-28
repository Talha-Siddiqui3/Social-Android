package com.social.social.activities

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.social.social.R
import com.social.social.helper.OtpTextWatcher
import com.social.social.showKeyboard
import kotlinx.android.synthetic.main.activity_authentication_verify.*

enum class AuthenticationVerifyFields {
    EditText1, EditText2, EditText3, EditText4, EditText5, EditText6
}

class AuthenticationVerifyActivity : AppCompatActivity() {
    private lateinit var listEditTexts: ArrayList<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication_verify)
        authVerifyOtpEditText1.showKeyboard()
        setupOtpFields()
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
}