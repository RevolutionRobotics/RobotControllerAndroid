package com.revolution.robotics.features.coding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.blockly.DialogFactory
import com.revolution.robotics.blockly.utils.JavascriptResultHandler
import com.revolution.robotics.databinding.FragmentCodingBinding
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import org.kodein.di.erased.instance

class CodingFragment : BaseFragment<FragmentCodingBinding, CodingViewModel>(R.layout.fragment_coding) {

    companion object {
        private const val BLOCKLY_LOCATION = "file:///android_asset/blockly/webview.html"
    }

    override val viewModelClass: Class<CodingViewModel> = CodingViewModel::class.java

    private val javascriptResultHandler: JavascriptResultHandler by kodein.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            binding?.buttonBackground = ChippedBoxConfig.Builder()
                .chipSize(R.dimen.dimen_12dp)
                .backgroundColorResource(R.color.grey_28)
                .create()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.viewBlockly?.init(BLOCKLY_LOCATION, DialogFactory(javascriptResultHandler, fragmentManager))
    }
}
