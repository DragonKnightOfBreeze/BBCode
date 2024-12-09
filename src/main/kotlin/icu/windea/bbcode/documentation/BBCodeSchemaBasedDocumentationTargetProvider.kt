package icu.windea.bbcode.documentation

import com.intellij.platform.backend.documentation.*
import com.intellij.psi.*
import icu.windea.bbcode.psi.*

class BBCodeSchemaBasedDocumentationTargetProvider: PsiDocumentationTargetProvider {
    override fun documentationTarget(element: PsiElement, originalElement: PsiElement?): DocumentationTarget? {
        if(element !is BBCodeTag && element !is BBCodeAttribute) return null
        return BBCodeSchemaBasedDocumentationTarget(element)
    }
}
