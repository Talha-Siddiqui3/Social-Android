package com.social.social.customViews

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.social.social.R


/*Created by Talha Siddiqui on 19/07/2020.
 Copyright (c) 2020 Rove. All rights reserved.
*/


class BlurView : RelativeLayout {

    // var onDismissCallback: (() -> Unit?)? = null
    val onDismissCallbacks: ArrayList<(() -> Unit?)?> = ArrayList()

    constructor(context: Context, color: Int? = Color.BLACK, dismissable: Boolean = false) : super(
        context
    ) {
        init(color, dismissable)
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(null, false)
    }


    /*  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
          super.onSizeChanged(w, h, oldw, oldh)
          val parentHeight = (parent as View).height
          val parentWidth = (parent as View).width
         this.layoutParams= LayoutParams(parentWidth, parentHeight)

      }*/

    private fun init(color: Int?, dismissable: Boolean) {
        View.inflate(context, R.layout.blur_view_layout, this)
        elevation = 50f
        setBackgroundColor(color!!)
        background.alpha = 214
        isFocusable = true
        isClickable = true

        if (dismissable) {
            this.setOnClickListener {
                for (callback in onDismissCallbacks) {
                    callback?.invoke()
                }
            }
        }

    }


    fun setOpacity(blurOpacity: Int) {
        background.alpha = blurOpacity
    }


}
