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
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.LateInitKodein
import org.kodein.di.TT
import org.kodein.di.direct

abstract class BaseFragment<B : ViewDataBinding, V : ViewModel>(@LayoutRes private val layoutResourceId: Int) :
    Fragment() {

    protected var binding: B? = null
    protected var viewModel: V? = null
    protected var kodein = LateInitKodein()

    protected abstract val viewModelClass: Class<V>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kodein.baseKodein = (requireContext().applicationContext as KodeinAware).kodein
        viewModel = ViewModelProviders.of(this, getViewModelFactory()).get(viewModelClass)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding?.let { binding ->
            binding.lifecycleOwner = this.viewLifecycleOwner
            binding.setVariable(BR.viewModel, viewModel)
        }
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun showDialog(baseDialog: BaseDialog) {
        if (!isStateSaved) {
            baseDialog.show(fragmentManager)
        }
    }

    open fun onBackPressed() = false

    // Override if you have a custom ViewModelFactory
    open fun getViewModelFactory(): ViewModelProvider.Factory? = KodeinViewModelFactory(kodein)

    class KodeinViewModelFactory(val kodein: Kodein) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            kodein.direct.Instance(TT(modelClass))
    }
}
