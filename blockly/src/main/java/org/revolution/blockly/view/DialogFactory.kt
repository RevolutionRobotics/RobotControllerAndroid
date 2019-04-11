package org.revolution.blockly.view

import android.os.Parcelable
import android.webkit.JsPromptResult
import kotlinx.android.parcel.Parcelize

interface DialogFactory {

    fun showTextInputDialog(result: JsPromptResult, options: TextOptions)

    fun showSliderDialog(result: JsPromptResult, options: SliderOptions)

    // Option classes ---------------------------------------------------------

    @Parcelize
    data class TextOptions(val title: String) : Parcelable

    @Parcelize
    data class SliderOptions(val minValue: Int, val maxValue: Int) : Parcelable
}
