package com.revolution.robotics.features.configure.controller.program.priority

import android.annotation.SuppressLint
import android.view.GestureDetector
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
    AdapterDelegateItem<PriorityItem>() {

    override fun canHandleData(data: Any): Boolean = data is ProgramPriorityItemViewModel

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        data: PriorityItem
    ) {
        (viewHolder as PriorityViewHolder).binding.viewModel = data as ProgramPriorityItemViewModel
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        val binding = ItemPriorityProgramBinding.inflate(inflater, parent, false)
        binding.lifecycleOwner = lifecycleOwner
        return PriorityViewHolder(binding).apply {

            val gestureDetector =
                GestureDetector(binding.root.context, object : GestureDetector.SimpleOnGestureListener() {
                    override fun onLongPress(e: MotionEvent?) {
                        this@apply.binding.viewModel?.isItemSelected?.set(true)
                        itemTouchHelper.startDrag(this@apply)
                    }
                })
            binding.root.setOnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
                true
            }
        }
    }

    class PriorityViewHolder(val binding: ItemPriorityProgramBinding) : RecyclerView.ViewHolder(binding.root)
}
