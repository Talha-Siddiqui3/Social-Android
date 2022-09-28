package com.social.social.helper

sealed class ResourceValidation<T>(val data: T) {
    class Success<T>(data: T) : ResourceValidation<T>(data)
    class Error<T>(data: T) : ResourceValidation<T>(data)
}
