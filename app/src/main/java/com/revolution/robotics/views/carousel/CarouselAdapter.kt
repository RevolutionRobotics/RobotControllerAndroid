package com.revolution.robotics.views.carousel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.viewpager.widget.PagerAdapter

abstract class CarouselAdapter<T> : PagerAdapter() {
    private val mData: MutableList<T> = mutableListOf()

    abstract fun bindItem(inflater: LayoutInflater, parent: ViewGroup, data: T, position: Int): ViewDataBinding

    var selectedPosition = 0

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    fun setItems(itemList: List<T>) {
        mData.clear()
        itemList.forEach { item ->
            mData.add(item)
        }
        notifyDataSetChanged()
    }

    fun removeItems(filter: (T) -> Boolean) {
        val iterator = mData.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (filter.invoke(item)) {
                iterator.remove()
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemPosition(item: Any): Int = POSITION_NONE

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val binding = bindItem(LayoutInflater.from(collection.context), collection, mData[position], position)
        collection.addView(binding.root)

        if (selectedPosition == position) {
            binding.root.scaleX = 1.0f
            binding.root.scaleY = 1.0f
        } else {
            binding.root.scaleX = CarouselPageTransformer.ITEM_SCALE_BASE
            binding.root.scaleY = CarouselPageTransformer.ITEM_SCALE_BASE
        }
        return binding.root
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int = mData.size
}
