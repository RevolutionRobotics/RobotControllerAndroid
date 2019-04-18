package com.revolution.robotics.robots.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.revolution.robotics.databinding.ItemRobotsBinding

class RobotsPagerAdapter(private val context: Context) : PagerAdapter() {
    private val mViews: ArrayList<View?> = ArrayList()
    private val mData: MutableList<RobotsAdapterItem> = mutableListOf()

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    fun setItems(itemList: List<RobotsAdapterItem>) {
        itemList.forEach(::addItem)
        notifyDataSetChanged()
    }

    private fun addItem(item: RobotsAdapterItem) {
        mViews.add(null)
        mData.add(item)
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val binding = ItemRobotsBinding.inflate(LayoutInflater.from(context), collection, false)
        collection.addView(binding.root)
        binding.viewModel = mData[position]
        mViews[position] = binding.root
        return binding.root
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
        mViews[position] = null
    }

    override fun getCount(): Int = mData.size
}
