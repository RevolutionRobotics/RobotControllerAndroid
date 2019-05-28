package com.revolution.robotics.blockly.dialogs.soundPicker

import com.revolution.robotics.core.Mvp

class SoundPickerMvp : Mvp {

    interface View : Mvp.View {
        fun onSoundsLoaded(sounds: List<SoundOption>)
        fun onSoundSelected(fileName: String)
        fun onSoundConfirmed(fileName: String?)
    }

    interface Presenter : Mvp.Presenter<View, SoundOption> {
        fun loadSounds()
        fun onSoundSelected(sound: SoundOption)
        fun onDoneClicked()
    }
}