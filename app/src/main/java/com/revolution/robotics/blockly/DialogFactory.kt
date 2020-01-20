package com.revolution.robotics.blockly

import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.revolution.robotics.R
import com.revolution.robotics.blockly.dialogs.blockOptions.BlockOptionsDialog
import com.revolution.robotics.blockly.dialogs.colorPicker.ColorPickerDialog
import com.revolution.robotics.blockly.dialogs.confirm.ConfirmDialog
import com.revolution.robotics.blockly.dialogs.dialpad.DialpadDialog
import com.revolution.robotics.blockly.dialogs.directionSelector.Direction
import com.revolution.robotics.blockly.dialogs.directionSelector.DirectionSelectorDialog
import com.revolution.robotics.blockly.dialogs.donutSelector.DonutSelectorDialog
import com.revolution.robotics.blockly.dialogs.lightEffectPicker.LightEffectPickerDialog
import com.revolution.robotics.blockly.dialogs.optionSelector.Option
import com.revolution.robotics.blockly.dialogs.optionSelector.OptionSelectorDialog
import com.revolution.robotics.blockly.dialogs.slider.SliderDialog
import com.revolution.robotics.blockly.dialogs.soundPicker.SoundPickerDialog
import com.revolution.robotics.blockly.dialogs.textInput.TextInputDialog
import com.revolution.robotics.blockly.dialogs.valueOptions.ValueOptionsDialog
import com.revolution.robotics.blockly.dialogs.variableOptions.VariableOptionsDialog
import com.revolution.robotics.blockly.utils.BlocklyResultHolder
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import org.revolutionrobotics.blockly.android.BlocklyOption
import org.revolutionrobotics.blockly.android.BlocklyVariable
import org.revolutionrobotics.blockly.android.view.result.*
import org.revolutionrobotics.blockly.android.view.DialogFactory

@Suppress("TooManyFunctions")
class DialogFactory(
    private val blocklyResultHolder: BlocklyResultHolder,
    private val resourceResolver: ResourceResolver,
    private val fragmentManager: FragmentManager?
) : DialogFactory {

    var userConfiguration: UserConfiguration? = null

    override fun showAlertDialog(message: String, result: ConfirmResult) {
        blocklyResultHolder.result = result
        ConfirmDialog.newInstance(message, false).show(fragmentManager)
    }

    override fun showConfirmationDialog(message: String, result: ConfirmResult) {
        blocklyResultHolder.result = result
        ConfirmDialog.newInstance(message, true).show(fragmentManager)
    }

    override fun showDirectionSelectorDialog(defaultValue: String, result: DirectionResult) {
        blocklyResultHolder.result = result
        DirectionSelectorDialog.newInstance(Direction.getByValue(defaultValue)).show(fragmentManager)
    }

    override fun showDialpad(defaultValue: Double, result: DialpadResult) {
        blocklyResultHolder.result = result
        DialpadDialog.newInstance(defaultValue).show(fragmentManager)
    }

    override fun showSlider(title: String, maxValue: Int, defaultValue: Int, result: SliderResult) {
        blocklyResultHolder.result = result
        SliderDialog.newInstance(title, maxValue, defaultValue).show(fragmentManager)
    }

    override fun showOptionSelector(
        title: String,
        showLabels: Boolean,
        blocklyOptions: List<BlocklyOption>,
        defaultValue: BlocklyOption?,
        result: OptionResult
    ) {
        blocklyResultHolder.result = result
        if (blocklyOptions.size > 6) {
            ValueOptionsDialog.newInstance(
                title,
                defaultValue?.value,
                blocklyOptions.map { it.value },
                R.string.blockly_no_distance_sensor_configured
            ).show(fragmentManager)
        } else {
            val options = blocklyOptions.map { blocklyOption -> blocklyOption.toOption(defaultValue, resourceResolver) }
            OptionSelectorDialog.newInstance(title, options, showLabels).show(fragmentManager)
        }
    }

    override fun showColorPicker(title: String, colors: List<String>, defaultValue: String, result: ColorResult) {
        blocklyResultHolder.result = result
        ColorPickerDialog.newInstance(title, colors, defaultValue).show(fragmentManager)
    }

    override fun showLightEffectPicker(
        title: String,
        defaultValue: String?,
        result: LightEffectResult
    ) {
        blocklyResultHolder.result = result
        LightEffectPickerDialog.newInstance(title, defaultValue).show(fragmentManager)
    }

    override fun showSoundPicker(title: String, defaultValue: String?, result: SoundResult) {
        blocklyResultHolder.result = result
        SoundPickerDialog.newInstance(title, defaultValue).show(fragmentManager)
    }

    override fun showBlockOptionsDialog(title: String, comment: String, result: BlockOptionResult) {
        blocklyResultHolder.result = result
        BlockOptionsDialog.newInstance(title, comment).show(fragmentManager)
    }

    override fun showVariableOptionsDialog(
        title: String,
        defaultValue: BlocklyVariable?,
        variables: List<BlocklyVariable>,
        result: VariableResult
    ) {
        blocklyResultHolder.result = result
        VariableOptionsDialog.newInstance(title, defaultValue, variables).show(fragmentManager)
    }

    override fun showBumperSelector(title: String, subtitle: String?, defaultValue: String?, result: TextResult) {
        blocklyResultHolder.result = result
        ValueOptionsDialog.newInstance(
            title,
            defaultValue,
            userConfiguration?.mappingId?.getSensorNames(Sensor.TYPE_BUMPER)?.filterNotNull() ?: emptyList(),
            R.string.blockly_no_bumper_configured
        ).show(fragmentManager)
    }

    override fun showMotorSelector(title: String, subtitle: String?, defaultValue: String?, result: TextResult) {
        blocklyResultHolder.result = result
        ValueOptionsDialog.newInstance(
            title,
            defaultValue,
            userConfiguration?.mappingId?.getMotorNames(Motor.TYPE_MOTOR)?.filterNotNull() ?: emptyList(),
            R.string.blockly_no_motor_configured
        ).show(fragmentManager)
    }

    override fun showUltrasonicSensorSelector(
        title: String,
        subtitle: String?,
        defaultValue: String?,
        result: TextResult
    ) {
        blocklyResultHolder.result = result
        ValueOptionsDialog.newInstance(
            title,
            defaultValue,
            userConfiguration?.mappingId?.getSensorNames(Sensor.TYPE_ULTRASONIC)?.filterNotNull() ?: emptyList(),
            R.string.blockly_no_distance_sensor_configured
        ).show(fragmentManager)
    }

    override fun showTextInput(title: String, subtitle: String?, defaultValue: String?, result: TextResult) {
        blocklyResultHolder.result = result
        TextInputDialog.newInstance(title, subtitle, defaultValue).show(fragmentManager)
    }

    override fun showDonutSelector(defaultValue: String, isMultiSelection: Boolean, result: DonutResult) {
        blocklyResultHolder.result = result
        val selectionType =
            if (isMultiSelection) {
                DonutSelectorDialog.DonutSelectionType.MULTI
            } else {
                DonutSelectorDialog.DonutSelectionType.SINGLE
            }
        DonutSelectorDialog.newInstance(selectionType, defaultValue).show(fragmentManager)
    }

    private fun BlocklyOption.toOption(defaultOption: BlocklyOption?, resourceResolver: ResourceResolver): Option {
        val resourceName = "ic_blockly_${Regex("[^A-Za-z]").replace(key, "_").toLowerCase()}"
        return Option(resourceResolver.drawableResourceByName(resourceName), value, key, key == defaultOption?.key)
    }
}
