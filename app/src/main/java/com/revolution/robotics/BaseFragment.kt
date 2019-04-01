package com.revolution.robotics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

abstract class BaseFragment<B : ViewDataBinding, V : ViewModel>(@LayoutRes private val layoutResourceId: Int)
    : Fragment() {

    protected var binding: B? = null
    protected var viewModel: V? = null

    protected abstract val viewModelClass: Class<V>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, getViewModelFactory()).get(viewModelClass)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding?.let { binding ->
            binding.lifecycleOwner = this
            binding.setVariable(BR.viewModel, viewModel)
        }
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // Override if you have a custom ViewModelFactory
    open fun getViewModelFactory(): ViewModelProvider.Factory? = null
}
