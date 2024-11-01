package icu.windea.bbcode

object BBCodeConstants {
    val locationClass = BBCodeBundle::class.java

    val sampleText = "/samples/BBCode.txt".toClasspathUrl(locationClass).readText()
}
