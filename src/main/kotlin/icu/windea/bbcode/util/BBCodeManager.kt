package icu.windea.bbcode.util

import com.intellij.psi.*
import com.intellij.psi.util.*
import icu.windea.bbcode.*
import icu.windea.bbcode.psi.*
import icu.windea.bbcode.psi.BBCodeTypes.*

object BBCodeManager {
    fun getStartTagNameElement(tag: BBCodeTag): PsiElement? {
        tag.children().find { it.elementType == TAG_NAME && it.prevSibling?.elementType == TAG_PREFIX_START }
            ?.let { return it }
        return null
    }

    fun getEndTagNameElement(tag: BBCodeTag): PsiElement? {
        tag.children(forward = false).find { it.elementType == TAG_NAME && it.prevSibling?.elementType == TAG_SUFFIX_START }
            ?.let { return it }
        tag.children(forward = false).find { it is PsiErrorElement }
            ?.children()?.find { it.elementType == TAG_NAME && it.prevSibling?.elementType == TAG_SUFFIX_START }
            ?.let { return it }
        return null
    }

    fun isStartTagNameToken(element: PsiElement): Boolean {
        return element.elementType.let { it == TAG_PREFIX_START || it == TAG_NAME || it == TAG_PREFIX_END }
    }

    fun isEndTagNameToken(element: PsiElement): Boolean {
        return element.elementType.let { it == TAG_SUFFIX_START || it == TAG_NAME || it == TAG_SUFFIX_END }
    }
}
