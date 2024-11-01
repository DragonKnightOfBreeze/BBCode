package icu.windea.bbcode

import com.intellij.*
import org.jetbrains.annotations.*

@NonNls
private const val BUNDLE = "messages.BBCodeBundle"

object BBCodeBundle : DynamicBundle(BUNDLE) {
    fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any): String {
        return getMessage(key, *params)
    }
}
