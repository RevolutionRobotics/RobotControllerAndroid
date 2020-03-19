package com.revolution.robotics.core.domain.remote

import com.revolution.robotics.core.domain.ButtonMapping

@Suppress("DataClassContainsFunctions")
data class Controller(
    var type: String? = null,
    var backgroundPrograms: List<@JvmSuppressWildcards ProgramBinding> = emptyList(),
    var buttons: ButtonMapping? = null,
    var drivetrainPriority: Long? = null
) {

    companion object {
        const val TYPE_GAMER = "gamer"
        const val TYPE_MULTITASKER = "multiTasker"
        const val TYPE_DRIVER = "driver"
    }

    fun getProgramIds(): List<String> {
        val ids = mutableListOf<String>()
        backgroundPrograms.forEach { binding ->
            binding.program?.let { id ->
                ids.add(id)
            }
        }

        buttons?.apply {
            addProgramIdFromButtonMapping(b1, ids)
            addProgramIdFromButtonMapping(b2, ids)
            addProgramIdFromButtonMapping(b3, ids)
            addProgramIdFromButtonMapping(b4, ids)
            addProgramIdFromButtonMapping(b5, ids)
            addProgramIdFromButtonMapping(b6, ids)
        }
        return ids
    }

    private fun addProgramIdFromButtonMapping(programBinding: ProgramBinding?, ids: MutableList<String>) {
        programBinding?.program?.let {
            ids.add(it)
        }
    }
}
