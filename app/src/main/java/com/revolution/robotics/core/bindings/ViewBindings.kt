package com.revolution.robotics.core.bindings

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.revolution.robotics.RoboticsApplication.Companion.kodein
import com.revolution.robotics.core.domain.remote.LocalizedString
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import org.kodein.di.erased.instance

@BindingAdapter("backgroundResource")
fun setBackgroundResource(view: View, @DrawableRes background: Int) {
    view.setBackgroundResource(background)
}

@Suppress("DEPRECATION")
@BindingAdapter("formattedText")
fun setFormattedText(textView: TextView, text: LocalizedString?) {
    val resourceResolver: ResourceResolver by kodein.instance()
    textView.text = when {
        text?.getLocalizedString(resourceResolver) == null -> ""
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> Html.fromHtml(
            text.getLocalizedString(resourceResolver),
            Html.FROM_HTML_MODE_LEGACY
        )
        else -> Html.fromHtml(text.getLocalizedString(resourceResolver))
    }
}

@BindingAdapter("localizedFirebaseText")
fun setLocalizedFirebaseText(textView: TextView, localizedString: LocalizedString?) {
    val resourceResolver: ResourceResolver by kodein.instance()
    if (localizedString == null) {
        textView.text = ""
    } else {
        textView.text = localizedString.getLocalizedString(resourceResolver)
    }
}
