package com.revolution.robotics.core.extensions

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import java.lang.IllegalArgumentException

fun Context.color(@ColorRes colorId: Int) = ContextCompat.getColor(this, colorId)

fun Context.dimension(@DimenRes dimensionId: Int) = resources.getDimensionPixelSize(dimensionId)

fun Context.dimensionAsFloat(@DimenRes dimensionId: Int) = resources.getDimension(dimensionId)

fun Context.drawable(@DrawableRes drawableId: Int) = AppCompatResources.getDrawable(this, drawableId)

fun Context.font(@FontRes fontId: Int) =
    ResourcesCompat.getFont(this, fontId) ?: throw IllegalArgumentException("Font doesn't exist")

fun Context.animatedDrawable(@DrawableRes drawableId: Int) = AnimatedVectorDrawableCompat.create(this, drawableId)

fun Context.createAndShowDialog(view: View): Dialog? =
    AlertDialog.Builder(this).let { builder ->
        builder.setCancelable(true)
        builder.setView(view)

        return builder.create().apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            show()
        }
    }
