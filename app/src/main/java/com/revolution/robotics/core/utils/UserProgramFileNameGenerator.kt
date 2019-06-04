package com.revolution.robotics.core.utils

import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider

class UserProgramFileNameGenerator(private val applicationContextProvider: ApplicationContextProvider) {

    fun generatePythonFileName() =
        "${applicationContextProvider.applicationContext.filesDir.path}/${System.currentTimeMillis()}.py"

    fun generateXmlFileName() =
        "${applicationContextProvider.applicationContext.filesDir.path}/${System.currentTimeMillis()}.xml"
}
