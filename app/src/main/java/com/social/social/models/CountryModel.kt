package com.social.social.models

class CountryModel(private val name: String, private val flag: Int, private val code: String) {

    fun getName(): String {
        return name
    }

    fun getFlag(): Int {
        return flag
    }

    fun getCode(): String {
        return code
    }
}