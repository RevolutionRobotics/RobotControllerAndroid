package org.revolution.blockly.view.jsInterface

interface SaveBlocklyListener {

    fun onPythonProgramSaved(file: String)

    fun onXMLProgramSaved(file: String)

    fun onVariablesExported(variables: String)
}
