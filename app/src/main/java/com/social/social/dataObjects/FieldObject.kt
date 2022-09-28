package com.social.social.dataObjects

class FieldObject<T : Enum<T>>(val fieldEnum: Enum<T>, val value: String) {
    var valid = false
    var error = ""
}