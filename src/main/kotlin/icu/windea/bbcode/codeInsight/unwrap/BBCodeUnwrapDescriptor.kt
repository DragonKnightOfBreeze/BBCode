// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package icu.windea.bbcode.codeInsight.unwrap

import com.intellij.codeInsight.unwrap.*
import com.intellij.openapi.editor.*
import com.intellij.openapi.project.*
import com.intellij.openapi.util.*
import com.intellij.psi.*
import com.intellij.psi.util.*
import icu.windea.bbcode.lang.*
import icu.windea.bbcode.psi.*

//com.intellij.lang.xml.XmlUnwrapDescriptor

class BBCodeUnwrapDescriptor : UnwrapDescriptor {
    override fun collectUnwrappers(project: Project, editor: Editor, file: PsiFile): List<Pair<PsiElement, Unwrapper>> {
        val offset = editor.caretModel.offset

        val e1 = file.findElementAt(offset)
        if (e1 != null) {
            val language = e1.parent.language
            if (language !== file.language) {
                val unwrapDescriptor = LanguageUnwrappers.INSTANCE.forLanguage(language)
                if (unwrapDescriptor != null && unwrapDescriptor !is BBCodeUnwrapDescriptor) {
                    return unwrapDescriptor.collectUnwrappers(project, editor, file)
                }
            }
        }

        val result: MutableList<Pair<PsiElement, Unwrapper>> = ArrayList()

        val viewProvider = file.viewProvider

        for (language in viewProvider.languages) {
            val unwrapDescriptor = LanguageUnwrappers.INSTANCE.forLanguage(language)
            if (unwrapDescriptor is BBCodeUnwrapDescriptor) {
                val e = viewProvider.findElementAt(offset, language)

                var tag = PsiTreeUtil.getParentOfType(e, BBCodeTag::class.java)
                while (tag != null) {
                    if (BBCodeManager.getStartTagNameElement(tag) != null) {
                        result.add(Pair(tag, BBCodeEnclosingTagUnwrapper()))
                    }
                    tag = PsiTreeUtil.getParentOfType(tag, BBCodeTag::class.java)
                }
            }
        }

        result.sortBy { it.first.textOffset }

        return result
    }

    override fun showOptionsDialog(): Boolean {
        return true
    }

    override fun shouldTryToRestoreCaretPosition(): Boolean {
        return false
    }
}
