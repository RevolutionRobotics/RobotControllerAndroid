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
import com.revolution.robotics.analytics.Reporter
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.LateInitKodein
import org.kodein.di.TT
import org.kodein.di.direct
import org.kodein.di.erased.instance

abstract class BaseFragment<B : ViewDataBinding, V : ViewModel>(@LayoutRes private val layoutResourceId: Int) :
    Fragment() {

    protected var binding: B? = null
    protected var viewModel: V? = null
    protected var kodein = LateInitKodein()
    protected val reporter: Reporter by kodein.instance()

    protected abstract val viewModelClass: Class<V>
    protected open val screen: Reporter.Screen? = null

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

    override fun onResume() {
        super.onResume()
        if (screen != null) {
            activity?.let { reporter.setScreen(it, screen!!) }
        }
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
