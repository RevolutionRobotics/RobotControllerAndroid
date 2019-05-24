package com.revolution.robotics.core.bindings

import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.Spanned
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.extensions.color
import com.revolution.robotics.views.ChippedEditText
import com.revolution.robotics.views.ChippedEditTextViewModel
import com.revolution.robotics.views.RoboticsButton
import com.revolution.robotics.views.carousel.CarouselAdapter
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.views.chippedBox.ChippedBoxDrawable

@BindingAdapter("listener")
fun setSeekbarListener(seekBar: SeekBar, listener: SeekBar.OnSeekBarChangeListener) {
    seekBar.setOnSeekBarChangeListener(listener)
}

@BindingAdapter("chippedBoxConfig")
fun setChippedBoxConfig(view: View, config: ChippedBoxConfig?) {
    if (config != null) {
        view.background = ChippedBoxDrawable(view.context, config)
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("carouselItems")
fun setRobotsViewPagerItems(viewPager: ViewPager, itemList: List<Any>?) {
    if (itemList != null && itemList.isNotEmpty()) {
        (viewPager.adapter as? CarouselAdapter<Any>)?.setItems(itemList)
    }
}

@BindingAdapter("textColorResource")
fun setTextColor(textView: TextView, @ColorRes color: Int) {
    if (color != 0) {
        textView.setTextColor(textView.context.color(color))
    }
}

@BindingAdapter("textColorHintResource")
fun setTextColorHintResource(editText: EditText, @ColorRes hintColor: Int) {
    if (hintColor != 0) {
        editText.setHintTextColor(editText.context.color(hintColor))
    }
}

@BindingAdapter("chippedEditModel")
fun setChippedEditViewModel(edit: ChippedEditText, model: ChippedEditTextViewModel?) {
    model?.let {
        edit.setViewModel(it)
    }
}

@BindingAdapter("inputFilter")
fun setDigits(editText: EditText, regexp: String?) {
    if (regexp == null) {
        editText.filters = arrayOf()
    } else {
        editText.filters = arrayOf(object : InputFilter {
            override fun filter(
                source: CharSequence,
                start: Int,
                end: Int,
                dest: Spanned?,
                dstart: Int,
                dend: Int
            ): CharSequence {
                if (source == "" || source.toString().matches(Regex(regexp))) {
                    return source
                }
                return ""
            }
        })
    }
}

@BindingAdapter("text", "image")
fun setupRoboticsButton(button: RoboticsButton, text: String, image: Drawable) {
    button.apply {
        this.text = text
        this.image = image
    }
}

@BindingAdapter("program")
fun setProgram(textView: TextView, program: UserProgram?) {
    program?.let { textView.text = it.name }
}

@BindingAdapter("widthPercent")
fun setWidthPercent(view: View, widthPercent: Float) {
    val layoutParams = view.layoutParams
    if (layoutParams is ConstraintLayout.LayoutParams) {
        layoutParams.matchConstraintPercentWidth = widthPercent
        view.layoutParams = layoutParams
    }
}

@BindingAdapter("textResource")
fun setText(textView: TextView, @StringRes textResource: Int) {
    if (textResource != 0) {
        textView.setText(textResource)
    }
}
