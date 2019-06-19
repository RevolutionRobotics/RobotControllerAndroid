package com.revolution.robotics.features.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentCommunityBinding
import org.kodein.di.erased.instance

class CommunityFragment : BaseFragment<FragmentCommunityBinding, CommunityViewModel>(R.layout.fragment_community),
    CommunityMvp.View {

    override val viewModelClass = CommunityViewModel::class.java

    private val resourceResolver: ResourceResolver by kodein.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            binding?.toolbarViewModel = CommunityToolbarViewModel(resourceResolver)
        }

    override fun openCommunity() {
        // TODO open community here
    }
}
