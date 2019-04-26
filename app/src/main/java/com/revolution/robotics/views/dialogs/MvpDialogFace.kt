package com.revolution.robotics.views.dialogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
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

@Suppress("OptionalUnit")
abstract class MvpDialogFace<B : ViewDataBinding, V : ViewModel>(@LayoutRes private val layoutResourceId: Int) :
    DialogFace<B>(layoutResourceId) {

    protected var viewModel: V? = null
    protected var kodein = LateInitKodein()

    protected abstract val viewModelClass: Class<V>

    override fun activate(fragment: Fragment, inflater: LayoutInflater, container: ViewGroup): View? {
        val view = super.activate(fragment, inflater, container)
        kodein.baseKodein = (fragment.context as KodeinAware).kodein
        viewModel = ViewModelProviders.of(fragment, getViewModelFactory()).get(viewModelClass)
        binding?.let { binding ->
            binding.lifecycleOwner = fragment
            binding.setVariable(BR.viewModel, viewModel)
        }
        onViewCreated(fragment, container)
        return view
    }

    open fun onViewCreated(fragment: Fragment, container: ViewGroup) = Unit

    open fun onDestroyView() = Unit

    override fun releaseFace() {
        onDestroyView()
        viewModel = null
        super.releaseFace()
    }

    private fun getViewModelFactory(): ViewModelProvider.Factory? = KodeinViewModelFactory(kodein)

    class KodeinViewModelFactory(val kodein: Kodein) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            kodein.direct.Instance(TT(modelClass))
    }
}
