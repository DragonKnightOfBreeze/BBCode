package icu.windea.bbcode.intentions

import com.intellij.codeInsight.intention.*
import com.intellij.codeInspection.*
import com.intellij.openapi.editor.*
import com.intellij.openapi.project.*
import com.intellij.psi.*
import com.intellij.psi.util.*
import icu.windea.bbcode.*
import icu.windea.bbcode.psi.*
import icu.windea.bbcode.psi.BBCodeTypes.*

class RemoveSimpleAttributeIntentionFix @JvmOverloads constructor(
    private val isFix: Boolean = false
) : PsiElementBaseIntentionAction(), LocalQuickFix, PriorityAction {
    override fun getName(): String {
        return BBCodeBundle.message("bbcode.intention.removeSimpleAttribute.text")
    }

    override fun getText(): String {
        return if(isFix) name else familyName
    }

    override fun getFamilyName(): String {
        return BBCodeBundle.message("bbcode.intention.removeSimpleAttribute.family")
    }

    override fun getPriority(): PriorityAction.Priority {
        return if(isFix) PriorityAction.Priority.LOW else PriorityAction.Priority.NORMAL
    }

    override fun isAvailable(project: Project, editor: Editor, element: PsiElement): Boolean {
        val tag = element.parentOfType<BBCodeTag>(withSelf = true) ?: return false
        return existsSimpleAttribute(tag)
    }

    override fun invoke(project: Project, editor: Editor, element: PsiElement) {
        val tag = element.parentOfType<BBCodeTag>(withSelf = true) ?: return
        removeSimpleAttribute(tag, editor)
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val e = descriptor.psiElement.parentOfType<BBCodeTag>(withSelf = true) ?: return
        removeSimpleAttribute(e, null)
    }

    private fun existsSimpleAttribute(element: BBCodeTag): Boolean {
        val e1 = element.children().find { it.elementType == ATTRIBUTE_VALUE }
        return e1 != null
    }

    private fun removeSimpleAttribute(element: BBCodeTag, editor: Editor?) {
        val e1 = element.children().find { it.elementType == ATTRIBUTE_VALUE }
        val e2 = element.children().find { it.elementType == EQUAL_SIGN }
        element.deleteChildRange(e2, e1)

        editor?.caretModel?.moveToOffset(element.nameIdentifier?.endOffset ?: element.startOffset)
    }
}
