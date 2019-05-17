package com.revolution.robotics.features.configure.controller.program.priority

import androidx.lifecycle.LifecycleOwner
import com.revolution.robotics.core.utils.recyclerview.DiffUtilRecyclerAdapter

class ProgramPriorityAdapter(lifecycleOwner: LifecycleOwner) : DiffUtilRecyclerAdapter<ProgramPriorityItemViewModel>() {

    init {
        adapterDelegate.register(PriorityProgramDelegateItem(lifecycleOwner))
    }
}