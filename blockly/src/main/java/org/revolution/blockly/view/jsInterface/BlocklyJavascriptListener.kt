package org.revolution.blockly.view.jsInterface

interface BlocklyJavascriptListener {

    fun onPythonProgramSaved(file: String)

    fun onXMLProgramSaved(file: String)

    fun onVariablesExported(variables: String)
}