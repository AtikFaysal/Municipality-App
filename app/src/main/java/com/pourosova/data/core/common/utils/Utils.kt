package co.jatri.common.utils

object Utils {
    fun getBuildTypeName(buildType: String) = when (buildType) {
        "debug" -> "Dev"
        "qa" -> "QA"
        "release" -> "Live"
        else -> "Unknown"
    }
}