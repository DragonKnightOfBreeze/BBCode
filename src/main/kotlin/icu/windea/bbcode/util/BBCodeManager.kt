@file:Suppress("unused")

package icu.windea.bbcode.util

import com.intellij.openapi.util.*
import com.intellij.psi.*
import com.intellij.psi.util.*
import icu.windea.bbcode.*
import icu.windea.bbcode.psi.*
import icu.windea.bbcode.psi.BBCodeTypes.*

object BBCodeManager {
    fun getStartTagNameElement(element: BBCodeTag): PsiElement? {
        element.children().find { it.elementType == TAG_NAME && it.prevSibling?.elementType == TAG_PREFIX_START }
            ?.let { return it }
        return null
    }

    fun getEndTagNameElement(element: BBCodeTag): PsiElement? {
        element.children(forward = false).find { it.elementType == TAG_NAME && it.prevSibling?.elementType == TAG_SUFFIX_START }
            ?.let { return it }
        element.children(forward = false).find { it is PsiErrorElement }
            ?.children()?.find { it.elementType == TAG_NAME && it.prevSibling?.elementType == TAG_SUFFIX_START }
            ?.let { return it }
        return null
    }

    fun getAttributeNameElement(element: BBCodeAttribute): PsiElement? {
        return element.children().find { it.elementType == ATTRIBUTE_NAME }
    }

    fun isStartTagNameToken(element: PsiElement): Boolean {
        return element.elementType.let { it == TAG_PREFIX_START || it == TAG_NAME || it == TAG_PREFIX_END }
    }

    fun isEndTagNameToken(element: PsiElement): Boolean {
        return element.elementType.let { it == TAG_SUFFIX_START || it == TAG_NAME || it == TAG_SUFFIX_END }
    }

    fun isInlineTag(element: BBCodeTag): Boolean {
        return element.children().find { it.elementType == EMPTY_TAG_PREFIX_END } != null
    }

    fun getSimpleAttributeRange(element: BBCodeTag): TextRange? {
        val start = element.children().find { it.elementType == EQUAL_SIGN }?.startOffset ?: return null
        val end = element.children().find { it.elementType == ATTRIBUTE_VALUE }?.endOffset ?: return null
        return TextRange.create(start, end)
    }
}
