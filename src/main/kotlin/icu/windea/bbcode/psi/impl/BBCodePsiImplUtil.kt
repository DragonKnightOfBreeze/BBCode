package icu.windea.bbcode.psi.impl

import com.intellij.openapi.util.Iconable
import com.intellij.psi.PsiElement
import com.intellij.refactoring.suggested.startOffset
import com.intellij.util.IncorrectOperationException
import icons.BBCodeIcons
import icu.windea.bbcode.psi.*
import javax.swing.Icon

object BBCodePsiImplUtil {
    //region BBCodeTag
    @JvmStatic
    fun getName(element: BBCodeTag): String? {
        return element.tagPrefix.tagName?.text
    }

    @JvmStatic
    fun setName(element: BBCodeTag, name: String): PsiElement {
        throw IncorrectOperationException()
    }

    @JvmStatic
    fun getNameIdentifier(element: BBCodeTag): PsiElement? {
        return element.tagPrefix.tagName
    }

    @JvmStatic
    fun getTextOffset(element: BBCodeTag): Int {
        return element.tagPrefix.tagName?.startOffset ?: (element.tagPrefix.startOffset + 1)
    }

    @JvmStatic
    fun getIcon(element: BBCodeTag, @Iconable.IconFlags flags: Int): Icon {
        return BBCodeIcons.Tag
    }
    //endregion

    //region BBCodeAttribute
    @JvmStatic
    fun getName(element: BBCodeAttribute): String? {
        return element.attributeName.text
    }

    @JvmStatic
    fun setName(element: BBCodeAttribute, name: String): PsiElement {
        throw IncorrectOperationException()
    }

    @JvmStatic
    fun getNameIdentifier(element: BBCodeAttribute): PsiElement {
        return element.attributeName
    }

    @JvmStatic
    fun getTextOffset(element: BBCodeAttribute): Int {
        return element.attributeName.startOffset
    }

    @JvmStatic
    fun getValue(element: BBCodeAttribute): String? {
        return element.attributeValue?.text
    }
    //endregion

    //region BBCodeText
    @JvmStatic
    fun getValue(element: BBCodeText): String? {
        return element.textToken.text
    }
    //endregion
}
