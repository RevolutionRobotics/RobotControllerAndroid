package com.revolution.robotics.blockly.dialogs.lightEffectPicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.revolution.robotics.R
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.blockly.dialogs.lightEffectPicker.adapter.LightEffectPickerAdapter
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.BlocklyDialogLightEffectPickerBinding
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import org.kodein.di.erased.instance
import org.revolutionrobotics.blockly.android.view.result.LightEffectResult
import org.revolutionrobotics.blockly.android.view.result.SoundResult

class LightEffectPickerDialog :
    JavascriptPromptDialog<BlocklyDialogLightEffectPickerBinding>(R.layout.blockly_dialog_light_effect_picker), LightEffectPickerMvp.View {

    companion object {
        private const val SPAN_COUNT = 5

        private var Bundle.title by BundleArgumentDelegate.String("title")
        private var Bundle.selectedLightEffect by BundleArgumentDelegate.StringNullable("selectedLightEffect")

        fun newInstance(title: String, selectedLightEffect: String? = null) =
            LightEffectPickerDialog().withArguments { bundle ->
                bundle.title = title
                bundle.selectedLightEffect = selectedLightEffect
            }
    }

    override val hasCloseButton = true
    override val hasTitle = false

    private val presenter: LightEffectPickerMvp.Presenter by kodein.instance()
    private val adapter = LightEffectPickerAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            binding.background = ChippedBoxConfig.Builder()
                .chipSize(R.dimen.dimen_8dp)
                .backgroundColorResource(R.color.grey_28)
                .borderColorResource(R.color.white)
                .create()
            binding.recycler.apply {
                layoutManager = GridLayoutManager(context, SPAN_COUNT)
                adapter = this@LightEffectPickerDialog.adapter
            }
            arguments?.let { title.set(it.title) }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, null)
        presenter.loadLightEffects(arguments?.selectedLightEffect)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onLightEffectsLoaded(lightEffects: List<LightEffectOption>) {
        adapter.setItems(lightEffects)
    }


    override fun onLightEffectSelected(fileName: String) {
        (blocklyResultHolder.result as? LightEffectResult)?.confirm(fileName)
        dismissAllowingStateLoss()
    }

}
