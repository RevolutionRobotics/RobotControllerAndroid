package com.revolution.robotics.features.configure

import androidx.annotation.ColorRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class ConfigureViewModel(private val presenter: ConfigureMvp.Presenter) : ViewModel() {

    var connectionTextColorRes: MutableLiveData<Int> = MutableLiveData()
    var controllerTextColorRes: MutableLiveData<Int> = MutableLiveData()
    var connectionBackgroundConfig: MutableLiveData<ChippedBoxConfig> = MutableLiveData()
    var controllerBackgroundConfig: MutableLiveData<ChippedBoxConfig> = MutableLiveData()

    companion object {
        @ColorRes
        private const val activeTextColorRes = R.color.white
        @ColorRes
        private const val activeButtonBackground = R.color.grey_28
        @ColorRes
        private const val activeButtonBorder = R.color.white

        private const val inactiveTextColorRes = R.color.grey_8e
        @ColorRes
        private const val inactiveButtonBackground = R.color.grey_28
        @ColorRes
        private const val inactiveButtonBorder = R.color.grey_28
    }

    private val connectionsButtonConfigBuilder = ChippedBoxConfig.Builder()
        .chipSize(R.dimen.dimen_10dp)
        .chipTopLeft(true)
        .chipBottomLeft(true)
        .chipTopRight(false)
        .chipBottomRight(false)

    private val controllerButtonConfigBuilder = ChippedBoxConfig.Builder()
        .chipSize(R.dimen.dimen_10dp)
        .chipTopLeft(false)
        .chipBottomLeft(false)
        .chipTopRight(true)
        .chipBottomRight(true)

    fun setScreen(nextScreen: ConfigurationTabs) {
        if (nextScreen == ConfigurationTabs.CONNECTIONS) {
            connectionBackgroundConfig.value = connectionsButtonConfigBuilder.toActive().create()
            controllerBackgroundConfig.value = controllerButtonConfigBuilder.toInactive().create()
            connectionTextColorRes.value = activeTextColorRes
            controllerTextColorRes.value = inactiveTextColorRes
        } else {
            connectionBackgroundConfig.value = connectionsButtonConfigBuilder.toInactive().create()
            controllerBackgroundConfig.value = controllerButtonConfigBuilder.toActive().create()
            connectionTextColorRes.value = inactiveTextColorRes
            controllerTextColorRes.value = activeTextColorRes
        }
    }

    private fun ChippedBoxConfig.Builder.toActive(): ChippedBoxConfig.Builder = apply {
        backgroundColorResource(activeButtonBackground)
        borderColorResource(activeButtonBorder)
    }

    private fun ChippedBoxConfig.Builder.toInactive(): ChippedBoxConfig.Builder = apply {
        backgroundColorResource(inactiveButtonBackground)
        borderColorResource(inactiveButtonBorder)
    }

    fun onConnectionsSelected() = presenter.onConnectionsTabSelected()

    fun onControllerSelected() = presenter.onControllerTabSelected()

    fun onBluetoothClicked() = presenter.onBluetoothClicked()
}
