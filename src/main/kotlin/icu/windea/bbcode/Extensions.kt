@file:Suppress("unused", "NOTHING_TO_INLINE")

package icu.windea.bbcode

import java.io.*
import java.net.*

inline fun <reified T> Any?.cast(): T = this as T

inline fun <reified T> Any?.castOrNull(): T? = this as? T

inline fun <T : CharSequence> T.orNull() = this.takeIf { it.isNotEmpty() }

inline fun <T> Array<T>?.orNull() = this?.takeIf { it.isNotEmpty() }

inline fun <T : Collection<*>> T?.orNull() = this?.takeIf { it.isNotEmpty() }

inline fun <T : Map<*, *>> T?.orNull() = this?.takeIf { it.isNotEmpty() }

fun Collection<String>.toCommaDelimitedString(): String {
    val input = this
    return if (input.isEmpty()) "" else input.joinToString(",")
}

fun String.toCommaDelimitedStringList(destination: MutableList<String> = mutableListOf()): MutableList<String> {
    return this.split(',').mapNotNullTo(destination) { it.trim().orNull() }
}

fun String.toCommaDelimitedStringSet(destination: MutableSet<String> = mutableSetOf()): MutableSet<String> {
    return this.split(',').mapNotNullTo(destination) { it.trim().orNull() }
}

fun String.toFileUrl(): URL = File(this).toURI().toURL()

fun String.toClasspathUrl(locationClass: Class<*>): URL = locationClass.getResource(this)!!
