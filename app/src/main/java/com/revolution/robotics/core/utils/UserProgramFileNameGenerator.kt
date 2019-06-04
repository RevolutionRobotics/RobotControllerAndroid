package com.revolution.robotics.core.utils

class UserProgramFileNameGenerator {

    fun generatePythonFileName() =
        "${System.currentTimeMillis()}.py"

    fun generateXmlFileName() =
        "${System.currentTimeMillis()}.xml"
}
