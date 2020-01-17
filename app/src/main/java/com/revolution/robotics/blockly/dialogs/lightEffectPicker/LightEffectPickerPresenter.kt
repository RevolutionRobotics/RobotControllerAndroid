package com.revolution.robotics.blockly.dialogs.lightEffectPicker

class LightEffectPickerPresenter : LightEffectPickerMvp.Presenter {

    override var view: LightEffectPickerMvp.View? = null
    override var model: LightEffectOption? = null

    override fun loadLightEffects(selectedLightEffect: String?) {
        val result = LightEffectIcons.ICONS.map { icon ->
            LightEffectOption(
                icon.key,
                icon.value,
                icon.key == selectedLightEffect,
                this@LightEffectPickerPresenter
            )
        }

        view?.onLightEffectsLoaded(result)
    }

    override fun onLightEffectSelected(lightEffect: LightEffectOption) {
        view?.onLightEffectSelected(lightEffect.value)
    }
}
