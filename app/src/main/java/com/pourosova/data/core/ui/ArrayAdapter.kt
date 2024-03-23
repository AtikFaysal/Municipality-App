package com.pourosova.data.core.ui

import android.content.Context
import android.widget.ArrayAdapter
import com.pourosova.data.R

/**
 * This could be use for creating ArrayAdapter to generate dropDown item list for any type
 * @author monir
 * */
fun <E> createDropDownAdapter(
    context: Context, dataList: List<E>
): ArrayAdapter<E> {
    return ArrayAdapter(
        context,
        R.layout.item_spninner,
        dataList
    )
}