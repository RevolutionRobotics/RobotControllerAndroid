package com.revolution.robotics.features.controllers.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.annotation.AnimRes
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.setListener
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentControllerSetupCoreBinding
import org.kodein.di.erased.instance

abstract class SetupFragment :
    BaseFragment<FragmentControllerSetupCoreBinding, SetupViewModel>(R.layout.fragment_controller_setup_core),
    SetupMvp.View {

    companion object {
        private const val PROGRAM_SELECTOR_ANIMATION_DURATION_MS = 250L
    }

    override val viewModelClass = SetupViewModel::class.java

    private val presenter: SetupMvp.Presenter by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()

    abstract fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View

    abstract fun updateBinding(viewModel: SetupViewModel)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val core = super.onCreateView(inflater, container, savedInstanceState)
        createContentView(inflater, binding?.contentWrapper)
        return core
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        binding?.apply {
            toolbarViewModel = SetupToolbarViewModel(resourceResolver)
            dimmer.setOnClickListener { hideProgramSelector() }
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
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
            dimmer.disappearWithAnimation(R.anim.dim_screen_disappear)
            programSelector.disappearWithAnimation(R.anim.program_selector_disappear)
        }
        viewModel?.selectProgram(SetupViewModel.NO_PROGRAM_SELECTED)
    }

    override fun onProgramSlotSelected(index: Int, mostRecent: MostRecentProgramViewModel) {
        viewModel?.let { updateBinding(it) }

        if (index != SetupViewModel.NO_PROGRAM_SELECTED) {
            binding?.apply {
                this.mostRecent = mostRecent
                dimmer.appearWithAnimation(R.anim.dim_screen_appear)
                programSelector.appearWithAnimation(R.anim.program_selector_appear)
            }
        }
    }

    private fun View.appearWithAnimation(@AnimRes animRes: Int) =
        startAnimation(AnimationUtils.loadAnimation(context, animRes).apply {
            duration = PROGRAM_SELECTOR_ANIMATION_DURATION_MS
            setListener(onStart = { this@appearWithAnimation.visibility = View.VISIBLE })
            interpolator = DecelerateInterpolator()
        })

    private fun View.disappearWithAnimation(@AnimRes animRes: Int) =
        startAnimation(AnimationUtils.loadAnimation(context, animRes).apply {
            duration = PROGRAM_SELECTOR_ANIMATION_DURATION_MS
            setListener(onEnd = { this@disappearWithAnimation.visibility = View.INVISIBLE })
            interpolator = AccelerateInterpolator()
        })
}
