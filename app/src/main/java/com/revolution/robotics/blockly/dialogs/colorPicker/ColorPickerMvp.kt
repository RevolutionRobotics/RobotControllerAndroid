package com.revolution.robotics.blockly.dialogs.colorPicker

import com.revolution.robotics.core.Mvp

class ColorPickerMvp : Mvp {

    interface View : Mvp.View {
        fun onColorOptionsAvailable(colors: List<ColorOption>)
        fun onColorSelected(color: ColorOption)
    }

    interface Presenter : Mvp.Presenter<View, ColorOption> {
        fun createColorOptions(colors: List<String>, selectedColor: String?)
        fun onColorSelected(color: ColorOption)
    }
}