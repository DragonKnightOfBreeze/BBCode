package icu.windea.bbcode.lang

import com.intellij.openapi.util.*
import com.intellij.psi.*
import com.intellij.psi.util.*
import icu.windea.bbcode.*
import icu.windea.bbcode.psi.*

@Suppress("unused")
object BBCodeManager {
    fun getStartTagNameElement(element: BBCodeTag): PsiElement? {
        element.children().find { it.elementType == BBCodeTypes.TAG_NAME && it.prevSibling?.elementType == BBCodeTypes.TAG_PREFIX_START }
            ?.let { return it }
        return null
    }

    fun getEndTagNameElement(element: BBCodeTag): PsiElement? {
        element.children(forward = false).find { it.elementType == BBCodeTypes.TAG_NAME && it.prevSibling?.elementType == BBCodeTypes.TAG_SUFFIX_START }
            ?.let { return it }
        element.children(forward = false).find { it is PsiErrorElement }
            ?.children()?.find { it.elementType == BBCodeTypes.TAG_NAME && it.prevSibling?.elementType == BBCodeTypes.TAG_SUFFIX_START }
            ?.let { return it }
        return null
    }

    fun getAttributeNameElement(element: BBCodeAttribute): PsiElement? {
        return element.children().find { it.elementType == BBCodeTypes.ATTRIBUTE_NAME }
    }

    fun isTagPrefixToken(element: PsiElement): Boolean {
        return element.elementType.let { it == BBCodeTypes.TAG_PREFIX_START || it == BBCodeTypes.TAG_NAME || it == BBCodeTypes.TAG_PREFIX_END || it == BBCodeTypes.EMPTY_TAG_PREFIX_END }
    }

    fun isTagSuffixToken(element: PsiElement): Boolean {
        return element.elementType.let { it == BBCodeTypes.TAG_SUFFIX_START || it == BBCodeTypes.TAG_NAME || it == BBCodeTypes.TAG_SUFFIX_END }
    }

    fun isEmptyTag(element: BBCodeTag): Boolean {
        return element.children().find { it.elementType == BBCodeTypes.EMPTY_TAG_PREFIX_END } != null
    }

    fun getSimpleAttributeRange(element: BBCodeTag): TextRange? {
        val start = element.children().find { it.elementType == BBCodeTypes.EQUAL_SIGN }?.startOffset ?: return null
        val end = element.children().find { it.elementType == BBCodeTypes.ATTRIBUTE_VALUE }?.endOffset ?: return null
        return TextRange.create(start, end)
    }
}
