package com.revolution.robotics.core.bindings

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter

@BindingAdapter("entries")
fun setEntries(spinner: Spinner, entries: List<String>?) {
    val dataAdapter = ArrayAdapter<String>(
        spinner.context,
        android.R.layout.simple_spinner_item, entries
    )
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = dataAdapter
}