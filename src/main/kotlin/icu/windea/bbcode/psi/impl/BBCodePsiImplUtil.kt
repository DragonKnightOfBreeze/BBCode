package icu.windea.bbcode.psi.impl

import com.intellij.openapi.util.*
import com.intellij.psi.*
import com.intellij.refactoring.suggested.*
import com.intellij.util.*
import icons.*
import icu.windea.bbcode.psi.*
import icu.windea.bbcode.util.*
import javax.swing.*

@Suppress("UNUSED_PARAMETER")
object BBCodePsiImplUtil {
    //region BBCodeTag
    @JvmStatic
    fun getIcon(element: BBCodeTag, @Iconable.IconFlags flags: Int): Icon {
        return BBCodeIcons.Tag
    }

    @JvmStatic
    fun getName(element: BBCodeTag): String {
        return BBCodeManager.getStartTagNameElement(element)?.text.orEmpty()
    }

    @JvmStatic
    fun setName(element: BBCodeTag, name: String): PsiElement {
        throw IncorrectOperationException()
    }

    @JvmStatic
    fun getNameIdentifier(element: BBCodeTag): PsiElement? {
        return BBCodeManager.getStartTagNameElement(element)
    }

    @JvmStatic
    fun getTextOffset(element: BBCodeTag): Int {
        return element.nameIdentifier?.startOffset ?: 1
    }
    
    @JvmStatic
    fun getValue(element: BBCodeTag): String? {
        return element.attributeValue?.text
    }
    //endregion

    //region BBCodeAttribute
    @JvmStatic
    fun getName(element: BBCodeAttribute): String {
        return element.attributeName.text.orEmpty()
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

    //region BBCodeAttributeValue
    @JvmStatic
    fun getValue(element: BBCodeAttributeValue): String {
        return element.attributeValueToken.text
    }
    //endregion

    //region BBCodeText
    @JvmStatic
    fun getValue(element: BBCodeText): String {
        return element.text.trim()
    }
    //endregion

    @JvmStatic
    fun getReferences(element: PsiElement): Array<out PsiReference> {
        return PsiReferenceService.getService().getContributedReferences(element)
    }
}
