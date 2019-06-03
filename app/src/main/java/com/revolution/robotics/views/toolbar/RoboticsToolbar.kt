package com.revolution.robotics.views.toolbar

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.Observable
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.dimension
import com.revolution.robotics.core.extensions.invisible
import com.revolution.robotics.core.extensions.makeConnections
import com.revolution.robotics.core.extensions.setAppearance
import com.revolution.robotics.databinding.ViewToolbarBinding

@Suppress("UnnecessaryApply")
class RoboticsToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    var viewModel: ToolbarViewModel? = null
        set(value) {
            field = value
            if (value != null) {
                logo.invisible = !value.isLogoVisible
                back.invisible = !value.hasBackOption
                title.text = value.title.get()
                value.title.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                        title.text = value.title.get()
                    }
                })
                value.options.reversed().forEachIndexed { index, option -> createOption(option, index == 0) }
            }
        }
    var hasFakeIcon = false
    private var lastIconAddedId = 0

    init {
        ViewToolbarBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private val logo = AppCompatImageView(context).apply {
        id = View.generateViewId()
        setImageResource(R.drawable.rr_logo)
        scaleType = ImageView.ScaleType.CENTER_INSIDE
        addView(this, R.dimen.toolbar_logo_size.toDimension(), R.dimen.toolbar_logo_size.toDimension())
        makeConnections { connections ->
            connections.connect(id, ConstraintSet.START, R.id.header_background, ConstraintSet.START)
            connections.connect(id, ConstraintSet.TOP, R.id.header_background, ConstraintSet.TOP)
            connections.connect(id, ConstraintSet.BOTTOM, R.id.header_border, ConstraintSet.BOTTOM)
        }
        layoutParams = (layoutParams as MarginLayoutParams).apply {
            setMargins(0, 0, 0, R.dimen.dimen_12dp.toDimension())
            marginStart = R.dimen.dimen_10dp.toDimension()
        }
    }

    private val back = AppCompatImageView(context).apply {
        id = View.generateViewId()
        setImageResource(R.drawable.back)
        setBackgroundResource(R.drawable.bg_button_default)
        scaleType = ImageView.ScaleType.CENTER_INSIDE
        addView(this, R.dimen.toolbar_back_size.toDimension(), R.dimen.toolbar_back_size.toDimension())
        makeConnections { connections ->
            connections.connect(id, ConstraintSet.START, R.id.header_background, ConstraintSet.START)
            connections.connect(id, ConstraintSet.TOP, R.id.header_background, ConstraintSet.TOP)
            connections.connect(id, ConstraintSet.BOTTOM, R.id.header_border, ConstraintSet.BOTTOM)
        }
        layoutParams = (layoutParams as MarginLayoutParams).apply {
            setMargins(0, 0, 0, R.dimen.dimen_12dp.toDimension())
            marginStart = R.dimen.dimen_6dp.toDimension()
        }
        setOnClickListener { (context as? Activity)?.onBackPressed() }
    }

    private val title = AppCompatTextView(context).apply {
        id = View.generateViewId()
        addView(this, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
        makeConnections { connections ->
            connections.connect(id, ConstraintSet.START, R.id.header_background, ConstraintSet.START)
            connections.connect(id, ConstraintSet.TOP, R.id.header_background, ConstraintSet.TOP)
            connections.connect(id, ConstraintSet.BOTTOM, R.id.header_background, ConstraintSet.BOTTOM)
        }
        setAppearance(R.style.ToolbarTitle)
        layoutParams = (layoutParams as MarginLayoutParams).apply {
            marginStart = R.dimen.toolbar_box_size.toDimension()
        }
    }

    private fun createOption(option: ToolbarOption, isFirst: Boolean) = AppCompatImageView(context).apply {
        id = View.generateViewId()
        setImageResource(option.icon)
        setBackgroundResource(R.drawable.bg_button_default)
        scaleType = ImageView.ScaleType.FIT_XY
        addView(this, R.dimen.toolbar_icon_size.toDimension(), R.dimen.toolbar_icon_size.toDimension())
        makeConnections { connections ->
            if (lastIconAddedId == 0) {
                connections.connect(id, ConstraintSet.END, lastIconAddedId, ConstraintSet.END)
            } else {
                connections.connect(id, ConstraintSet.END, lastIconAddedId, ConstraintSet.START)
            }
            connections.connect(id, ConstraintSet.TOP, R.id.header_background, ConstraintSet.TOP)
            connections.connect(id, ConstraintSet.BOTTOM, R.id.header_background, ConstraintSet.BOTTOM)
        }
        layoutParams = (layoutParams as MarginLayoutParams).apply {
            marginEnd = (if (isFirst && hasFakeIcon) R.dimen.dimen_64dp else R.dimen.dimen_24dp).toDimension()
            setPadding(
                R.dimen.dimen_8dp.toDimension(),
                R.dimen.dimen_8dp.toDimension(),
                R.dimen.dimen_8dp.toDimension(),
                R.dimen.dimen_8dp.toDimension()
            )
        }
        setOnClickListener { option.onClick.invoke() }
        lastIconAddedId = id
    }

    private fun Int.toDimension() = context.dimension(this)
}
