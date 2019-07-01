package com.revolution.robotics.features.community

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentCommunityBinding
import com.revolution.robotics.features.shared.ErrorHandler
import org.kodein.di.erased.instance

class CommunityFragment : BaseFragment<FragmentCommunityBinding, CommunityViewModel>(R.layout.fragment_community),
    CommunityMvp.View {

    companion object {
        const val COMMUNITY_FORUMS_URL = "https://revolutionrobotics.discourse.group/"
    }

    override val viewModelClass = CommunityViewModel::class.java

    private val presenter: CommunityMvp.Presenter by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()
    private val errorHandler: ErrorHandler by kodein.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            binding?.toolbarViewModel = CommunityToolbarViewModel(resourceResolver)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, null)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    @Suppress("SwallowedException")
    override fun openCommunityForums() {
        try {
            requireActivity().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(COMMUNITY_FORUMS_URL)))
        } catch (exception: ActivityNotFoundException) {
            errorHandler.onError(R.string.error_cannot_open_url)
        }
    }
}
