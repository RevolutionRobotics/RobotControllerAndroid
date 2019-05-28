package com.revolution.robotics.blockly.dialogs.colorPicker

class ColorPickerPresenter : ColorPickerMvp.Presenter {

    override var view: ColorPickerMvp.View? = null
    override var model: ColorOption? = null

    override fun createColorOptions(colors: List<String>, selectedColor: String?) {
        view?.onColorOptionsAvailable(colors.map { ColorOption(it, it == selectedColor, this) })
    }

    override fun onColorSelected(color: ColorOption) {
        view?.onColorSelected(color)
    }
}
