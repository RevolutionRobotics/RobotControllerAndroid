package com.revolution.robotics.views

import android.content.Context
import android.content.res.TypedArray
import android.text.InputFilter
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.color
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.views.chippedBox.ChippedBoxDrawable
import kotlinx.android.synthetic.main.view_chipped_edit_text.view.backgroundFrame
import kotlinx.android.synthetic.main.view_chipped_edit_text.view.content
import kotlinx.android.synthetic.main.view_chipped_edit_text.view.title

class ChippedEditText : FrameLayout {

    companion object {
        const val CHIPPED_EDIT_TEXT_DEFAULT_MAX_LINES = 2
        const val CHIPPED_EDIT_TEXT_DEFAULT_MAX_LENGTH = 25
    }

    @StringRes
    private var titleRes: Int? = null
    @StringRes
    private var textRes: Int? = null
    @StringRes
    private var hintRes: Int? = null

    @ColorRes
    private var titleColorRes: Int? = null
    @ColorRes
    private var textColorRes: Int? = null
    @ColorRes
    private var hintColorRes: Int? = null
    @ColorRes
    private var borderColorRes: Int? = null
    @ColorRes
    private var backgroundColorRes: Int? = null

    private var textMaxLines: Int? = null
    private var textMaxLength: Int? = null

    private var contentEditText: EditText? = null

    constructor(context: Context) : this(context, null) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        initVariables(attrs)
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initVariables(attrs)
        initView()
    }

    private fun initVariables(attrs: AttributeSet?) {
        if (attrs == null) return
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ChippedEditText,
            0, 0
        )

        try {
            initTexts(a)
            initColors(a)
            initIntegers(a)
        } finally {
            a.recycle()
        }
    }

    private fun initTexts(a: TypedArray) {
        titleRes = a.getResourceId(R.styleable.ChippedEditText_cetTitle, 0)
        textRes = a.getResourceId(R.styleable.ChippedEditText_cetText, 0)
        hintRes = a.getResourceId(R.styleable.ChippedEditText_cetHint, 0)
    }

    private fun initColors(a: TypedArray) {
        titleColorRes = a.getResourceId(R.styleable.ChippedEditText_cetTitleColor, R.color.grey_8e)
        textColorRes = a.getResourceId(R.styleable.ChippedEditText_cetTextColor, R.color.white)
        hintColorRes = a.getResourceId(R.styleable.ChippedEditText_cetHintColor, R.color.grey_8e)
        borderColorRes = a.getResourceId(R.styleable.ChippedEditText_cetBorderColor, R.color.grey_8e)
        backgroundColorRes = a.getResourceId(R.styleable.ChippedEditText_cetBackgroundColor, R.color.grey_28)
    }

    private fun initIntegers(a: TypedArray) {
        textMaxLines = a.getInt(R.styleable.ChippedEditText_cetMaxLines, CHIPPED_EDIT_TEXT_DEFAULT_MAX_LINES)
        textMaxLength = a.getInt(R.styleable.ChippedEditText_cetMaxLength, CHIPPED_EDIT_TEXT_DEFAULT_MAX_LENGTH)
    }

    private fun initView() {
        val view = View.inflate(context, R.layout.view_chipped_edit_text, this)
        contentEditText = view.content
        assignColorValues(view)
        assignTextValues(view)
        assignIntegerValues(view)
        assignBackground(view)
    }

    private fun assignTextValues(view: View) {
        titleRes?.let { if (it != 0) view.title.setText(it) }
        textRes?.let { if (it != 0) view.content.setText(it) }
        hintRes?.let { if (it != 0) view.content.setHint(it) }
    }

    private fun assignColorValues(view: View) {
        titleColorRes?.let { view.title.setTextColor(context.color(it)) }
        textColorRes?.let { view.content.setTextColor(context.color(it)) }
        hintColorRes?.let { view.content.setHintTextColor(context.color(it)) }
        backgroundColorRes?.let { view.title.setBackgroundColor(context.color(it)) }
    }

    private fun assignIntegerValues(view: View) {
        textMaxLines?.let { view.content.maxLines = it }
        textMaxLength?.let { view.content.filters = arrayOf(InputFilter.LengthFilter(it)) }
    }

    private fun assignBackground(view: View) {
        val backgroundConfig = ChippedBoxConfig
            .Builder()
            .apply {
                backgroundColorRes?.let { backgroundColorResource(it) }
                borderColorRes?.let { borderColorResource(it) }
            }
            .borderSize(R.dimen.dimen_1dp)
            .chipTopRight(true)
            .chipBottomLeft(true)
            .chipSize(R.dimen.dimen_8dp)
            .create()
        view.backgroundFrame.background = ChippedBoxDrawable(context, backgroundConfig)
    }

    @Suppress("UnusedPrivateMember")
    private fun getContent() = contentEditText?.text?.toString() ?: ""
}
