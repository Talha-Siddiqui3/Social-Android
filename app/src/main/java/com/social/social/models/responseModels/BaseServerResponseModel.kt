package com.social.social.models.responseModels

abstract class BaseServerResponseModel {
    abstract val message: String?
    abstract val success: Boolean
    abstract val error: String?
}