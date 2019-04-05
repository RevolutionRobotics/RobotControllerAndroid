package com.revolution.robotics.core.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.revolution.robotics.core.glide.GlideApp

@BindingAdapter("android:src")
fun loadImage(imageView: ImageView, url: String?) {
    GlideApp.with(imageView)
        .load(url)
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
    gsUrl?.let {
        GlideApp.with(imageView)
            .load(FirebaseStorage.getInstance().getReferenceFromUrl(it))
            .into(imageView)
    }
}
