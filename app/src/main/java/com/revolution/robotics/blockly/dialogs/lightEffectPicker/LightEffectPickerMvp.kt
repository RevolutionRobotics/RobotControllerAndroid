package com.revolution.robotics.blockly.dialogs.lightEffectPicker

import com.revolution.robotics.core.Mvp

class LightEffectPickerMvp : Mvp {

    interface View : Mvp.View {
        fun onLightEffectsLoaded(lightEffects: List<LightEffectOption>)
        fun onLightEffectSelected(fileName: String)
    }

    interface Presenter : Mvp.Presenter<View, LightEffectOption> {
        fun loadLightEffects(selectedLightEffect: String?)
        fun onLightEffectSelected(lightEffect: LightEffectOption)
    }
}
