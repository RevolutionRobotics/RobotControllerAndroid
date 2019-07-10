package com.revolution.robotics.core.bindings

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.revolution.robotics.core.domain.remote.LocalizedString

@BindingAdapter("backgroundResource")
fun setBackgroundResource(view: View, @DrawableRes background: Int) {
    view.setBackgroundResource(background)
}

@Suppress("DEPRECATION")
@BindingAdapter("formattedText")
fun setFormattedText(textView: TextView, text: LocalizedString?) {
    textView.text = when {
        text?.getLocalizedString() == null -> ""
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> Html.fromHtml(
            text.getLocalizedString(),
            Html.FROM_HTML_MODE_LEGACY
        )
        else -> Html.fromHtml(text.getLocalizedString())
    }
}

@BindingAdapter("localizedFirebaseText")
fun setLocalizedFirebaseText(textView: TextView, localizedString: LocalizedString?) {
    if (localizedString == null) {
        textView.text = ""
    } else {
        textView.text = localizedString.getLocalizedString()
    }
}
