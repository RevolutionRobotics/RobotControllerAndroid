package com.revolution.robotics.core.kodein.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import com.revolution.robotics.core.extensions.color
import com.revolution.robotics.core.extensions.dimension
import com.revolution.robotics.core.extensions.font

class ResourceResolver(val context: Context) {

    @Suppress("SpreadOperator")
    fun string(@StringRes stringId: Int, vararg arguments: String): String? = context.getString(stringId, *arguments)

    fun dimen(@DimenRes dimenId: Int) = context.dimension(dimenId)

    fun color(@ColorRes colorId: Int) = context.color(colorId)

    fun font(@FontRes fontId: Int) = context.font(fontId)

    fun bitmap(@DrawableRes drawableId: Int, options: BitmapFactory.Options? = null): Bitmap? =
        BitmapFactory.decodeResource(context.resources, drawableId, options)

    fun drawable(@DrawableRes drawableId: Int): Drawable? = context.getDrawable(drawableId)

    fun drawableResourceByName(name: String) = context.resources.getIdentifier(name, "drawable", context.packageName)
}
