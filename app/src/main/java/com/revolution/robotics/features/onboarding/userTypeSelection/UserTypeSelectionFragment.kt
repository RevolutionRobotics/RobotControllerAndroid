package com.revolution.robotics.features.onboarding.userTypeSelection

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentUserTypeSelectionBinding
import org.kodein.di.erased.instance

class UserTypeSelectionFragment() :
    BaseFragment<FragmentUserTypeSelectionBinding, UserTypeSelectionViewModel>(R.layout.fragment_user_type_selection),
    UserTypeSelectionMvp.View {

    override val viewModelClass: Class<UserTypeSelectionViewModel> = UserTypeSelectionViewModel::class.java

    private val presenter: UserTypeSelectionMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }
}