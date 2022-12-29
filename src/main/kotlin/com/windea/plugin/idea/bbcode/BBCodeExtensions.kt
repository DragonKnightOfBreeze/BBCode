package com.windea.plugin.idea.bbcode

fun String.toUrl(locationClass: Class<*>) = locationClass.getResource(this)!!
