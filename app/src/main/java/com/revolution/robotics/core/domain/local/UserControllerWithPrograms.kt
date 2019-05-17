package com.revolution.robotics.core.domain.local

import android.util.SparseArray

data class UserControllerWithPrograms(
    var userController: UserController,
    var backgroundBindings: MutableList<UserBackgroundProgramBinding>,
    var programs: SparseArray<UserProgram>
)
