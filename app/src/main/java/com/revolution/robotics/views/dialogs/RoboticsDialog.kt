package com.revolution.robotics.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.forEachIndexed
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.dimension
import com.revolution.robotics.core.extensions.gone
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.databinding.DialogRoboticsCoreBinding
import com.revolution.robotics.databinding.DialogRoboticsCoreButtonBinding
import org.kodein.di.KodeinAware
import org.kodein.di.LateInitKodein

@Suppress("OptionalUnit")
abstract class RoboticsDialog : DialogFragment() {

    abstract val hasCloseButton: Boolean
    abstract val dialogFaces: List<DialogFace<*>>
    abstract val dialogButtons: List<DialogButton>

    protected var kodein = LateInitKodein()

    protected lateinit var binding: DialogRoboticsCoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kodein.baseKodein = (requireContext().applicationContext as KodeinAware).kodein
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogRoboticsCoreBinding.inflate(inflater, container, false)
        dialogFaces[0].activate(this, inflater, binding.container)
        dialogButtons.forEachIndexed { index, button ->
            val buttonBinding = DialogRoboticsCoreButtonBinding.inflate(inflater, binding.buttonContainer, false)
            buttonBinding.viewModel = DialogButtonViewModel(button, index == 0, index == dialogButtons.size - 1)
            binding.buttonContainer.addView(buttonBinding.root)
        }

        if (dialogButtons.isEmpty()) {
            binding.buttonContainer.gone = true
        }

        binding.background = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dialog_chip_size)
            .backgroundColorResource(R.color.grey_1e)
            .create()
        binding.viewModel = RoboticsDialogViewModel(hasCloseButton) {
            dialog.dismiss()
            onDialogCloseButtonClicked()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonContainer.forEachIndexed { index, child ->
            child.layoutParams = (child.layoutParams as LinearLayout.LayoutParams).apply {
                if (index != binding.buttonContainer.childCount - 1) {
                    marginEnd = view.context.dimension(R.dimen.dimen_2dp)
                }
                weight = 1.0f
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.apply {
            setLayout(context.dimension(R.dimen.dialog_width), context.dimension(R.dimen.dialog_height))
            setBackgroundDrawable(null)
        }
    }

    open fun onDialogCloseButtonClicked() = Unit

    fun activateFace(dialogFace: DialogFace<*>) {
        binding.container.removeAllViews()
        context?.let { context -> dialogFace.activate(this, LayoutInflater.from(context), binding.container) }
    }

    fun show(fragmentManager: FragmentManager?) =
        show(fragmentManager, javaClass.simpleName)
}
