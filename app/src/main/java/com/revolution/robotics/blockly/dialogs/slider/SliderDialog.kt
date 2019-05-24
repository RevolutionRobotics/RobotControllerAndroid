package com.revolution.robotics.blockly.dialogs.slider

/*class SliderDialog : JavascriptPromptDialog<BlocklyDialogSliderBinding>(R.layout.blockly_dialog_slider) {

    companion object {

        private var Bundle.options by BundleArgumentDelegate.Parcelable<DialogFactory.SliderOptions>("options")

        fun newInstance(options: DialogFactory.SliderOptions) =
            SliderDialog().withArguments { bundle ->
                bundle.options = options
            }
    }

    lateinit var viewModel: SliderDialogViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.options?.let { options ->
            viewModel = SliderDialogViewModel(options)
        }
        binding.viewModel = viewModel
    }

    override fun getResult() = "${binding.slider.progress + viewModel.minValue}"
}
*/