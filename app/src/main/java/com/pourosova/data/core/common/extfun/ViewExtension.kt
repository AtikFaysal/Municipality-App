package com.pourosova.data.core.common.extfun

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import timber.log.Timber

fun EditText.getTextFromEt():String = this.text.toString().trim()
fun AutoCompleteTextView.getTextFromAt():String = this.text.toString().trim()
fun TextView.getTextFromTv():String = this.text.toString().trim()

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Activity.hideKeyboard() {
   try {
       val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
       var view: View ?= this.currentFocus
       if (view == null) {
           view = View(this)
       }
       imm.hideSoftInputFromWindow(view.windowToken, 0)
   }catch (ex : Exception){
       ex.message
   }
}

var lastClickTime: Long = 0
fun View.clickWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun EditText.getTextFromEtAsInteger() : Int{
    val value = this.text.toString().trim()
    val valueInt = try {
        value.toInt()
    }catch (ex : Exception)
    {
        0
    }

    return valueInt
}

fun EditText.getTextFromEtAsDouble() : Double{
    val value = this.text.toString().trim()
    val valueInt = try {
        value.toDouble()
    }catch (ex : Exception)
    {
        0.0
    }

    return valueInt
}

fun ConstraintLayout.disableAllView(isDisable : Boolean){
    Timber.e("isDisable: $isDisable")
    for (i in 0 until this.childCount) {
        val child: View = this.getChildAt(i)
        Timber.e("viewId: ${child.id}")
        child.isEnabled = isDisable
    }
}

infix fun Context.setClipboard(text: String) {
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Copied Text", text)
    clipboard.setPrimaryClip(clip)
}
