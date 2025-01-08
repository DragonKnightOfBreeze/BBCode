package icu.windea.bbcode

object BBCodeConstants {
    val locationClass = BBCodeBundle::class.java

    val sampleText = "/samples/Sample.bbcode".toClasspathUrl(locationClass).readText()

    const val dummyIdentifier = "windea"
}
