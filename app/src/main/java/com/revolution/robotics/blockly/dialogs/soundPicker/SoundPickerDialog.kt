package com.revolution.robotics.blockly.dialogs.soundPicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.revolution.robotics.R
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.blockly.dialogs.soundPicker.adapter.SoundPickerAdapter
import com.revolution.robotics.blockly.utils.SoundHelper
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.BlocklyDialogSoundPickerBinding
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import org.kodein.di.erased.instance

class SoundPickerDialog :
    JavascriptPromptDialog<BlocklyDialogSoundPickerBinding>(R.layout.blockly_dialog_sound_picker), SoundPickerMvp.View {

    companion object {
        private const val SPAN_COUNT = 5

        private var Bundle.title by BundleArgumentDelegate.String("title")
        private var Bundle.selectedSound by BundleArgumentDelegate.StringNullable("selectedSound")

        fun newInstance(title: String, selectedSound: String? = null) =
            SoundPickerDialog().withArguments { bundle ->
                bundle.title = title
                bundle.selectedSound = selectedSound
            }
    }

    override val hasCloseButton = true
    override val hasTitle = false

    private val presenter: SoundPickerMvp.Presenter by kodein.instance()
    private val adapter = SoundPickerAdapter()
    private val soundHelper = SoundHelper()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            binding.viewModel = SoundPickerViewModel(presenter)
            binding.background = ChippedBoxConfig.Builder()
                .chipSize(R.dimen.dimen_8dp)
                .backgroundColorResource(R.color.grey_28)
                .borderColorResource(R.color.white)
                .create()
            binding.recycler.apply {
                layoutManager = GridLayoutManager(context, SPAN_COUNT)
                adapter = this@SoundPickerDialog.adapter
            }
            arguments?.let { title.set(it.title) }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, null)
        presenter.loadSounds(arguments?.selectedSound)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onSoundsLoaded(sounds: List<SoundOption>) {
        adapter.setItems(sounds)
    }

    override fun onSoundSelected(fileName: String) {
        soundHelper.playSound(fileName, requireContext())
    }

    override fun onSoundConfirmed(fileName: String?) {
        if (fileName == null) {
            dismissAllowingStateLoss()
        } else {
            confirmResult(fileName)
        }
    }
}
