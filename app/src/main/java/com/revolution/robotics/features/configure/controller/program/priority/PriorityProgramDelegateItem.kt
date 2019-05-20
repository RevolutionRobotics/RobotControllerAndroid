package com.revolution.robotics.features.configure.controller.program.priority

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemPriorityProgramBinding

class PriorityProgramDelegateItem(
    private val lifecycleOwner: LifecycleOwner,
    private val itemTouchHelper: ItemTouchHelper
) :
    AdapterDelegateItem<ProgramPriorityItemViewModel>() {

    override fun canHandleData(data: Any): Boolean = data is ProgramPriorityItemViewModel

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: ProgramPriorityItemViewModel) {
        (viewHolder as PriorityViewHolder).binding.viewModel = data
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        val binding = ItemPriorityProgramBinding.inflate(inflater, parent, false)
        binding.lifecycleOwner = lifecycleOwner
        return PriorityViewHolder(binding).apply {
            binding.imgReorder.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    itemTouchHelper.startDrag(this)
                }
                false
            }
        }
    }

    class PriorityViewHolder(val binding: ItemPriorityProgramBinding) : RecyclerView.ViewHolder(binding.root)
}
