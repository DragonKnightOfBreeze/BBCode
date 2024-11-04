@file:Suppress("unused")

package icu.windea.bbcode

import java.io.*
import java.net.*

fun String.toFileUrl(): URL = File(this).toURI().toURL()

fun String.toClasspathUrl(locationClass: Class<*>): URL = locationClass.getResource(this)!!
