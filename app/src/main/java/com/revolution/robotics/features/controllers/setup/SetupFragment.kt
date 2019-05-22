package com.revolution.robotics.features.controllers.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.annotation.AnimRes
import androidx.databinding.ViewDataBinding
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.extensions.onEnd
import com.revolution.robotics.core.extensions.onStart
import com.revolution.robotics.core.extensions.visible
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.FragmentControllerSetupCoreBinding
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.features.configure.controller.ControllerButton
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentProgramViewModel
import org.kodein.di.erased.instance

@Suppress("TooManyFunctions")
abstract class SetupFragment :
    BaseFragment<FragmentControllerSetupCoreBinding, SetupViewModel>(R.layout.fragment_controller_setup_core),
    SetupMvp.View, DialogEventBus.Listener {

    companion object {
        const val ID_CREATE_NEW = -1

        private const val PROGRAM_SELECTOR_ANIMATION_DURATION_MS = 250L
        private var Bundle.controllerId by BundleArgumentDelegate.Int("controllerId")
    }

    override val viewModelClass = SetupViewModel::class.java

    private val buttonNames = ControllerButton.values().toList()
    private val presenter: SetupMvp.Presenter by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()
    private val storage: UserConfigurationStorage by kodein.instance()

    abstract fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View

    abstract fun getContentBinding(): ViewDataBinding?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val core = super.onCreateView(inflater, container, savedInstanceState)
        createContentView(inflater, binding?.contentWrapper)
        return core
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        arguments?.controllerId?.let { presenter.loadControllerAndPrograms(it) }
        dialogEventBus.register(this)
        binding?.apply {
            toolbarViewModel = SetupToolbarViewModel(resourceResolver)
            dimmer.setOnClickListener { hideProgramSelector() }
        }
        storage.controllerHolder?.programToBeAdded?.let { programToAdd -> addProgram(programToAdd) }
        storage.controllerHolder?.programToBeAdded = null
        hideProgramSelector()
    }

    override fun onDestroyView() {
        presenter.unregister()
        dialogEventBus.unregister(this)
        viewModel?.saveToStorage(storage)
        super.onDestroyView()
    }

    override fun onBackPressed() =
        if (binding?.dimmer?.visibility == View.VISIBLE) {
            hideProgramSelector()
            true
        } else {
            super.onBackPressed()
        }

    private fun hideProgramSelector() {
        binding?.apply {
            if (dimmer.visible) {
                dimmer.disappearWithAnimation(R.anim.dim_screen_disappear)
                programSelector.disappearWithAnimation(R.anim.program_selector_disappear)
            }
        }
        viewModel?.selectProgram(SetupViewModel.NO_PROGRAM_SELECTED)
    }

    override fun updateContentBindings() {
        getContentBinding()?.invalidateAll()
    }

    override fun onProgramSlotSelected(index: Int, mostRecent: MostRecentProgramViewModel) {
        viewModel?.let { updateContentBindings() }
        if (index != SetupViewModel.NO_PROGRAM_SELECTED) {
            binding?.apply {
                this.mostRecent = mostRecent
                dimmer.appearWithAnimation(R.anim.dim_screen_appear)
                programSelector.appearWithAnimation(R.anim.program_selector_appear)
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        if (event == DialogEvent.ADD_PROGRAM) {
            addProgram(event.extras.getParcelable(ProgramDialog.KEY_PROGRAM))
        } else if (event == DialogEvent.REMOVE_PROGRAM) {
            removeProgram()
        }
    }

    private fun addProgram(program: UserProgram?) {
        program?.let { userProgram ->
            viewModel?.selectedProgram?.let { selectedProgram ->
                storage.addButtonProgram(userProgram, buttonNames[selectedProgram - 1])
                viewModel?.onProgramSet(userProgram)
                hideProgramSelector()
            }
        }
    }

    private fun removeProgram() {
        viewModel?.selectedProgram?.let { selectedProgram ->
            storage.removeButtonProgram(buttonNames[selectedProgram - 1])
            viewModel?.onProgramSet(null)
            hideProgramSelector()
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
}
