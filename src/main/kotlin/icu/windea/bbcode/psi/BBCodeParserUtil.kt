package icu.windea.bbcode.psi

import com.intellij.lang.*
import com.intellij.lang.parser.*
import com.intellij.openapi.util.*
import icu.windea.bbcode.util.*
import java.util.*

@Suppress("UNUSED_PARAMETER")
object BBCodeParserUtil : GeneratedParserUtilBase() {
    object Keys : KeyRegistry() {
        val tagNames by createKey<Deque<String>>(Keys)
    }

    @JvmStatic
    fun pushTagName(b: PsiBuilder, l: Int): Boolean {
        val tagNames = b.getOrCreateUserData(Keys.tagNames) { ArrayDeque() }
        val tagName = b.tokenText ?: return false
        tagNames.push(tagName)
        return true
    }

    @JvmStatic
    fun popTagName(b: PsiBuilder, l: Int): Boolean {
        val tagNames = b.getOrCreateUserData(Keys.tagNames) { ArrayDeque() }
        tagNames.poll()
        return true
    }

    @JvmStatic
    fun checkTagName(b: PsiBuilder, l: Int): Boolean {
        val tagNames = b.getOrCreateUserData(Keys.tagNames) { ArrayDeque() }
        if (b !is Builder) return true
        val tagName = b.tokenText ?: return false
        val lastTagName = tagNames.peek() ?: return false
        if (tagName != lastTagName) return false
        tagNames.poll()
        return true
    }
}
