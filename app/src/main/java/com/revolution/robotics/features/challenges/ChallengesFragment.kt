package com.revolution.robotics.features.challenges

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.blockly.AudioHandlerJavascriptInterface
import com.revolution.robotics.databinding.FragmentChallengesBinding
import com.revolution.robotics.blockly.DialogFactory
import com.revolution.robotics.blockly.utils.JavascriptResultHandler
import org.kodein.di.erased.instance

class ChallengesFragment : BaseFragment<FragmentChallengesBinding, ChallengesViewModel>(R.layout.fragment_challenges) {

    override val viewModelClass: Class<ChallengesViewModel> = ChallengesViewModel::class.java

    private val javascriptResultHandler: JavascriptResultHandler by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.viewBlockly?.apply {
            init("file:///android_asset/play_sound.html", DialogFactory(javascriptResultHandler, fragmentManager))
            addJavascriptInterface(AudioHandlerJavascriptInterface(context), "Native")
        }
    }
}
