package com.revolution.robotics.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.revolution.robotics.databinding.ViewRemoteImageBinding

class RemoteImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    val progress: ProgressBar
    val image: ImageView

    init {
        val binding = ViewRemoteImageBinding.inflate(LayoutInflater.from(context), this, true)
        progress = binding.progress
        image = binding.image
    }
}
