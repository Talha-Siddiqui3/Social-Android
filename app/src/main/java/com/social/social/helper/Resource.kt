package com.social.social.helper

sealed class Resource<T>(val data: T? = null, val message: String? = null,val customMessage:Boolean?=false) {

    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null,customMessage:Boolean?=false) : Resource<T>(data, message,customMessage)
}
