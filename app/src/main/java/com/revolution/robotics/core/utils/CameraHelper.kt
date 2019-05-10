package com.revolution.robotics.core.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.revolution.robotics.BuildConfig
import com.revolution.robotics.R
import java.io.File

class CameraHelper(private val destinationFileName: String) {

    constructor(robotId: Int) : this(generateFilenameForRobot(robotId))

    companion object {
        fun getImageFile(context: Context, destinationFileName: String): File {
            val temporaryImages = File(context.filesDir, "images")
            if (!temporaryImages.exists()) {
                temporaryImages.mkdir()
            }
            return File(temporaryImages, destinationFileName)
        }

        fun generateFilenameForRobot(robotId: Int) =
            "robot-$robotId.jpg"
    }

    fun getImageFile(context: Context) =
        getImageFile(context, destinationFileName)

    fun startCameraActivity(fragment: Fragment, requestCode: Int) {
        val activity = fragment.requireActivity()
        fragment.startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            getCameraUri(activity).let { cameraUri ->
                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                cameraUri.grantUriPermission(BuildConfig.APPLICATION_ID, activity)
                activity.packageManager.queryIntentActivities(this, PackageManager.MATCH_DEFAULT_ONLY)
                    ?.map { it.activityInfo.packageName }
                    ?.forEach { packageName -> cameraUri.grantUriPermission(packageName, activity) }
                putExtra(MediaStore.EXTRA_OUTPUT, cameraUri)
            }
        }, requestCode)
    }

    private fun getCameraUri(context: Context) =
        FileProvider.getUriForFile(context, context.getString(R.string.file_provide_author), getImageFile(context))

    private fun Uri.grantUriPermission(packageName: String, activity: Activity) =
        activity.grantUriPermission(
            packageName,
            this,
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        )
}
