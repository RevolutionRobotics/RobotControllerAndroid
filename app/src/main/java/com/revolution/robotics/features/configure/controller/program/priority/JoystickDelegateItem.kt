package com.revolution.robotics.features.configure.controller.program.priority

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemPriorityJoystickBinding

class JoystickDelegateItem(
    private val lifecycleOwner: LifecycleOwner,
    private val itemTouchHelper: ItemTouchHelper
) :
    AdapterDelegateItem<PriorityItem>() {

    override fun canHandleData(data: Any): Boolean = data is ProgramPriorityJoystickViewModel

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        data: PriorityItem
    ) {
        (viewHolder as PriorityViewHolder).binding.viewModel = data as ProgramPriorityJoystickViewModel
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        val binding = ItemPriorityJoystickBinding.inflate(inflater, parent, false)
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

    class PriorityViewHolder(val binding: ItemPriorityJoystickBinding) : RecyclerView.ViewHolder(binding.root)
}
