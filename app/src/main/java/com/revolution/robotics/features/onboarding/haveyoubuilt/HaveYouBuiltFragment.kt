package com.revolution.robotics.features.onboarding.haveyoubuilt

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentHaveYouBuiltBinding
import org.kodein.di.KodeinAware
import org.kodein.di.erased.instance

class HaveYouBuiltFragment : BaseFragment<FragmentHaveYouBuiltBinding, HaveYouBuiltViewModel>(R.layout.fragment_have_you_built),
    HaveYouBuiltMvp.View {

    override val viewModelClass: Class<HaveYouBuiltViewModel> = HaveYouBuiltViewModel::class.java
    private val presenter: HaveYouBuiltMvp.Presenter by kodein.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kodein.baseKodein = (requireContext().applicationContext as KodeinAware).kodein
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }
}