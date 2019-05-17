package com.revolution.robotics.core.domain.local

import android.util.SparseArray

data class UserControllerWithPrograms(
    val userController: UserController,
    val backgroundBindings: List<UserBackgroundProgramBinding>,
    val programs: SparseArray<UserProgram>
)