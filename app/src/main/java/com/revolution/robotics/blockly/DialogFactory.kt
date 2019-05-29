package com.revolution.robotics.blockly

import android.webkit.JsPromptResult
import androidx.fragment.app.FragmentManager
import com.revolution.robotics.blockly.dialogs.directionSelector.DirectionSelectorDialog
import com.revolution.robotics.blockly.utils.JavascriptResultHandler
import org.revolution.blockly.view.DialogFactory

// TODO remove this suppress
@Suppress("UnusedPrivateMember")
class DialogFactory(
    private val javascriptResultHandler: JavascriptResultHandler,
    private val fragmentManager: FragmentManager?
) : DialogFactory {

    override fun showDirectionSelectorDialog(result: JsPromptResult) {
        javascriptResultHandler.registerResult(result)
        DirectionSelectorDialog.newInstance().show(fragmentManager)
    }
}
