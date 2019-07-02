package com.revolution.robotics.core.bindings

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.revolution.robotics.R
import com.revolution.robotics.core.glide.GlideApp
import com.revolution.robotics.core.utils.CameraHelper
import com.revolution.robotics.views.RemoteImageView
import java.io.File

@BindingAdapter("android:src", "errorDrawable", requireAll = false)
fun loadImage(imageView: ImageView, url: String?, errorDrawable: Drawable?) {
    if (!url.isNullOrEmpty()) {
        GlideApp.with(imageView)
            .load(url)
            .apply {
                if (errorDrawable == null) {
                    error(R.drawable.ic_image_not_found)
                } else {
                    error(errorDrawable)
                }
            }
            .into(imageView)
    } else {
        setErrorDrawable(imageView, errorDrawable)
    }
}

@BindingAdapter("localResource", "errorDrawable", requireAll = false)
fun loadLocalResource(imageView: ImageView, @DrawableRes localResource: Int, errorDrawable: Drawable?) {
    if (localResource != 0) {
        GlideApp.with(imageView)
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

@BindingAdapter("firebaseImage", "errorDrawable", requireAll = false)
fun loadFirebaseImage(imageView: ImageView, reference: StorageReference, errorDrawable: Drawable?) {
    GlideApp.with(imageView)
        .load(reference)
        .apply {
            if (errorDrawable == null) {
                error(R.drawable.ic_image_not_found)
            } else {
                error(errorDrawable)
            }
        }
        .into(imageView)
}

@BindingAdapter("firebaseImageUrl", "errorDrawable", "animate", requireAll = false)
fun loadFirebaseImage(imageView: ImageView, gsUrl: String?, errorDrawable: Drawable?, animate: Boolean?) {
    if (!gsUrl.isNullOrEmpty()) {
        GlideApp.with(imageView)
            .load(FirebaseStorage.getInstance().getReferenceFromUrl(gsUrl))
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

@BindingAdapter("firebaseImageUrl", "robotId", "errorDrawable", "animate", requireAll = false)
fun loadFirebaseImage(
    remoteImageView: RemoteImageView,
    gsUrl: String?,
    robotId: Int?,
    errorDrawable: Drawable?,
    animate: Boolean?
) {
    val imageFile =
        CameraHelper.getImageFile(remoteImageView.context, CameraHelper.generateFilenameForRobot(robotId ?: -1))
    if (imageFile.exists()) {
        remoteImageView.empty.setImageResource(0)
        loadImageFromFile(remoteImageView, imageFile, null)
    } else if (!gsUrl.isNullOrEmpty()) {
        remoteImageView.empty.setImageResource(0)
        loadFirebaseImage(remoteImageView.image, gsUrl, errorDrawable, animate)
    } else {
        if (errorDrawable == null) {
            remoteImageView.empty.setImageResource(R.drawable.ic_image_not_found)
        } else {
            remoteImageView.empty.setImageDrawable(errorDrawable)
        }
    }
}

@BindingAdapter("imageFile", "firebaseImageUrl")
fun loadImageFromFile(imageView: RemoteImageView, file: File?, gsUrl: String?) {
    if (file != null && file.exists()) {
        Glide.with(imageView)
            .load(file)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(imageView.image)
    } else if (!gsUrl.isNullOrEmpty()) {
        loadFirebaseImage(imageView.image, gsUrl, null, false)
    }
}
