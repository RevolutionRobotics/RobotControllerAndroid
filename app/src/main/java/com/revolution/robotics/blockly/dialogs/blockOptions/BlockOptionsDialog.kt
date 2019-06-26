package com.revolution.robotics.blockly.dialogs.blockOptions

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.revolution.robotics.R
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.BlocklyDialogBlockOptionsBinding
import com.revolution.robotics.views.ChippedEditTextViewModel
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogButtonHelper
import org.kodein.di.erased.instance
import org.revolution.blockly.view.result.BlockOptionResult

class BlockOptionsDialog :
    JavascriptPromptDialog<BlocklyDialogBlockOptionsBinding>(R.layout.blockly_dialog_block_options) {

    companion object {
        private const val COMMENT_MAX_LENGTH = 80

        private var Bundle.title by BundleArgumentDelegate.String("title")
        private var Bundle.comment by BundleArgumentDelegate.StringNullable("comment")

        fun newInstance(title: String, comment: String? = null) = BlockOptionsDialog().withArguments { bundle ->
            bundle.title = title
            bundle.comment = comment
        }
    }

    override val hasCloseButton = true
    override val hasTitle = true

    private val resourceResolver: ResourceResolver by kodein.instance()
    private val navigator: Navigator by kodein.instance()
    private val dialogButtonHelper = DialogButtonHelper()

    private var wasResultConfirmed = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            dialogButtonHelper.createButtons(binding.buttonContainer, setOf(
                DialogButton(R.string.dialog_block_options_delete, R.drawable.ic_delete) {
                    (blocklyResultHolder.result as? BlockOptionResult)?.confirmDelete()
                    wasResultConfirmed = true
                    dismissAllowingStateLoss()
                },
                DialogButton(R.string.dialog_block_options_help, R.drawable.ic_community) {
                    dismissAllowingStateLoss()
                    navigator.navigate(R.id.toCommunity)
                },
                DialogButton(R.string.dialog_block_options_duplicate, R.drawable.ic_copy, true) {
                    (blocklyResultHolder.result as? BlockOptionResult)?.confirmDuplicate()
                    wasResultConfirmed = true
                    dismissAllowingStateLoss()
                }
            ))
            title.set(arguments?.title)
            binding.comment.setViewModel(
                ChippedEditTextViewModel(
                    title = resourceResolver.string(R.string.dialog_block_options_comment_title),
                    hint = resourceResolver.string(R.string.dialog_block_options_comment_description),
                    text = arguments?.comment,
                    titleColor = R.color.white,
                    hintColor = R.color.grey_8e,
                    borderColor = R.color.grey_8e,
                    backgroundColor = R.color.grey_28,
                    textMaxLength = COMMENT_MAX_LENGTH
                )
            )
        }

    override fun onDismiss(dialog: DialogInterface?) {
        if (!wasResultConfirmed) {
            (blocklyResultHolder.result as? BlockOptionResult)?.confirmComment(binding.comment.getContent())
        }
    }
}
