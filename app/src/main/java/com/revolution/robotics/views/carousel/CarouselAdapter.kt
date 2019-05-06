package com.revolution.robotics.views.carousel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.viewpager.widget.PagerAdapter

abstract class CarouselAdapter<T> : PagerAdapter() {
    private val mViews: ArrayList<View?> = ArrayList()
    private val mData: MutableList<T> = mutableListOf()

    abstract fun bindItem(inflater: LayoutInflater, parent: ViewGroup, data: T): ViewDataBinding

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    fun setItems(itemList: List<T>) {
        mViews.clear()
        mData.clear()
        itemList.forEach { item ->
            mViews.add(null)
            mData.add(item)
        }
        notifyDataSetChanged()
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val binding = bindItem(LayoutInflater.from(collection.context), collection, mData[position])
        collection.addView(binding.root)
        mViews[position] = binding.root
        return binding.root
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
        mViews[position] = null
    }

    override fun getCount(): Int = mData.size
}
