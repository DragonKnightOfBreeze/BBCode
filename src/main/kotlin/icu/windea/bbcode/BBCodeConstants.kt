package icu.windea.bbcode

object BBCodeConstants {
    val locationClass = BBCodeBundle::class.java

    val sampleText = "/samples/sample.bbcode".toClasspathUrl(locationClass).readText()
}
