package com.revolution.robotics.features.build.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.revolution.robotics.databinding.ItemBuildStepBinding


class BuildRobotAdapter : PagerAdapter() {

    var buildSteps: List<BuildStepViewModel> = emptyList()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val binding = ItemBuildStepBinding.inflate(inflater, container, false)
        binding.viewModel = buildSteps[position]
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = buildSteps.size
}