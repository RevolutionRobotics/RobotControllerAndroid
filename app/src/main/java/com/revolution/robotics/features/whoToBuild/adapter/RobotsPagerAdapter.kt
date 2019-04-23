package com.revolution.robotics.features.whoToBuild.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.google.firebase.storage.StorageReference
import com.revolution.robotics.databinding.ItemRobotsBinding

class RobotsPagerAdapter : PagerAdapter() {
    private val mViews: ArrayList<View?> = ArrayList()
    private val mData: MutableList<RobotItem> = mutableListOf()

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    fun setItems(itemList: List<RobotItem>) {
        itemList.forEach(::addItem)
        notifyDataSetChanged()
    }

    private fun addItem(item: RobotItem) {
        mViews.add(null)
        mData.add(item)
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val binding = ItemRobotsBinding.inflate(LayoutInflater.from(collection.context), collection, false)
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
