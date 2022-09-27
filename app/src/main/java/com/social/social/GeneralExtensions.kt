@file:Suppress("DEPRECATION")

package com.social.social

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ScrollView
import android.widget.Toast

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun String.showToastShort(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.showToastLong(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}

fun String.printLog(text: Any?) {
    if (BuildConfig.DEBUG) {
        Log.e(this, if (text !is String) text.toString() else text)
    }
}

fun String.printProdLog(text: Any?) {
    Log.e(this, if (text !is String) text.toString() else text)
}

fun Activity.printLog(text: Any?) {
    if (BuildConfig.DEBUG) {
        Log.e(this::class.java.simpleName, if (text !is String) text.toString() else text)
    }
}

//fun Activity.changeStatusBarColor(isLight: Boolean = true) {
//    if (isLight) {
//        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
//            Configuration.UI_MODE_NIGHT_NO -> {
//                Log.e("UI_MODE_NIGHT_NO", "Light")
//                window.decorView.systemUiVisibility =
//                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//            }
//            Configuration.UI_MODE_NIGHT_YES -> {
//                Log.e("UI_MODE_NIGHT_YES", "Dark")
//                window.decorView.systemUiVisibility =
//                    window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
//            }
//        }
//        window.statusBarColor = ContextCompat.getColor(
//            this,
//            R.color.backgroundColor
//        ) // set status background white
//    } else {
//        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
//        val decorView: View = window.decorView //set status background black
//        decorView.systemUiVisibility =
//            decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() //set status text  light
//    }
//}

fun Activity.pxToDp(px: Float): Float {
    return px / this.resources.displayMetrics.density
}

fun Activity.dpToPx(dp: Int): Float {
    return dp * this.resources.displayMetrics.density
}

fun Activity.sdpToPx(sdp: Float): Float {
    return sdp * this.resources.displayMetrics.density
}

fun ScrollView.scrollToBottom() {
    val lastChild = getChildAt(childCount - 1)
    val bottom = lastChild.bottom + paddingBottom
    val delta = bottom - (scrollY + height)
    smoothScrollTo(0, delta)
//    smoothScrollBy(0, delta)
}