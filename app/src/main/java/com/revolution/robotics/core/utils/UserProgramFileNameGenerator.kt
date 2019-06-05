package com.revolution.robotics.core.utils

import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider

class UserProgramFileNameGenerator(private val applicationContextProvider: ApplicationContextProvider) {

    fun generatePythonFileName(includeFolder: Boolean = false) =
        if (includeFolder) {
            "${applicationContextProvider.applicationContext.filesDir}/${System.currentTimeMillis()}.py"
        } else {
            "${System.currentTimeMillis()}.py"
        }

    fun generateXmlFileName(includeFolder: Boolean = false) =
        if (includeFolder) {
            "${applicationContextProvider.applicationContext.filesDir}/${System.currentTimeMillis()}.xml"
        } else {
            "${System.currentTimeMillis()}.xml"
        }
}
