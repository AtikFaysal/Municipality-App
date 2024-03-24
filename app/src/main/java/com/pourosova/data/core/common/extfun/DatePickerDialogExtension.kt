package com.pourosova.data.core.common.extfun

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.fragment.app.FragmentManager
import com.pourosova.data.core.common.dateparser.DateTimeFormat
import com.pourosova.data.core.common.dateparser.DateTimeParser
import com.iamkamrul.dateced.dateCed
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog as CustomDatePicker

fun Activity.showDatePickerDialog( callback:(pickDate:String)->Unit){
    val calendar = Calendar.getInstance()
    val day = calendar[Calendar.DAY_OF_MONTH]
    val month = calendar[Calendar.MONTH]
    val year = calendar[Calendar.YEAR]
    calendar.set(year,month,day)
    val datePicker = DatePickerDialog(this, { _, selectedYear, monthOfYear, dayOfMonth ->
            callback.invoke("$selectedYear-${String.format("%02d", monthOfYear+1)}-${String.format("%02d", dayOfMonth)}")
        }, calendar.get(Calendar.YEAR),  calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

    datePicker.datePicker.maxDate = calendar.timeInMillis
    datePicker.show()
}

fun FragmentManager.showCustomDatePicker(
    blockedDates: List<String>,
    callback: (pickDate: String) -> Unit
){
    val calendar = Calendar.getInstance()
    val day = calendar[Calendar.DAY_OF_MONTH]
    val month = calendar[Calendar.MONTH]
    val year = calendar[Calendar.YEAR]
    calendar.set(year,month,day)

    val datePicker = CustomDatePicker.newInstance(
        { _, selectedYear, monthOfYear, dayOfMonth ->
            callback.invoke(
                "$selectedYear-${
                    String.format(
                        "%02d",
                        monthOfYear + 1
                    )
                }-${String.format("%02d", dayOfMonth)}"
            )
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    datePicker.minDate = Calendar.getInstance()
    val calendars : Array<Calendar> = blockedDates.map {
        getCalender(it)
    }.toTypedArray().sortedArray()
    datePicker.disabledDays = calendars
    datePicker.show(this, "")
}

private fun getCalender(date : String) : Calendar{
    val cal = Calendar.getInstance()
    cal.time = date.dateCed().toDate()
    return cal
}

fun Activity.showDatePickerDialog(maxDate : Int, minDate : Int, callback:(pickDate:String)->Unit){
    val calendar = Calendar.getInstance()
    val day = calendar[Calendar.DAY_OF_MONTH]
    val month = calendar[Calendar.MONTH]
    val year = calendar[Calendar.YEAR]
    calendar.set(year,month,day)
    val datePicker = DatePickerDialog(this, { _, selectedYear, monthOfYear, dayOfMonth ->
        callback.invoke("$selectedYear-${String.format("%02d", monthOfYear+1)}-${String.format("%02d", dayOfMonth)}")
    }, calendar.get(Calendar.YEAR),  calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    datePicker.datePicker.maxDate = DateTimeParser.addLimitToDate(maxDate)
    datePicker.datePicker.minDate = DateTimeParser.addLimitToDate(minDate * -1)
    datePicker.show()
}

fun Activity.showTimePickerDialog(callback:(pickTime:String)->Unit){
    val cal = Calendar.getInstance()
    val timePicker = TimePickerDialog(this, { _, hour, minute ->
        val h = if(hour < 10) "0$hour" else hour
        val m = if(minute < 10) "0$minute" else minute
        callback.invoke("$h:$m:00")
    }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false)
    timePicker.show()
}

fun getNextOrPreviousDate(day : Int) : String {
    val calender = Calendar.getInstance()
    calender.add(Calendar.DAY_OF_MONTH, day)
    val df = SimpleDateFormat(DateTimeFormat.sqlYMD, Locale.US)
    return df.format(calender.time)
}
