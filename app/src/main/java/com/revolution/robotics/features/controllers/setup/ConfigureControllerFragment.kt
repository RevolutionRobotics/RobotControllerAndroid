package com.revolution.robotics.features.controllers.setup

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.annotation.AnimRes
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.extensions.onEnd
import com.revolution.robotics.core.extensions.onStart
import com.revolution.robotics.core.extensions.visible
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentControllerSetupCoreBinding
import com.revolution.robotics.features.configure.ConfigureFragmentDirections
import com.revolution.robotics.features.configure.controller.ControllerButton
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentProgramViewModel
import org.kodein.di.erased.instance

@Suppress("TooManyFunctions")
class ConfigureControllerFragment :
    BaseFragment<FragmentControllerSetupCoreBinding, ConfigureControllerViewModel>(R.layout.fragment_controller_setup_core),
    ConfigureControllerMvp.View, DialogEventBus.Listener {

    companion object {
        private const val PROGRAM_SELECTOR_ANIMATION_DURATION_MS = 250L

        private var Bundle.configId by BundleArgumentDelegate.Int("configId")

        fun newInstance(configId: Int) = ConfigureControllerFragment().withArguments { bundle ->
            bundle.configId = configId
        }
    }

    override val viewModelClass = ConfigureControllerViewModel::class.java

    private val presenter: ConfigureControllerMvp.Presenter by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()
    private val navigator: Navigator by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        arguments?.configId?.let { presenter.loadControllerAndPrograms(it) }
        dialogEventBus.register(this)
        binding?.apply {
            dimmer.setOnClickListener { hideProgramSelector() }
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    override fun onBackPressed() =
        if (binding?.dimmer?.visibility == View.VISIBLE) {
            hideProgramSelector()
            true
        } else {
            super.onBackPressed()
        }

    override fun hideProgramSelector() {
        binding?.apply {
            if (dimmer.visible) {
                dimmer.disappearWithAnimation(R.anim.dim_screen_disappear)
                programSelector.disappearWithAnimation(R.anim.program_selector_disappear)
            }
        }
        viewModel?.selectProgram(ConfigureControllerViewModel.NO_PROGRAM_SELECTED)
    }

    override fun updateContentBindings() {
        binding?.invalidateAll()
    }

    override fun onProgramSlotSelected(index: Int, mostRecent: MostRecentProgramViewModel?) {
        viewModel?.let { updateContentBindings() }
        if (index != ConfigureControllerViewModel.NO_PROGRAM_SELECTED) {
            binding?.apply {
                this.mostRecent = mostRecent
                dimmer.appearWithAnimation(R.anim.dim_screen_appear)
                programSelector.appearWithAnimation(R.anim.program_selector_appear)
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.ADD_PROGRAM -> presenter.addProgram(event.program())
            DialogEvent.REMOVE_PROGRAM -> presenter.removeProgram()
            DialogEvent.EDIT_PROGRAM -> event.program().let { program ->
                navigateToEditProgram(program)
            }
            else -> Unit

        }
    }

    private fun View.appearWithAnimation(@AnimRes animRes: Int) {
        clearAnimation()
        visibility = View.INVISIBLE
        binding?.dimmer?.apply {
            isClickable = true
            isFocusable = true
        }
        startAnimation(AnimationUtils.loadAnimation(context, animRes).apply {
            duration = PROGRAM_SELECTOR_ANIMATION_DURATION_MS
            interpolator = DecelerateInterpolator()
            onStart { this@appearWithAnimation.visibility = View.VISIBLE }
        })
    }

    private fun View.disappearWithAnimation(@AnimRes animRes: Int) {
        clearAnimation()
        visibility = View.VISIBLE
        binding?.dimmer?.apply {
            isClickable = false
            isFocusable = false
        }
        startAnimation(AnimationUtils.loadAnimation(context, animRes).apply {
            duration = PROGRAM_SELECTOR_ANIMATION_DURATION_MS
            interpolator = AccelerateInterpolator()
            onEnd { this@disappearWithAnimation.visibility = View.INVISIBLE }
        })
    }

    override fun showAllPrograms(controllerButton: ControllerButton, configId: Int) {
        navigator.navigate(ConfigureFragmentDirections.toProgramSelector(controllerButton.name, configId))
    }

    override fun navigateToEditProgram(userProgram: UserProgram?) {
        userProgram?.let { navigator.navigate(ConfigureFragmentDirections.toCoding(it, true)) }
    }

    private fun DialogEvent.program() =
        extras.getParcelable<UserProgram>(ProgramDialog.KEY_PROGRAM)
}
