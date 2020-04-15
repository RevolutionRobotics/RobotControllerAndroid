package com.revolution.robotics.core.cache

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.revolution.robotics.core.bindings.loadFirebaseImage
import com.revolution.robotics.core.bindings.loadImageFromFile
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.utils.CameraHelper
import java.io.File
import java.io.FileOutputStream

class ImageCache(
    private val context: Context
) {

    companion object {
        private const val TAG = "ImageCache"
        private const val EXTENTION_SUFFIX = ".png"
    }

    private val imageFolder = context.getDir("ImageCache", Context.MODE_PRIVATE)

    fun saveImage(url: String, bitmap: Bitmap) {
        context.let {
            try {
                val file = File(imageFolder, urlToFileName(url))
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 85, out)
                out.flush()
                out.close()
                Log.i(TAG, "Image from " + url + " saved to " + file.absoluteFile)
            } catch (e: Exception) {
                Log.i(TAG, "Failed to save image: $url")
            }
        }
    }

    fun deleteImage(url: String) {
        Log.d(TAG, "Delete $url")
        File(imageFolder, urlToFileName(url)).delete()
    }

    fun getImagePath(url: String?) = File(imageFolder, urlToFileName(url)).absolutePath

    fun getRobotImagePath(userRobot: UserRobot) : String? {
        val imageFile =
            CameraHelper.getImageFile(context, CameraHelper.generateFilenameForRobot(userRobot.id ?: -1)
            )
        return if (imageFile.exists()) {
            imageFile.absolutePath
        } else {
            File(imageFolder, urlToFileName(userRobot.coverImage)).absolutePath
        }
    }

    fun getAllImages() :List<String> {
        return imageFolder.listFiles().map { it -> fileNameToUrl(it.name) }
    }

    fun urlToFileName(url: String?) = Base64.encodeToString(url?.toByteArray(charset("UTF-8")), Base64.NO_WRAP).trim() + EXTENTION_SUFFIX

    fun fileNameToUrl(fileName: String?) = Base64.decode(fileName?.removeSuffix(EXTENTION_SUFFIX), Base64.NO_WRAP).toString(charset("UTF-8"))

    fun isSaved(url: String) = File(imageFolder, urlToFileName(url)).exists()
}