package com.revolution.robotics.features.challenges.challengeGroup

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentChallengeGroupBinding
import com.revolution.robotics.features.challenges.challengeGroup.adapter.ChallengeGroupAdapter
import com.revolution.robotics.features.challenges.challengeGroup.download.DownloadChallengeDialog
import org.kodein.di.erased.instance

class ChallengeGroupFragment :
    BaseFragment<FragmentChallengeGroupBinding, ChallengeGroupViewModel>(R.layout.fragment_challenge_group),
    ChallengeGroupMvp.View, DialogEventBus.Listener {

    companion object {
        private const val SPAN_COUNT = 3
    }

    override val viewModelClass: Class<ChallengeGroupViewModel> = ChallengeGroupViewModel::class.java
    override val screen = Reporter.Screen.CHALLENGE_GROUP

    private val resourceResolver: ResourceResolver by kodein.instance()
    private val presenter: ChallengeGroupMvp.Presenter by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()
    private val adapter = ChallengeGroupAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.apply {
            toolbarViewModel = ChallengeGroupToolbarViewModel(resourceResolver)
            recycler.adapter = adapter
            recycler.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
        presenter.register(this, viewModel)
        dialogEventBus.register(this)
    }

    override fun onDestroyView() {
        presenter.unregister()
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    override fun showDownloadDialog(challengeCategoryId: String?) {
        challengeCategoryId?.let { DownloadChallengeDialog.newInstance(it).show(fragmentManager) }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.CHALLENGE_DOWNLOADED -> presenter.loadChallengeCategories()
            else -> Unit
        }
    }
}
