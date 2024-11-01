package icu.windea.bbcode

import java.io.File
import java.net.URL

fun String.toFileUrl(): URL = File(this).toURI().toURL()

fun String.toClasspathUrl(locationClass: Class<*>): URL = locationClass.getResource(this)!!
