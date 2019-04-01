package com.revolution.robotics.core.utils.dynamicPermissions

data class DynamicPermissionListener(
    val onPermissionGranted: () -> Unit,
    val onPermissionDenied: (() -> Unit)? = null,
    val showRationale: (() -> Unit)? = null
)