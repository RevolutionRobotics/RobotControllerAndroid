package com.revolution.robotics.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.forEachIndexed
import com.revolution.robotics.BaseDialog
import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.extensions.dimension
import com.revolution.robotics.core.extensions.gone
import com.revolution.robotics.databinding.DialogRoboticsCoreBinding
import com.revolution.robotics.databinding.DialogRoboticsCoreButtonBinding
import org.kodein.di.KodeinAware
import org.kodein.di.LateInitKodein
import org.kodein.di.erased.instance

@Suppress("OptionalUnit")
abstract class RoboticsDialog : BaseDialog() {

    abstract val hasCloseButton: Boolean
    abstract val dialogFaces: List<DialogFace<*>>
    abstract val dialogButtons: List<DialogButton>

    protected var kodein = LateInitKodein()
    val dialogEventBus: DialogEventBus by kodein.instance()

    protected lateinit var binding: DialogRoboticsCoreBinding
    protected lateinit var activeFace: DialogFace<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kodein.baseKodein = (requireContext().applicationContext as KodeinAware).kodein
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogRoboticsCoreBinding.inflate(inflater, container, false)
        activateFace(dialogFaces[0])

        binding.background = dialogBackgroundConfig.create()
        binding.viewModel = RoboticsDialogViewModel(hasCloseButton) {
            dialog.dismiss()
            onDialogCloseButtonClicked()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateButtonWeights()
    }

    open fun onDialogCloseButtonClicked() = Unit

    fun activateFace(dialogFace: DialogFace<*>) {
        binding.container.removeAllViews()
        context?.let { context -> dialogFace.activate(this, LayoutInflater.from(context), binding.container) }
        activeFace = dialogFace
        createButtons()
        activeFace.onActivated()
    }

    private fun createButtons() {
        val inflater = LayoutInflater.from(context)
        binding.buttonContainer.removeAllViews()

        val buttons = dialogButtons.union(activeFace.dialogFaceButtons)
        buttons.forEachIndexed { index, button ->
            val buttonBinding = DialogRoboticsCoreButtonBinding.inflate(inflater, binding.buttonContainer, false)
            buttonBinding.viewModel = DialogButtonViewModel(button, index == 0, index == buttons.size - 1)
            binding.buttonContainer.addView(buttonBinding.root)
            button.viewModel = buttonBinding.viewModel
        }
        binding.buttonContainer.gone = buttons.isEmpty()
        updateButtonWeights()
    }

    private fun updateButtonWeights() {
        binding.buttonContainer.forEachIndexed { index, child ->
            child.layoutParams = (child.layoutParams as LinearLayout.LayoutParams).apply {
                if (index != binding.buttonContainer.childCount - 1) {
                    marginEnd = requireContext().dimension(R.dimen.dimen_2dp)
                }
                weight = 1.0f
            }
        }
    }
}
