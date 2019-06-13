package com.revolution.robotics.blockly.dialogs.soundPicker

import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider

class SoundPickerPresenter(
    private val contextProvider: ApplicationContextProvider
) : SoundPickerMvp.Presenter {

    companion object {
        private const val FOLDER_SOUNDS = "sounds"
        private const val EXTENSION_MP3 = ".mp3"
    }

    override var view: SoundPickerMvp.View? = null
    override var model: SoundOption? = null

    private var selectedSound: SoundOption? = null

    override fun loadSounds(selectedSound: String?) {
        val assets = contextProvider.applicationContext.assets
        val result = ArrayList<SoundOption>().apply {
            val fileNames =
                assets.list(FOLDER_SOUNDS)?.filter {
                    it.endsWith(EXTENSION_MP3)
                }?.map {
                    it.substring(0, it.length - EXTENSION_MP3.length)
                }
            if (fileNames != null) {
                addAll(fileNames.map { filename ->
                    SoundOption(
                        filename,
                        // TODO remove this default
                        SoundIcons.ICONS[filename] ?: 0x1F437,
                        filename == selectedSound,
                        this@SoundPickerPresenter
                    ).apply {
                        if (isSelected.get()) {
                            this@SoundPickerPresenter.selectedSound = this
                        }
                    }
                })
            }
        }

        view?.onSoundsLoaded(result)
    }

    override fun onSoundSelected(sound: SoundOption) {
        selectedSound?.isSelected?.set(false)
        sound.isSelected.set(true)
        selectedSound = sound
        view?.onSoundSelected(getFilePath(sound.fileName))
    }

    override fun onDoneClicked() {
        view?.onSoundConfirmed(selectedSound?.fileName)
    }

    private fun getFilePath(fileName: String) =
        "$FOLDER_SOUNDS/${fileName}$EXTENSION_MP3"
}
