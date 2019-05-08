package com.revolution.robotics.core.bindings

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.revolution.robotics.core.glide.GlideApp
import com.revolution.robotics.views.RemoteImageView

@BindingAdapter("android:src")
fun loadImage(imageView: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        GlideApp.with(imageView)
            .load(url)
            .into(imageView)
    }
}

@BindingAdapter("localResource")
fun loadLocalResource(imageView: ImageView, @DrawableRes localResource: Int) {
    GlideApp.with(imageView)
        .load(localResource)
        .into(imageView)
}

@BindingAdapter("firebaseImage")
fun loadFirebaseImage(imageView: ImageView, reference: StorageReference) {
    GlideApp.with(imageView)
        .load(reference)
        .into(imageView)
}

@BindingAdapter("firebaseImageUrl")
fun loadFirebaseImage(imageView: ImageView, gsUrl: String?) {
    if (!gsUrl.isNullOrEmpty()) {
        GlideApp.with(imageView)
            .load(FirebaseStorage.getInstance().getReferenceFromUrl(gsUrl))
            .into(imageView)
    }
}

@BindingAdapter("firebaseImageUrl")
fun loadFirebaseImage(remoteImageView: RemoteImageView, gsUrl: String?) =
    loadFirebaseImage(remoteImageView.image, gsUrl)
