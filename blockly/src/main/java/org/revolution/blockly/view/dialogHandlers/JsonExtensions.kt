package org.revolution.blockly.view.dialogHandlers

import org.json.JSONObject
import org.revolution.blockly.BlocklyOption

fun JSONObject.defaultKey(): String =
    optString("defaultKey", "")

fun JSONObject.defaultOption() =
    options().find { it.key == defaultKey() }

fun JSONObject.defaultInput(fallback: String? = ""): String =
    optString("defaultInput", fallback)

fun JSONObject.defaultValue(fallback: String? = ""): String =
    optString("defaultValue", fallback)

fun JSONObject.maxValue() =
    optString("maximum", "100").toInt()

fun JSONObject.title(): String =
    optString("title", "")

fun JSONObject.comment(): String =
    optString("comment", "")

fun JSONObject.options(): List<BlocklyOption> =
    ArrayList<BlocklyOption>().apply {
        withOptions { option ->
            add(BlocklyOption(option.getString("key"), option.getString("value")))
        }
    }

fun JSONObject.colors(): List<String> =
    ArrayList<String>().apply {
        withOptions { option ->
            add(option.getString("key"))
        }
    }

fun JSONObject.withOptions(callback: (option: JSONObject) -> Unit) {
    val options = getJSONArray("options")
    repeat(options.length()) { index ->
        val option = options.getJSONObject(index)
        callback.invoke(option)
    }
}
