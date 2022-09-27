package com.social.social.misc

import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.airbnb.lottie.LottieAnimationView
import com.social.social.R
import com.social.social.customViews.*
import com.social.social.makeGone
import com.social.social.makeVisible
import com.social.social.customViews.AlertBox


/*Created by Talha Siddiqui on 21/07/2020.
 Copyright (c) 2020 Rove. All rights reserved.
*/


abstract class MyBaseClass : AppCompatActivity() {
    var rootLayout: ViewGroup? = null

    // private var rootCl: ConstraintLayout? = null
    private lateinit var context: Activity
    private var pb: ProgressBar? = null
    var blurViewDismissible: BlurView? = null

    private var alertBox: AlertBox? = null
    private var loadingView: View? = null
    private var animation: LottieAnimationView? = null

    fun addCommonViews(
        rootLayout: ViewGroup?,
        context: Activity,
        blurViewColor: Int = Color.BLACK
    ) {
        this.rootLayout = rootLayout
        this.context = context
        addBlurViewBlack(blurViewColor)
        addProgressBar()
        addLoadingView()
        addAlertBox()
    }

    fun showBlurView() {
        blurViewDismissible?.visibility = View.VISIBLE
    }

    fun hideBlurView() {
        blurViewDismissible?.visibility = View.GONE
    }

    private fun addLoadingView() {
        loadingView = LayoutInflater.from(context).inflate(R.layout.loading_layout, null)
        addViewWrapContent(loadingView!!)
        loadingView?.elevation = 52f
        animation = loadingView?.findViewById(R.id.lottieAnimation)
        hideLoading()
    }


    private fun addBlurViewBlack(blurViewColor: Int) {
        blurViewDismissible = BlurView(this, blurViewColor, dismissable = true)
        addViewMatchParent(blurViewDismissible!!)
    }


    private fun addAlertBox() {
        alertBox = AlertBox(this, blurViewDismissible!!)
        rootLayout?.addView(alertBox)
    }


    private fun addViewMatchParent(view: View) {
        view.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        rootLayout!!.addView(view)
        view.visibility = View.GONE
    }

    private fun addViewWrapContent(view: View) {
        view.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        val layoutParams =
            view?.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.bottomToBottom = ConstraintSet.PARENT_ID;
        layoutParams.endToEnd = ConstraintSet.PARENT_ID;
        layoutParams.startToStart = ConstraintSet.PARENT_ID;
        layoutParams.topToTop = ConstraintSet.PARENT_ID;
        rootLayout!!.addView(view)
        view.visibility = View.GONE
    }

    private fun addProgressBar() {
        pb = ProgressBar(context)
        if (rootLayout != null) {
            rootLayout?.addView(pb)
            val layoutParams =
                pb?.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.width = resources.getDimension(com.intuit.sdp.R.dimen._40sdp).toInt()
            layoutParams.height = resources.getDimension(com.intuit.sdp.R.dimen._40sdp).toInt()
            layoutParams.bottomToBottom = ConstraintSet.PARENT_ID;
            layoutParams.endToEnd = ConstraintSet.PARENT_ID;
            layoutParams.startToStart = ConstraintSet.PARENT_ID;
            layoutParams.topToTop = ConstraintSet.PARENT_ID;
            pb?.layoutParams = layoutParams
        }
        pb?.elevation = 220f
        hideProgressBar()
    }

    fun showLoading(blockUI: Boolean = true) {
        showBlurView()
        loadingView?.makeVisible()
        animation?.playAnimation()
        if (blockUI) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
    }

