package icu.windea.bbcode

fun String.toUrl(locationClass: Class<*>) = locationClass.getResource(this)!!
