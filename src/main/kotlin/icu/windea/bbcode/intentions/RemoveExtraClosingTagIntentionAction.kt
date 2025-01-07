package icu.windea.bbcode.intentions

import com.intellij.codeInsight.intention.*
import com.intellij.codeInspection.*
import com.intellij.openapi.editor.*
import com.intellij.openapi.project.*
import com.intellij.psi.*
import com.intellij.psi.templateLanguages.*
import com.intellij.psi.util.*
import com.intellij.xml.analysis.*
import icu.windea.bbcode.*
import icu.windea.bbcode.psi.*
import icu.windea.bbcode.util.*

//com.intellij.codeInspection.htmlInspections.RemoveExtraClosingTagIntentionAction

class RemoveExtraClosingTagIntentionAction : LocalQuickFix, IntentionAction {
    override fun getFamilyName(): String {
        return XmlAnalysisBundle.message("xml.quickfix.remove.extra.closing.tag")
    }

    override fun getText(): String {
        return name
    }

    override fun isAvailable(project: Project, editor: Editor, file: PsiFile): Boolean {
        val element = file.findElementAt(editor.caretModel.offset) ?: return false
        if (element.parent.let { it !is BBCodeTag && it !is PsiErrorElement }) return false
        if (!BBCodeManager.isTagSuffixToken(element)) return false
        return true
    }

    override fun startInWriteAction(): Boolean {
        return true
    }

    override fun invoke(project: Project, editor: Editor, file: PsiFile) {
        val parent = file.findElementAt(editor.caretModel.offset)?.parent ?: return
        doFix(parent)
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val element = descriptor.psiElement?.takeIf { BBCodeManager.isTagSuffixToken(it) } ?: return
        val parent = element.parent
        doFix(parent)
    }

    private fun doFix(tagElement: PsiElement) {
        if (tagElement is PsiErrorElement) {
            val outers = PsiTreeUtil.findChildrenOfType(tagElement, OuterLanguageElement::class.java)
            val replacement = outers.joinToString("") { it.text }
            val document = getDocument(tagElement)
            if (replacement.isNotEmpty()) {
                val range = tagElement.getTextRange()
                document.replaceString(range.startOffset, range.endOffset, replacement)
            } else {
                tagElement.delete()
            }
        } else {
            val endTagStart = tagElement.children(forward = false).find { it.elementType == BBCodeTypes.TAG_SUFFIX_START }
            if(endTagStart != null) {
                val document = getDocument(tagElement)
                document.deleteString(endTagStart.startOffset, tagElement.lastChild.endOffset)
            }
        }
    }

    private fun getDocument(tagElement: PsiElement): Document {
        return tagElement.containingFile.viewProvider.document
    }

}
