package icu.windea.bbcode.codeInsight.navigation

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler
import com.intellij.openapi.editor.*
import com.intellij.psi.*
import icu.windea.bbcode.lang.schema.*
import icu.windea.bbcode.psi.BBCodeAttribute
import icu.windea.bbcode.psi.BBCodeTag

class BBCodeSchemaBasedGotoDeclarationHandler: GotoDeclarationHandler {
    override fun getGotoDeclarationTargets(sourceElement: PsiElement?, offset: Int, editor: Editor?): Array<PsiElement>? {
        return when {
            sourceElement is BBCodeTag -> {
                val schema = BBCodeSchemaManager.resolveForTag(sourceElement)
                schema?.pointer?.element?.let { arrayOf(it) }
            }
            sourceElement is BBCodeAttribute -> {
                val schema = BBCodeSchemaManager.resolveForAttribute(sourceElement)
                schema?.pointer?.element?.let { arrayOf(it) }
            }
            else -> null
        }
    }
}
