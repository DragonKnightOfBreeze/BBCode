package icu.windea.bbcode.intentions

import com.intellij.codeInsight.intention.*
import com.intellij.openapi.editor.*
import com.intellij.openapi.project.*
import com.intellij.psi.*
import com.intellij.psi.util.*
import icu.windea.bbcode.*
import icu.windea.bbcode.lang.*
import icu.windea.bbcode.psi.*

//com.intellij.codeInspection.htmlInspections.RenameTagBeginOrEndIntentionAction

class RenameTagBeginOrEndIntentionAction(
    private val targetName: String,
    private val sourceName: String,
    private val start: Boolean,
) : IntentionAction {
    override fun getFamilyName(): String {
        return if (start) BBCodeBundle.message("bbcode.intention.rename.start.tag", sourceName, targetName)
        else BBCodeBundle.message("bbcode.intention.rename.end.tag", sourceName, targetName)
    }

    override fun getText(): String {
        return familyName
    }

    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean {
        return true
    }

    override fun invoke(project: Project, editor: Editor, file: PsiFile) {
        val offset = editor.caretModel.offset
        var element = file.findElementAt(offset)
        if (element == null) return
        if (element is PsiWhiteSpace) element = PsiTreeUtil.prevLeaf(element)
        if (element.elementType == BBCodeTypes.TAG_SUFFIX_END) element = element?.prevSibling
        if (element.elementType != BBCodeTypes.TAG_NAME) return
        if (element == null) return

        val text = element.text
        val target = if (targetName != text) element else findOtherSide(element, start)
        if (target == null) return
        val document = file.viewProvider.document ?: return
        val textRange = target.textRange
        document.replaceString(textRange.startOffset, textRange.endOffset, targetName)
    }

    private fun findOtherSide(element: PsiElement, start: Boolean): PsiElement? {
        val parent = element.parent ?: return null
        val tag = (if (parent is PsiErrorElement) parent.parent else parent) as? BBCodeTag ?: return null
        return if (start) {
            BBCodeManager.getStartTagNameElement(tag)
        } else {
            BBCodeManager.getEndTagNameElement(tag)
        }
    }

    override fun startInWriteAction(): Boolean {
        return true
    }
}
