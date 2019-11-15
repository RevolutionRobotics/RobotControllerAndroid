package com.revolution.robotics.views

import android.content.Context
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.string
import com.revolution.robotics.databinding.ViewChippedEditTextBinding
import com.revolution.robotics.views.ChippedEditTextViewModel.Companion.CHIPPED_EDIT_TEXT_DEFAULT_MAX_LENGTH
import com.revolution.robotics.views.ChippedEditTextViewModel.Companion.CHIPPED_EDIT_TEXT_DEFAULT_MAX_LINES
import com.revolution.robotics.views.ChippedEditTextViewModel.Companion.CHIPPED_EDIT_TEXT_DEFAULT_MIN_LINES

class ChippedEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    private var viewModel = ChippedEditTextViewModel()
    val binding: ViewChippedEditTextBinding

    init {
        attrs?.let {
            initVariables(it)
        }
        binding = ViewChippedEditTextBinding.inflate(LayoutInflater.from(context), this, true)
        binding.viewModel = viewModel
    }

    private fun initVariables(attrs: AttributeSet?) {
        if (attrs == null) return
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ChippedEditText,
            0, 0
        )

        try {
            viewModel.apply {
                title = context.string(a.getResourceId(R.styleable.ChippedEditText_cetTitle, 0))
                text = context.string(a.getResourceId(R.styleable.ChippedEditText_cetText, 0))
                hint = context.string(a.getResourceId(R.styleable.ChippedEditText_cetHint, 0))

                titleColor = a.getResourceId(R.styleable.ChippedEditText_cetTitleColor, R.color.grey_8e)
                textColor = a.getResourceId(R.styleable.ChippedEditText_cetTextColor, R.color.white)
                hintColor = a.getResourceId(R.styleable.ChippedEditText_cetHintColor, R.color.grey_8e)
                borderColor = a.getResourceId(R.styleable.ChippedEditText_cetBorderColor, R.color.grey_8e)
                backgroundColor = a.getResourceId(R.styleable.ChippedEditText_cetBackgroundColor, R.color.grey_28)

                textMaxLines = a.getInt(R.styleable.ChippedEditText_cetMaxLines, CHIPPED_EDIT_TEXT_DEFAULT_MAX_LINES)
                textMinLines = a.getInt(R.styleable.ChippedEditText_cetMinLines, CHIPPED_EDIT_TEXT_DEFAULT_MIN_LINES)
                textMaxLength = a.getInt(R.styleable.ChippedEditText_cetMaxLength, CHIPPED_EDIT_TEXT_DEFAULT_MAX_LENGTH)
            }
        } finally {
            a.recycle()
        }
    }

    fun setViewModel(viewModel: ChippedEditTextViewModel) {
        this.viewModel = viewModel
        binding.viewModel = viewModel

        binding.content.let { content ->
            content.filters = arrayOf(InputFilter.LengthFilter(viewModel.textMaxLength))
            if (viewModel.textMaxLines == 1 && viewModel.textMaxLines == 1) {
                content.setSingleLine()
            } else content.setSingleLine(false)
        }

        binding.executePendingBindings()
    }

    fun getContent() = binding.content.text?.toString() ?: ""

    fun addTextChangedListener(textWatcher: TextWatcher) {
        binding.content.addTextChangedListener(textWatcher)
    }
}
