// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package icu.windea.bbcode.codeInsight.unwrap

import com.intellij.codeInsight.unwrap.*
import com.intellij.openapi.editor.*
import com.intellij.psi.*
import com.intellij.psi.codeStyle.*
import com.intellij.psi.util.*
import com.intellij.util.*
import icu.windea.bbcode.*
import icu.windea.bbcode.lang.*
import icu.windea.bbcode.psi.*
import icu.windea.bbcode.psi.BBCodeTypes.*
import kotlin.math.*

//com.intellij.lang.xml.XmlEnclosingTagUnwrapper

class BBCodeEnclosingTagUnwrapper : Unwrapper {
    override fun isApplicableTo(e: PsiElement): Boolean {
        return e is BBCodeTag && !BBCodeManager.isEmptyTag(e)
    }

    override fun collectElementsToIgnore(element: PsiElement, result: Set<PsiElement>) {
    }

    override fun getDescription(e: PsiElement): String {
        e as BBCodeTag
        return BBCodeBundle.message("bbcode.action.unwrap.enclosing.tag.name.description", e.name)
    }

    override fun collectAffectedElements(element: PsiElement, toExtract: MutableList<in PsiElement>): PsiElement {
        val range = element.textRange
        val startTagNameEnd = element.children().find { it.elementType == TAG_PREFIX_END }
        val endTagNameStart = element.children().find { it.elementType == TAG_SUFFIX_START }

        val start = startTagNameEnd?.textRange?.endOffset ?: range.startOffset
        val end = endTagNameStart?.textRange?.startOffset ?: range.endOffset

        for (child in element.children) {
            val childRange = child.textRange
            if (childRange.startOffset >= start && childRange.endOffset <= end) {
                toExtract.add(child)
            }
        }
        return element
    }

    override fun unwrap(editor: Editor, element: PsiElement): List<PsiElement> {
        val range = element.textRange
        val startTagNameEnd = element.children().find { it.elementType == TAG_PREFIX_END }
        val endTagNameStart = element.children().find { it.elementType == TAG_SUFFIX_START }

        val project = element.project
        val file = element.containingFile
        val document = editor.document
        val marker = document.createRangeMarker(range)
        if (endTagNameStart != null) {
            document.deleteString(endTagNameStart.textRange.startOffset, range.endOffset)
            document.deleteString(range.startOffset, startTagNameEnd!!.textRange.endOffset)
        } else {
            document.replaceString(range.startOffset, range.endOffset, "")
        }

        deleteEmptyLine(document, marker.startOffset)
        deleteEmptyLine(document, marker.endOffset)

        PsiDocumentManager.getInstance(project).commitDocument(document)
        CodeStyleManager.getInstance(project).adjustLineIndent(file, marker.textRange)
        return emptyList()
    }

    private fun deleteEmptyLine(document: Document, offset: Int) {
        val line = if (offset < document.textLength) document.getLineNumber(offset) else -1
        if (line > 0 && DocumentUtil.isLineEmpty(document, line)) {
            val start = document.getLineStartOffset(line)
            val end = min((document.getLineEndOffset(line) + 1).toDouble(), (document.textLength - 1).toDouble()).toInt()
            if (end == document.textLength - 1) {
                document.deleteString(start - 1, end)
            } else if (start < end) {
                document.deleteString(start, end)
            }
        }
    }
}
