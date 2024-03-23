package com.pourosova.data.core.domain.base

import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

class IoValidation @Inject constructor(){

}

fun String.isPhoneNumberValid(): Boolean {
    val pattern: Pattern = Pattern.compile( "((0|01|\\+88|\\+88\\s*\\(0\\)|\\+88\\s*0)\\s*)?1(\\s*[0-9]){9}")
    val matcher: Matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String.isPasswordValid() : Boolean
{
    return this.isNotEmpty() && this.length >= 8
}