package com.revolution.robotics.core.bindings

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.revolution.robotics.R
import com.revolution.robotics.core.utils.CameraHelper
import com.revolution.robotics.views.RemoteImageView
import java.io.File

@BindingAdapter(
    "imagePath",
    "originalSize",
    "grayScale",
    "errorDrawable",
    requireAll = false
)
fun loadLocalImage(
    imageView: ImageView,
    imagePath: String,
    originalSize: Boolean?,
    grayScale: Boolean?,
    errorDrawable: Drawable?
) {
    Glide.with(imageView)
        .load(imagePath)
        .apply {
            if (errorDrawable == null) {
                error(R.drawable.ic_image_not_found)
            } else {
                error(errorDrawable)
            }
        }
        .apply { if (originalSize == true) override(SIZE_ORIGINAL, SIZE_ORIGINAL) }
        .apply {
            if (grayScale == true) {
                val colorMatrix =  ColorMatrix()
                colorMatrix.setSaturation(0.0f)
                val filter =  ColorMatrixColorFilter(colorMatrix)
                imageView.colorFilter = filter
            } else {
                imageView.clearColorFilter()
            }
        }
        .into(imageView)
}

@BindingAdapter("localResource", "errorDrawable", requireAll = false)
fun loadLocalResource(
    imageView: ImageView, @DrawableRes localResource: Int,
    errorDrawable: Drawable?
) {
    if (localResource != 0) {
        Glide.with(imageView)
            .load(localResource)
            .apply {
                if (errorDrawable == null) {
                    error(R.drawable.ic_image_not_found)
                } else {
                    error(errorDrawable)
                }
            }
            .into(imageView)
    }
}

@BindingAdapter(
    "imageUrl",
    "errorDrawable",
    "progressView",
    "animate",
    "originalSize",
    requireAll = false
)
fun loadFirebaseImage(
    imageView: ImageView,
    gsUrl: String?,
    errorDrawable: Drawable?,
    progressView: View?,
    animate: Boolean?,
    originalSize: Boolean?
) {
    if (!gsUrl.isNullOrEmpty()) {
        Glide.with(imageView)
            .load(gsUrl)
            .apply {
                if (errorDrawable == null) {
                    error(R.drawable.ic_image_not_found)
                } else {
                    error(errorDrawable)
                }

                if (animate == true) {
                    transition(DrawableTransitionOptions.withCrossFade())
                }
            }
            .apply {
                if (progressView != null) {
                    listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressView.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {

                            progressView.visibility = View.GONE
                            return false
                        }
                    }
                    )
                }
            }
            .apply { if (originalSize == true) override(SIZE_ORIGINAL, SIZE_ORIGINAL) }
            .into(imageView)
    } else {
        setErrorDrawable(imageView, errorDrawable)
    }
}

private fun setErrorDrawable(imageView: ImageView, errorDrawable: Drawable?) {
    imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
    if (errorDrawable == null) {
        imageView.setImageResource(R.drawable.ic_image_not_found)
    } else {
        imageView.setImageDrawable(errorDrawable)
    }
}

@BindingAdapter("imageUrl", "robotId", "errorDrawable", "animate", requireAll = false)
fun loadFirebaseImage(
    remoteImageView: RemoteImageView,
    gsUrl: String?,
    robotId: Int?,
    errorDrawable: Drawable?,
    animate: Boolean?
) {
    val imageFile =
        CameraHelper.getImageFile(
            remoteImageView.context,
            CameraHelper.generateFilenameForRobot(robotId ?: -1)
        )
    if (imageFile.exists()) {
        remoteImageView.empty.setImageResource(0)
        loadImageFromFile(remoteImageView, imageFile, null)
    } else if (!gsUrl.isNullOrEmpty()) {
        remoteImageView.empty.setImageResource(0)
        loadFirebaseImage(
            remoteImageView.image,
            gsUrl,
            errorDrawable,
            remoteImageView.progress,
            animate,
            false
        )
    } else {
        if (errorDrawable == null) {
            remoteImageView.empty.setImageResource(R.drawable.ic_image_not_found)
        } else {
            remoteImageView.empty.setImageDrawable(errorDrawable)
        }
    }
}

@BindingAdapter("imageFile", "imageUrl")
fun loadImageFromFile(imageView: RemoteImageView, file: File?, gsUrl: String?) {
    if (file != null && file.exists()) {
        Glide.with(imageView)
            .load(file)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(imageView.image)
    } else if (!gsUrl.isNullOrEmpty()) {
        loadFirebaseImage(imageView.image, gsUrl, null, imageView.progress, false, false)
    }
}
