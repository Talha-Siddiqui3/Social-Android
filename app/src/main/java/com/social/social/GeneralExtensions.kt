@file:Suppress("DEPRECATION")

package com.social.social

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

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

fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

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

fun Activity.getSharedPrefBoolean(key: String): Boolean {
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences?.getBoolean(key, false) ?: false
}

fun Activity.setSharedPrefBoolean(key: String, value: Boolean) {
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor? = preferences?.edit()
    editor?.putBoolean(key, value)
    editor?.apply()
}

fun Application.getSharedPrefBoolean(key: String): Boolean {
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences?.getBoolean(key, false) ?: false
}

fun Application.setSharedPrefBoolean(key: String, value: Boolean) {
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor? = preferences?.edit()
    editor?.putBoolean(key, value)
    editor?.apply()
}


fun Activity.getSharedPrefString(key: String): String? {
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences?.getString(key, null)
}

fun Activity.setSharedPrefString(key: String, value: String) {
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor? = preferences?.edit()
    editor?.putString(key, value)
    editor?.apply()
}


fun Application.getSharedPrefString(key: String): String? {
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences?.getString(key, null)
}

fun Application.setSharedPrefString(key: String, value: String) {
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor? = preferences?.edit()
    editor?.putString(key, value)
    editor?.apply()
}

fun createPartFromString(stringData: String?): RequestBody? {
    return stringData?.toRequestBody("text/plain".toMediaTypeOrNull())
}


