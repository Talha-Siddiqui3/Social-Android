package com.social.social.activities

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.social.social.R
import com.social.social.customViews.CountryPickerDialog
import com.social.social.misc.MyBaseClass
import com.social.social.showKeyboard
import kotlinx.android.synthetic.main.activity_main.*

class AuthenticationActivity : MyBaseClass(), View.OnClickListener {

    var pickerDialog: CountryPickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addCommonViews(root, this)

        userAuthPhone.showKeyboard()
        addCountryPicker()
        userAuthCountryCode.setOnClickListener(this)
        authSelectedCountry.setOnClickListener(this)


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

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.userAuthCountryCode -> {
                pickerDialog?.showDialog()
            }
            R.id.authSelectedCountry -> {
                pickerDialog?.showDialog()
            }
        }
    }
}