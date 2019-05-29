package com.revolution.robotics.features.coding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.blockly.DialogFactory
import com.revolution.robotics.blockly.utils.JavascriptResultHandler
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.databinding.FragmentCodingBinding
import com.revolution.robotics.features.coding.programs.ProgramsDialog
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import org.kodein.di.erased.instance

class CodingFragment : BaseFragment<FragmentCodingBinding, CodingViewModel>(R.layout.fragment_coding), CodingMvp.View,
    DialogEventBus.Listener {

    companion object {
        private const val BLOCKLY_LOCATION = "file:///android_asset/blockly/webview.html"
    }

    override val viewModelClass: Class<CodingViewModel> = CodingViewModel::class.java

    private val presenter: CodingMvp.Presenter by kodein.instance()
    private val javascriptResultHandler: JavascriptResultHandler by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            binding?.buttonBackground = ChippedBoxConfig.Builder()
                .chipSize(R.dimen.dimen_12dp)
                .backgroundColorResource(R.color.grey_28)
                .create()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.viewBlockly?.init(BLOCKLY_LOCATION, DialogFactory(javascriptResultHandler, fragmentManager))
        presenter.register(this, viewModel)
        dialogEventBus.register(this)
    }

    override fun onDestroyView() {
        presenter.unregister()
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    // TODO remove this suppress
    @Suppress("UnusedPrivateMember")
    override fun onDialogEvent(event: DialogEvent) {
        if (event == DialogEvent.LOAD_PROGRAM) {
            val xml = event.extras.getString(ProgramsDialog.EXTRA_PROGRAM_XML)
            // TODO load XML file into blockly
        }
    }
}
