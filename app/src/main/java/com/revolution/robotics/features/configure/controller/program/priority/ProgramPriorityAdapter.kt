package com.revolution.robotics.features.configure.controller.program.priority

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ItemTouchHelper
import com.revolution.robotics.core.utils.recyclerview.RecyclerAdapter

class ProgramPriorityAdapter(lifecycleOwner: LifecycleOwner, itemTouchHelper: ItemTouchHelper) :
    RecyclerAdapter<ProgramPriorityItemViewModel>() {

    init {
        adapterDelegate.register(PriorityProgramDelegateItem(lifecycleOwner, itemTouchHelper))
    }
}
