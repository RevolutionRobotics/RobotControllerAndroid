package com.revolution.robotics.core.domain.remote

import com.revolution.robotics.core.domain.ButtonMapping

data class Controller(
    var description: String? = null,
    var id: String? = null,
    var lastModified: Long = 0L,
    var name: String? = null,
    var type: String? = null,
    var backgroundProgramBindings: List<@JvmSuppressWildcards ProgramBinding> = emptyList(),
    var mapping: ButtonMapping? = null
) {

    // TODO @szabolcs.nagy: Use these consts for the enum creation
    companion object {
        const val TYPE_GAMER = "gamer"
        const val TYPE_MULTI_TASKER = "multiTasker"
        const val DRIVER = "driver"
    }

    fun getProgramIds(): List<String> {
        val ids = mutableListOf<String>()
        backgroundProgramBindings.forEach { binding ->
            binding.programId?.let { id ->
                ids.add(id)
            }
        }

        mapping?.apply {
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
        programBinding?.programId?.let {
            ids.add(it)
        }
    }
}
