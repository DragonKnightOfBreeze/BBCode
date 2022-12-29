package com.windea.plugin.idea.bbcode

import com.intellij.*
import org.jetbrains.annotations.*
import java.util.function.*

@NonNls
private const val BUNDLE = "messages.BBCodeBundle"

object BBCodeBundle: DynamicBundle(BUNDLE) {
	fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any): String {
		return getMessage(key, *params)
	}
}

