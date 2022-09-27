package com.social.social.customViews

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.social.social.R
import com.social.social.makeVisible
import kotlinx.android.synthetic.main.my_alertbox_layout.view.*
import kotlinx.coroutines.launch
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil


interface DoubleButtonListenerCallback {
    fun button1ClickListener()
    fun button2ClickListener()
}

interface SingleButtonListenerCallback {
    fun buttonClickListener()
}

enum class AlertBoxType {
    ALERT, ERROR, SUCCESS
}

class AlertBox(
    context: Context,
    private val blurView: BlurView,
    var alertBoxType: AlertBoxType? = null,
    var leftButtonText: String? = null,
    var rightButtonText: String? = null,
    var centerButtonText: String? = null,
    var alertMessage: String? = null,
    var isLoginDialog: Boolean = false
) : RelativeLayout(context) {
    private var bottomPos = 0f
    private var centerPos = 0f
    private var animator: ObjectAnimator? = null
    private var sizeLoaded = MutableLiveData<Boolean>()
    var doubleButtonListener: DoubleButtonListenerCallback? = null
    var singleButtonListener: SingleButtonListenerCallback? = null
    var availableButtons: ArrayList<Button>
    private var parentWidth: Int? = null

    private var isCurrentlyShown = false

    init {
        sizeLoaded.value = false
        availableButtons = ArrayList()
        initializeView()
        setKeyboardListener()

    }

    private fun setKeyboardListener() {
        KeyboardVisibilityEvent.setEventListener(
            (context as AppCompatActivity),
            object : KeyboardVisibilityEventListener {
                override fun onVisibilityChanged(isOpen: Boolean) {
                    if (isCurrentlyShown) {
                        if (this@AlertBox.y == bottomPos) {
                            return
                        }
                        if (!isOpen) {
                            animatePickerTo(centerPos, 250)
                        }
                    }
                }
            })
    }

    private fun initializeView() {
        View.inflate(context, R.layout.my_alertbox_layout, this)
        this.elevation = 85f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val parentHeight = (this.parent as View).height
        parentWidth = (this.parent as View).width
        bottomPos = parentHeight.toFloat()// - (this.height)
        centerPos = (parentHeight / 2).toFloat() - (this.height / 2)
        if (!isCurrentlyShown) {
            this.visibility = View.GONE
        } else {
            // Will always be at center is already shown
            if (!animator?.isRunning!!) {
                animatePickerTo(centerPos, 250)
            } else {
                //this.visibility = View.GONE
                animator?.end()
                this.y = bottomPos
                animatePickerTo(centerPos, 250)
            }
        }
//        this.visibility = View.GONE
        if (sizeLoaded.value == false) {
            sizeLoaded.value = true
        }
    }

    /* private fun updateSize() {
         val parentHeight = (this.parent as View).height
         centerPos = (parentHeight / 2).toFloat() - (this.measuredHeight / 2)
     }*/

    private fun animatePickerTo(y: Float, duration: Int) {
        animator = ObjectAnimator.ofFloat(this, "Y", y)
        animator?.duration = duration.toLong()
        animator?.start()
    }

    fun showDialog() {
        (context as AppCompatActivity).lifecycleScope.launch {
            if (sizeLoaded.value == true) {
                showLoadedView()
            } else {
                sizeLoaded.observe(context as AppCompatActivity) {
                    if (sizeLoaded.value == true) {
                        showLoadedView()
                    }
                }
            }
        }
    }

    private fun showLoadedView() {
        UIUtil.hideKeyboard(context as Activity)
        Log.e("showLoadedView", "True")
        if (!isCurrentlyShown) {
            Handler(Looper.getMainLooper()).postDelayed({
                if (alertBoxType != null) {
                    when (alertBoxType) {
                        AlertBoxType.ALERT -> {
                            Glide.with(context).load(R.drawable.alert)
                                .into(alertBoxImageView)
//                            alertBoxTitle.makeVisible()
                            alertBoxMessage.setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.primaryTextColor
                                )
                            )
                            alertBoxMessage.text =
                                alertMessage
                                    ?: context.resources.getString(R.string.alertMessage)
                            alertBoxTitle.text = context.resources.getString(R.string.alert)

                            doubleTypeDialog(text1 = leftButtonText, text2 = rightButtonText)
                        }
                        AlertBoxType.ERROR -> {
                            singleTypeDialog(centerButtonText)
                            alertBoxTitle.text = context.resources.getString(R.string.error)
                            alertBoxMessage.text =
                                alertMessage ?: context.resources.getString(R.string.errorMessage)
                            Glide.with(context).load(R.drawable.error)
                                .into(alertBoxImageView)
                        }
                        AlertBoxType.SUCCESS -> {
                            singleTypeDialog(text1 = centerButtonText)
                            alertBoxTitle.text = context.resources.getString(R.string.success)
                            alertBoxMessage.text =
                                alertMessage ?: context.resources.getString(R.string.successMessage)
                            Glide.with(context).load(R.drawable.success)
                                .into(alertBoxImageView)
                        }
                        else -> {}
                    }
                    //updateSize()
                    blurView.makeVisible()
                    this.y = bottomPos
                    this.visibility = View.VISIBLE
                    animator = ObjectAnimator.ofFloat(this, "Y", centerPos)
                    animator?.duration = 400
                    animator?.start()
                    isCurrentlyShown = true
                }
            }, 200)
        }
    }

    private fun doubleTypeDialog(
        text1: String?,
        text2: String?,
        isFeedbackDialog: Boolean = false
    ) {
        removeAllButtons()
        val text1Actual = text1 ?: "Yes"
        val text2Actual = text2 ?: "No"
        val button1 = Button(context)
        val button2 = Button(context)
        button1.layoutParams = getButtonParams(
            button1,
            text1Actual,
            false,
            ALIGN_PARENT_START
        )
        button2.layoutParams = getButtonParams(
            button2,
            text2Actual,
            false,
            ALIGN_PARENT_END
        )
        val params = alertBoxRootLayout.layoutParams as LayoutParams
        params.height = resources.getDimension(com.intuit.sdp.R.dimen._240sdp).toInt()
        (button1.layoutParams as LayoutParams).addRule(BELOW, R.id.alertBoxMessage)
        (button2.layoutParams as LayoutParams).addRule(BELOW, R.id.alertBoxMessage)
        alertBoxRootLayout.layoutParams = params
        dialog_layout.addView(button1)
        dialog_layout.addView(button2)
        availableButtons.add(button1)
        availableButtons.add(button2)
        button1.setOnClickListener {
            doubleButtonListener?.button1ClickListener()
        }

        button2.setOnClickListener {
            doubleButtonListener?.button2ClickListener()
        }
    }

    private fun removeAllButtons() {
        availableButtons.forEach {
            dialog_layout.removeView(it)
        }
    }

    private fun singleTypeDialog(text1: String?) {
        removeAllButtons()
        val actualText1 = text1 ?: "Continue"
        val button = Button(context)
        button.layoutParams = getButtonParams(
            button,
            actualText1,
            true,
            CENTER_HORIZONTAL
        )
        (button.layoutParams as LayoutParams).addRule(BELOW, R.id.alertBoxMessage)
        dialog_layout.addView(button)
        availableButtons.add(button)
        button.setOnClickListener {
            singleButtonListener?.buttonClickListener()
        }
    }

    private fun getButtonParams(
        button: Button,
        text: String?,
        isSingle: Boolean,
        align: Int,
    ): LayoutParams {
        button.setBackgroundResource(R.drawable.default_button_selector)
        button.text = text
        button.typeface = ResourcesCompat.getFont(context, R.font.mulish_regular)
        button.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            context.resources.getDimension(com.intuit.ssp.R.dimen._10ssp)
        )
        button.setTextColor(ContextCompat.getColor(context, R.color.primaryTextColor))
        val buttonHeight = resources.getDimension(com.intuit.sdp.R.dimen._30sdp).toInt()
        val topMargin = resources.getDimension(com.intuit.sdp.R.dimen._15sdp).toInt()
        val params: LayoutParams?
        if (isSingle) {
            // for single button width should be maximum
            params =
                LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    buttonHeight
                )
            params.setMargins(
                context.resources.getDimension(com.intuit.sdp.R.dimen._35sdp).toInt(),
                topMargin,
                context.resources.getDimension(com.intuit.sdp.R.dimen._35sdp).toInt(),
                0
            )
        } else {
            // for two buttons width should be minimum
            params =
                LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    buttonHeight
                )
            params.width = (35 * parentWidth!! / 100)
            params.setMargins(
                0,
                topMargin,
                0,
                0
            )
        }
//        params.addRule(ALIGN_PARENT_BOTTOM)
        params.addRule(align)
        return params
    }

    fun setOnClickListener(listenerCallback: SingleButtonListenerCallback) {
        this.singleButtonListener = listenerCallback
    }

    fun setOnClickListener(listenerCallback: DoubleButtonListenerCallback) {
        this.doubleButtonListener = listenerCallback
    }

    fun hideAlertBox(hideBlurView: Boolean = true) {
        if (isCurrentlyShown) {

            animator = ObjectAnimator.ofFloat(this, "Y", bottomPos)
            animator?.duration = 400
            animator?.start()
            animator?.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator) {

                }

                override fun onAnimationEnd(animation: Animator) {
                    this@AlertBox.visibility = View.GONE
                    isCurrentlyShown = false
                    if (hideBlurView) {
                        blurView.visibility = View.INVISIBLE
                    }

                }

                override fun onAnimationCancel(animation: Animator) {

                }

                override fun onAnimationStart(animation: Animator) {


                }

            })
        }
    }
}