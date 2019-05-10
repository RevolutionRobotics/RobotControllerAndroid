package com.revolution.robotics.features.configure.save

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogSaveBinding
import com.revolution.robotics.views.ChippedEditTextViewModel
import com.revolution.robotics.views.dialogs.DialogFace

abstract class SaveDialogFace : DialogFace<DialogSaveBinding>(R.layout.dialog_save) {
    abstract val titleResource: Int
    abstract val nameTitleResource: Int
    abstract val nameHintResource: Int
    abstract val descriptionTitleResource: Int
    abstract val descriptionHintResource: Int

    var currentName = ""
    var currentDescription = ""

    open val onNameChangedCallbacks: (() -> Unit)? = null
    open val onDescriptionChangedCallbacks: (() -> Unit)? = null

    companion object {
        const val DEFAULT_DESCRIPTION_MIN_LINES = 1
        const val DEFAULT_DESCRIPTION_MAX_LINES = 2
        const val DEFAULT_DESCRIPTION_MAX_LENGTH = 80
        const val DEFAULT_NAME_MIN_LINES = 1
        const val DEFAULT_NAME_MAX_LINES = 1
        const val DEFAULT_NAME_MAX_LENGTH = 40
    }

    override fun onActivated() {
        super.onActivated()
        binding?.apply {
            val nameConfig =
                getChippedEditTextConfig(
                    name.context,
                    nameTitleResource,
                    nameHintResource,
                    true
                )
            val descriptionConfig =
                getChippedEditTextConfig(
                    description.context,
                    descriptionTitleResource,
                    descriptionHintResource,
                    false
                )

            title.setText(titleResource)
            name.setViewModel(nameConfig)
            description.setViewModel(descriptionConfig)

            onNameChangedCallbacks?.let {
                name.addTextChangedListener(SaveDialogTextWatcher(it))
            }
            onDescriptionChangedCallbacks?.let {
                description.addTextChangedListener(SaveDialogTextWatcher(it))
            }
        }
    }

    private fun getChippedEditTextConfig(
        context: Context,
        titleRes: Int,
        hintRes: Int,
        isNameConfig: Boolean
    ): ChippedEditTextViewModel {
        return ChippedEditTextViewModel(
            title = context.getString(titleRes),
            hint = context.getString(hintRes),
            text = "",
            titleColor = R.color.white,
            textColor = R.color.white,
            hintColor = R.color.grey_8e,
            borderColor = R.color.grey_8e,
            backgroundColor = R.color.grey_28,
            textMinLines = if (isNameConfig) DEFAULT_NAME_MIN_LINES else DEFAULT_DESCRIPTION_MIN_LINES,
            textMaxLines = if (isNameConfig) DEFAULT_NAME_MAX_LINES else DEFAULT_DESCRIPTION_MAX_LINES,
            textMaxLength = if (isNameConfig) DEFAULT_NAME_MAX_LENGTH else DEFAULT_DESCRIPTION_MAX_LENGTH
        )
    }

    fun getName(): String = binding?.name?.getContent() ?: ""
    fun getDescription(): String = binding?.description?.getContent() ?: ""

    class SaveDialogTextWatcher(private val callbacks: (() -> Unit)) : TextWatcher {
        override fun afterTextChanged(s: Editable) = Unit
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = callbacks()
    }
}
