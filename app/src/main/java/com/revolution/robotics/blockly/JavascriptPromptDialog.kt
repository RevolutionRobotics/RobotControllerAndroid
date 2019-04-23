package com.revolution.robotics.blockly

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.revolution.robotics.BaseDialog
import com.revolution.robotics.blockly.dialogs.BlocklyDialogInterface
import com.revolution.robotics.blockly.utils.JavascriptResultHandler
import com.revolution.robotics.databinding.BlocklyDialogCoreBinding
import org.kodein.di.KodeinAware
import org.kodein.di.LateInitKodein
import org.kodein.di.erased.instance

abstract class JavascriptPromptDialog<B : ViewDataBinding>(@LayoutRes private val layoutResourceId: Int) :
    BaseDialog(), BlocklyDialogInterface {

    lateinit var binding: B

    private val kodein = LateInitKodein()
    private val javascriptResultHandler: JavascriptResultHandler by kodein.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kodein.baseKodein = (requireContext().applicationContext as KodeinAware).kodein
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val core = BlocklyDialogCoreBinding.inflate(inflater, container, false)
        core.dialogInterface = this
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, core.container, true)
        return core.root
    }

    override fun onDismiss(dialog: DialogInterface?) {
        javascriptResultHandler.cancelResult()
        super.onDismiss(dialog)
    }

    override fun confirm() {
        javascriptResultHandler.confirmResult(getResult())
        dismiss()
    }

    abstract fun getResult(): String
}