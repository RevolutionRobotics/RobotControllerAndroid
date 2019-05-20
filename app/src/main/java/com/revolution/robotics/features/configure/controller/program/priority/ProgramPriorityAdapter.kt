package com.revolution.robotics.features.configure.controller.program.priority

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ItemTouchHelper
import com.revolution.robotics.core.utils.recyclerview.RecyclerAdapter
import java.util.Collections

class ProgramPriorityAdapter(lifecycleOwner: LifecycleOwner, itemTouchHelper: ItemTouchHelper) :
    RecyclerAdapter<ProgramPriorityItemViewModel>() {

    init {
        adapterDelegate.register(PriorityProgramDelegateItem(lifecycleOwner, itemTouchHelper))
    }

    fun swapItems(from: Int, to: Int) {
        if (from < to) {
            for (i in from until to) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in from downTo to + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
    }
}
