package com.social.social.activities

import android.os.Bundle
import com.social.social.R
import com.social.social.misc.MyBaseClass
import com.social.social.printLog
import kotlinx.android.synthetic.main.activity_main.*

class AuthenticationActivity : MyBaseClass() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addCommonViews(root, this)
        showLoading()


    }
}