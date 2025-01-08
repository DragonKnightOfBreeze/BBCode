package icu.windea.bbcode.codeInsight.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.*
import com.intellij.openapi.editor.*
import com.intellij.psi.util.*
import com.intellij.util.*
import icu.windea.bbcode.*
import icu.windea.bbcode.psi.*

class BBCodeTagSuffixCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, result: CompletionResultSet) {
        val element = parameters.position
        if (element.elementType != BBCodeTypes.TAG_NAME) return
        if (element.prevSibling?.elementType != BBCodeTypes.TAG_SUFFIX_START) return
        val tag = element.parent?.castOrNull<BBCodeTag>() ?: return
        val tagName = tag.name.orNull() ?: return
        val lengthToDelete = parameters.position.endOffset - BBCodeConstants.dummyIdentifier.length - parameters.editor.caretModel.offset
        val lookupElement = LookupElementBuilder.create(tagName)
            .withInsertHandler { c, _ ->
                val editor = c.editor
                val caretOffset = editor.caretModel.offset
                if(lengthToDelete != 0) {
                    //delete following tag name identifiers
                    editor.document.deleteString(caretOffset, caretOffset + lengthToDelete)
                }
                val nextChar = c.document.charsSequence.getOrNull(caretOffset)
                if (nextChar == ']') {
                    //move caret to the right bound of "]"
                    EditorModificationUtil.moveCaretRelatively(editor, 1)
                } else {
                    //insert "]" and move caret to the right bound "]"
                    EditorModificationUtil.insertStringAtCaret(editor, "]")
                }
            }
            .withAutoCompletionPolicy(AutoCompletionPolicy.ALWAYS_AUTOCOMPLETE)
        result.addElement(lookupElement)
    }
}
