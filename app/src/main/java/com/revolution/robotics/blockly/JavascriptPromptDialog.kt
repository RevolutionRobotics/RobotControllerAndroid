package com.revolution.robotics.blockly

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.databinding.ViewDataBinding
import com.revolution.robotics.BaseDialog
import com.revolution.robotics.blockly.dialogs.BlocklyDialogInterface
import com.revolution.robotics.blockly.utils.BlocklyResultHolder
import com.revolution.robotics.core.extensions.onPropertyChanged
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.BlocklyDialogCoreBinding
import org.kodein.di.KodeinAware
import org.kodein.di.LateInitKodein
import org.kodein.di.erased.instance

abstract class JavascriptPromptDialog<B : ViewDataBinding>(@LayoutRes private val layoutResourceId: Int) :
    BaseDialog(), BlocklyDialogInterface {

    lateinit var binding: B

    override val titleResource = ObservableInt()
    override val title = ObservableField("")

    protected val kodein = LateInitKodein()
    protected val blocklyResultHolder: BlocklyResultHolder by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kodein.baseKodein = (requireContext().applicationContext as KodeinAware).kodein
        titleResource.onPropertyChanged { title.set(resourceResolver.string(it)) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        BlocklyDialogCoreBinding.inflate(inflater, container, false).apply {
            background = dialogBackgroundConfig.create()
            dialogInterface = this@JavascriptPromptDialog
            binding = DataBindingUtil.inflate(inflater, layoutResourceId, this.container, true)
        }.root

    override fun onDismiss(dialog: DialogInterface?) {
        blocklyResultHolder.result?.cancel()
        super.onDismiss(dialog)
    }
}
