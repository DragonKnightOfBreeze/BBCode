// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package icu.windea.bbcode.intentions

import com.intellij.codeInsight.intention.*
import com.intellij.openapi.editor.*
import com.intellij.openapi.project.*
import com.intellij.psi.*
import com.intellij.psi.util.*
import icu.windea.bbcode.*
import icu.windea.bbcode.codeInsight.unwrap.*
import icu.windea.bbcode.psi.*
import icu.windea.bbcode.psi.BBCodeTypes.*
import icu.windea.bbcode.util.*

//com.intellij.codeInsight.daemon.impl.analysis.RemoveTagAndPromoteChildrenIntentionAction

class RemoveTagAndPromoteChildrenIntentionAction : IntentionAction {
    override fun getText(): String {
        return familyName
    }

    override fun getFamilyName(): String {
        return BBCodeBundle.message("bbcode.intention.removeTag.family")
    }

    override fun isAvailable(project: Project, editor: Editor, file: PsiFile): Boolean {
        val tag = getTag(editor, file) ?: return false
        if(BBCodeManager.isInlineTag(tag)) return false
        val offset = editor.caretModel.offset
        val startEnd = tag.children().find { it.elementType == TAG_PREFIX_END }
        if (startEnd == null || offset <= startEnd.startOffset) return true
        val endStart = tag.children().find { it.elementType == TAG_SUFFIX_START }
        if (endStart == null || offset >= startEnd.startOffset) return true
        return false
    }

    override fun invoke(project: Project, editor: Editor, file: PsiFile) {
        BBCodeEnclosingTagUnwrapper().unwrap(editor, getTag(editor, file)!!)
    }

    override fun startInWriteAction(): Boolean {
        return true
    }

    private fun getTag(editor: Editor, file: PsiFile): BBCodeTag? {
        val offset = editor.caretModel.offset
        var element = file.findElementAt(offset)
        var parent = element?.parent
        if (parent is BBCodeTag) return parent
        if (parent is BBCodeAttribute) return null

        element = file.findElementAt(offset - 1)
        parent = element?.parent
        if (parent is BBCodeTag) return parent
        return null
    }
}
