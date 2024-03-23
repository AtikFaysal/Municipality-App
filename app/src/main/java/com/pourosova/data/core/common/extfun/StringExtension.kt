package com.pourosova.data.core.common.extfun

import android.util.Base64

fun String.decode(): String =
    Base64.decode(this.replace(" ","/"), Base64.DEFAULT).toString(charset("UTF-8"))


fun String.encode(): String =
    Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.DEFAULT).replace("/"," ")

fun List<String>.convertSeatNoToDisplayFormat(): String {
    val displayFormat = StringBuilder()
    this.forEachIndexed { index, s ->
        if (index == (this.size - 1))
            displayFormat.append("[${s}]")
        else displayFormat.append("[${s}],")
    }

    return displayFormat.toString()
}

fun List<Int>.farePerSeat(): String {
    val displayFormat = StringBuilder()
    val uniqueItem = this.distinctBy { it }
    uniqueItem.forEachIndexed { index, s ->
        if (index == (uniqueItem.size - 1))
            displayFormat.append(s)
        else displayFormat.append("$s/")
    }

    return displayFormat.toString()
}

