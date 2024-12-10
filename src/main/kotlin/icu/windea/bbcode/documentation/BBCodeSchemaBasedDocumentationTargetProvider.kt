package icu.windea.bbcode.documentation

import com.intellij.platform.backend.documentation.*
import com.intellij.psi.*
import com.intellij.psi.util.*
import icu.windea.bbcode.*
import icu.windea.bbcode.psi.*

class BBCodeSchemaBasedDocumentationTargetProvider : PsiDocumentationTargetProvider {
    override fun documentationTarget(element: PsiElement, originalElement: PsiElement?): DocumentationTarget? {
        val elementType = originalElement?.elementType
        if (elementType == null) return null
        val elementForTarget = when {
            elementType == BBCodeTypes.TAG_NAME -> originalElement.parent?.castOrNull<BBCodeTag>()
            elementType == BBCodeTypes.ATTRIBUTE_NAME -> originalElement.parent?.castOrNull<BBCodeAttribute>()
            else -> null
        }
        if (elementForTarget == null) return null
        return BBCodeSchemaBasedDocumentationTarget(element, elementForTarget)
    }
}
