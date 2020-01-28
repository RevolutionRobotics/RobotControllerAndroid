package com.revolution.robotics.core.utils

data class VersionNumber(
    val major: Int,
    val minor: Int,
    val patch: Int
) : Comparable<VersionNumber> {

    override fun compareTo(other: VersionNumber): Int {
        if (this.major > other.major) return 1
        if (this.major < other.major) return -1
        if (this.minor > other.minor) return 1
        if (this.minor < other.minor) return -1
        if (this.patch > other.patch) return 1
        if (this.patch < other.patch) return -1
        return 0
    }

    companion object {
        private const val DELIMITER = "."

        fun parse(versionString: String): VersionNumber {
            val parts = versionString.split(DELIMITER)
            return when (parts.size) {
                1 -> VersionNumber(parts[0].toInt(), 0, 0)
                2 -> VersionNumber(parts[0].toInt(), parts[1].toInt(), 0)
                3 -> VersionNumber(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
                else -> {
                    throw IllegalArgumentException("Version should have 1-3 parts separated with '.'")
                }
            }
        }
    }
}
