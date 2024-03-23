package com.pourosova.data.core.common.extfun

import android.text.InputFilter
import android.text.Spanned
import com.pourosova.data.core.common.constant.AppConstants

fun String.convertToInt() : Int
{
    return try {
        this.toInt()
    }catch (ex : Exception)
    {
        0
    }
}

class MinMaxFilter(minValue: Int, maxValue: Int) : InputFilter {
    private var intMin: Int = 0
    private var intMax: Int = 0

   init {
       this.intMin = minValue
       this.intMax = maxValue
   }

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dStart: Int, dEnd: Int): CharSequence? {
        try {
            val input = Integer.parseInt(dest.toString() + source.toString())
            if (isInRange(intMin, intMax, input)) {
                return null
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return ""
    }

    private fun isInRange(a: Int, b: Int, c: Int): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}