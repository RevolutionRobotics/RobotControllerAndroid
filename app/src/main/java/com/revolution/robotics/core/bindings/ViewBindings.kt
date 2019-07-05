package com.revolution.robotics.core.bindings

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("backgroundResource")
fun setBackgroundResource(view: View, @DrawableRes background: Int) {
    view.setBackgroundResource(background)
}

@BindingAdapter("formattedText")
fun setFormattedText(textView: TextView, text: String?) {
    textView.text = when {
        text == null -> ""
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        else -> Html.fromHtml(text)
    }
}