    fun hideLoading(hideBlurView: Boolean = true) {
        hideBlurView
        loadingView?.makeGone()
        animation?.cancelAnimation()
        if (hideBlurView) {
            blurViewDismissible?.visibility = View.INVISIBLE
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun showProgressBar(blockUI: Boolean = true) {
        pb?.visibility = View.VISIBLE
        blurViewDismissible?.visibility = View.VISIBLE
        if (blockUI) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
    }


    fun hideProgressBar(hideBlurView: Boolean = true) {
        pb?.visibility = View.INVISIBLE
        if (hideBlurView) {
            blurViewDismissible?.visibility = View.INVISIBLE
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun showError(error: String?, customError: Boolean? = false) {
        if (error == null) {
            showUnknownError()
        } else {
            if (customError == true) {
                showCustomError(error)
            } else {
                when (error) {
                    "network" -> {
                        showNetworkError()
                    }
                    "unknown" -> {
                        showUnknownError()
                    }
                    "incorrectCredentials" -> {
                        showCustomError(ErrorsAndMessages.incorrectCredentialsError)
                    }
                    "incorrectOtp" -> {
                        showCustomError(ErrorsAndMessages.invalidCode)
                    }
                    else -> {
                        showUnknownError()
                    }
                }
            }

        }

    }


    fun showNetworkError() {
        alertBox?.isLoginDialog = false
        Log.e("showNetworkError", context.localClassName)
        alertBox?.alertBoxType = AlertBoxType.ERROR
        alertBox?.alertMessage = ErrorsAndMessages.networkError
        alertBox?.singleButtonListener = object : SingleButtonListenerCallback {
            override fun buttonClickListener() {
                alertBox?.hideAlertBox()
            }
        }
        alertBox?.showDialog()
    }

    fun showUnknownError() {
        alertBox?.isLoginDialog = false
        alertBox?.alertBoxType = AlertBoxType.ERROR
        alertBox?.alertMessage = ErrorsAndMessages.unknownError
        alertBox?.singleButtonListener = object : SingleButtonListenerCallback {
            override fun buttonClickListener() {
                alertBox?.hideAlertBox()
            }
        }
        alertBox?.showDialog()
    }

    fun showCustomError(customError: String) {
        //hideLoadingView()
        hideProgressBar()
        alertBox?.isLoginDialog = false
        alertBox?.alertBoxType = AlertBoxType.ERROR
        alertBox?.alertMessage = customError
        alertBox?.singleButtonListener = object : SingleButtonListenerCallback {
            override fun buttonClickListener() {
                alertBox?.hideAlertBox()
            }
        }
        alertBox?.showDialog()
    }

    fun showSuccessAlert() {
        //hideLoadingView()
        hideProgressBar()
        alertBox?.isLoginDialog = false
        alertBox?.alertBoxType = AlertBoxType.SUCCESS
        alertBox?.alertMessage = null //As default is already success message
        alertBox?.singleButtonListener = object : SingleButtonListenerCallback {
            override fun buttonClickListener() {
                alertBox?.hideAlertBox()
            }
        }
        alertBox?.showDialog()
    }


    fun showCustomAlert(
        message: String,
        alertBoxType: AlertBoxType,
        buttonText: String? = null,
        leftButtonText: String? = null,
        rightButtonText: String? = null
    ) {
        //hideLoadingView()
        hideProgressBar()
        alertBox?.isLoginDialog = false
        alertBox?.alertBoxType = alertBoxType
        alertBox?.alertMessage = message
        alertBox?.centerButtonText = buttonText
        alertBox?.leftButtonText = leftButtonText
        alertBox?.rightButtonText = rightButtonText
        alertBox?.singleButtonListener = object : SingleButtonListenerCallback {
            override fun buttonClickListener() {
                alertBox?.hideAlertBox()
            }
        }
        alertBox?.showDialog()
    }


    fun showCustomConfirmation(
        message: String,
        leftButtonText: String? = null,
        rightButtonText: String? = null,
        isLoginDialog: Boolean = false,
        alertBoxButtonListener: DoubleButtonListenerCallback? = null
    ) {
        Log.e("insideShowCustom", "true")
        alertBox?.isLoginDialog = isLoginDialog
        alertBox?.alertBoxType = AlertBoxType.ALERT
        alertBox?.alertMessage = message
        alertBox?.rightButtonText = rightButtonText
        alertBox?.leftButtonText = leftButtonText
        alertBox?.doubleButtonListener = object : DoubleButtonListenerCallback {
            override fun button1ClickListener() {
                alertBoxButtonListener?.button1ClickListener()
            }

            override fun button2ClickListener() {
                alertBoxButtonListener?.button2ClickListener()
            }
        }
        alertBox?.showDialog()
    }

    fun hideAlertBox(hideBlurView: Boolean = true) {
        alertBox?.hideAlertBox(hideBlurView)
    }

}


