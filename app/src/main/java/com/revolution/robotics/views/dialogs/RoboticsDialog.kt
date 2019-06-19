package com.revolution.robotics.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revolution.robotics.BaseDialog
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.DialogRoboticsCoreBinding
import org.kodein.di.KodeinAware
import org.kodein.di.LateInitKodein
import org.kodein.di.erased.instance

@Suppress("OptionalUnit")
abstract class RoboticsDialog : BaseDialog() {

    abstract val hasCloseButton: Boolean
    abstract val dialogFaces: List<DialogFace<*>>
    abstract val dialogButtons: List<DialogButton>

    var kodein = LateInitKodein()
    val dialogEventBus: DialogEventBus by kodein.instance()
    val navigator: Navigator by kodein.instance()

    protected lateinit var binding: DialogRoboticsCoreBinding

    private lateinit var activeFace: DialogFace<*>
    private val dialogButtonHelper = DialogButtonHelper()

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
        dialogButtonHelper.updateButtonWeights(binding.buttonContainer)
    }

    open fun onDialogCloseButtonClicked() = Unit

    fun activateFace(dialogFace: DialogFace<*>) {
        binding.container.removeAllViews()
        context?.let { context -> dialogFace.activate(this, LayoutInflater.from(context), binding.container) }
        activeFace = dialogFace

        val buttons = dialogButtons.union(activeFace.dialogFaceButtons)
        dialogButtonHelper.createButtons(binding.buttonContainer, buttons)
        activeFace.onActivated()
    }
}
