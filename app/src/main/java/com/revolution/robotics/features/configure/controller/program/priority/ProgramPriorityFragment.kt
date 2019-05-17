package com.revolution.robotics.features.configure.controller.program.priority

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentProgramPriorityBinding
import org.kodein.di.erased.instance


class ProgramPriorityFragment :
    BaseFragment<FragmentProgramPriorityBinding, ProgramPriorityViewModel>(R.layout.fragment_program_priority),
    ProgramPriorityMvp.View {

    override val viewModelClass: Class<ProgramPriorityViewModel> = ProgramPriorityViewModel::class.java
    private val presenter: ProgramPriorityMvp.Presenter by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()

    private var adapter: ProgramPriorityAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        val callback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                presenter.onItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                adapter?.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                presenter.onDragEnded()
            }
        }
        val touchHelper = ItemTouchHelper(callback)
        adapter = ProgramPriorityAdapter(this, touchHelper)

        binding?.toolbarViewModel = ProgramPriorityToolbarViewModel(resourceResolver)
        binding?.recyclerPriority?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ProgramPriorityFragment.adapter
            setHasFixedSize(true)
            touchHelper.attachToRecyclerView(this)
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        binding?.recyclerPriority?.adapter = null
        super.onDestroyView()
    }
}
