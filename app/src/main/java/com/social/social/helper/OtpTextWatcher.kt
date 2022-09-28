package com.social.social.helper

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.social.social.R


class OtpTextWatcher(private val view: EditText, private val editTexts: ArrayList<EditText>) :
    TextWatcher {

    var beforeText = ""

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeText = s.toString()
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        val text = s.toString()
        when (view.id) {
            R.id.authVerifyOtpEditText1 -> {
                if (text.length == 1) {
                    editTexts[1].requestFocus()
                    editTexts[1].setText("\u200B")
                    editTexts[1].setSelection(editTexts[1].text.length)
                }
            }
            R.id.authVerifyOtpEditText2 -> {
                if (text.length == 2 && text != "\u200B") {
                    editTexts[2].requestFocus()
                    editTexts[2].setText("\u200B")
                    editTexts[2].setSelection(editTexts[2].text.length)
                } else if (beforeText.length > text.length) {
                    editTexts[0].requestFocus()
                    editTexts[0].setSelection(editTexts[0].text.length)
                    editTexts[1].setText("\u200B")
                }
            }
            R.id.authVerifyOtpEditText3 -> {
                if (text.length == 2 && text != "\u200B") {
                    editTexts[3].requestFocus()
                    editTexts[3].setText("\u200B")
                    editTexts[3].setSelection(editTexts[3].text.length)
                } else if (beforeText.length > text.length) {
                    editTexts[1].requestFocus()
                    editTexts[1].setSelection(editTexts[1].text.length)
                    editTexts[2].setText("\u200B")
                }
            }
            R.id.authVerifyOtpEditText4 -> {
               if (beforeText.length > text.length) {
                    editTexts[2].requestFocus()
                    editTexts[2].setSelection(editTexts[2].text.length)
                    editTexts[3].setText("\u200B")
                }
            }
        }
    }
}