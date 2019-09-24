package com.revolution.robotics.features.onboarding.carby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.revolution.robotics.R
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentHaveYouBuiltCarbyBinding
import org.kodein.di.KodeinAware
import org.kodein.di.LateInitKodein
import org.kodein.di.erased.instance

class HaveYouBuiltCarbyFragment: Fragment() {

    private var kodein = LateInitKodein()
    private val navigator: Navigator by kodein.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kodein.baseKodein = (requireContext().applicationContext as KodeinAware).kodein
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentHaveYouBuiltCarbyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_have_you_built_carby, container, false)
        return binding.root
    }
}