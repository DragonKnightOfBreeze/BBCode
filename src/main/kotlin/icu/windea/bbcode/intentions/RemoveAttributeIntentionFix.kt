// Copyright 2000-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package icu.windea.bbcode.intentions

import com.intellij.codeInsight.intention.*
import com.intellij.codeInspection.*
import com.intellij.openapi.editor.*
import com.intellij.openapi.project.*
import com.intellij.psi.*
import com.intellij.psi.util.*
import icu.windea.bbcode.*
import icu.windea.bbcode.psi.*

//com.intellij.codeInsight.daemon.impl.analysis.RemoveAttributeIntentionFix

class RemoveAttributeIntentionFix @JvmOverloads constructor(
    private val myLocalName: String? = null
) : PsiElementBaseIntentionAction(), LocalQuickFix, PriorityAction {
    override fun getName(): String {
        return BBCodeBundle.message("bbcode.intention.removeAttribute.text", myLocalName.orEmpty())
    }

    override fun getText(): String {
        return if (myLocalName != null) name else familyName
    }

    override fun getFamilyName(): String {
        return BBCodeBundle.message("bbcode.intention.removeAttribute.family")
    }

    override fun getPriority(): PriorityAction.Priority {
        return if (myLocalName != null) PriorityAction.Priority.LOW else PriorityAction.Priority.NORMAL
    }

    override fun isAvailable(project: Project, editor: Editor, element: PsiElement): Boolean {
        return getAttribute(element, editor) != null
    }

    override fun invoke(project: Project, editor: Editor, element: PsiElement) {
        removeAttribute(getAttribute(element, editor), editor)
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val e = descriptor.psiElement
        removeAttribute(e, null)
    }

    private fun removeAttribute(e: PsiElement?, editor: Editor?) {
        val myAttribute = PsiTreeUtil.getParentOfType(e, BBCodeAttribute::class.java, false) ?: return

        val next = findNextAttribute(myAttribute)
        myAttribute.delete()

        if (next != null && editor != null) {
            editor.caretModel.moveToOffset(next.textRange.startOffset)
        }
    }

    private fun findNextAttribute(attribute: BBCodeAttribute): PsiElement? {
        var nextSibling = attribute.nextSibling
        while (nextSibling != null) {
            if (nextSibling is BBCodeAttribute) return nextSibling
            nextSibling = nextSibling.nextSibling
        }
        return null
    }

    private fun getAttribute(element: PsiElement, editor: Editor): BBCodeAttribute? {
        val result = PsiTreeUtil.getParentOfType(element, BBCodeAttribute::class.java)
        if (result != null) return result
        if (element.textRange.startOffset == editor.caretModel.offset) {
            return PsiTreeUtil.getParentOfType(PsiTreeUtil.prevLeaf(element), BBCodeAttribute::class.java)
        }
        return null
    }
}

