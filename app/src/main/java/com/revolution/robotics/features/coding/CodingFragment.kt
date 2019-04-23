package com.revolution.robotics.features.coding

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.blockly.DialogFactory
import com.revolution.robotics.blockly.utils.JavascriptResultHandler
import com.revolution.robotics.databinding.FragmentCodingBinding
import org.kodein.di.erased.instance

class CodingFragment : BaseFragment<FragmentCodingBinding, CodingViewModel>(R.layout.fragment_coding) {

    override val viewModelClass: Class<CodingViewModel> = CodingViewModel::class.java

    private val javascriptResultHandler: JavascriptResultHandler by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.viewBlockly?.init(
            "file:///android_asset/blockly/webview.html",
            DialogFactory(javascriptResultHandler, fragmentManager)
        )
    }
}
