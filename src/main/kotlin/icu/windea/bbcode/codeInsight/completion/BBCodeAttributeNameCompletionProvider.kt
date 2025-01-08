package icu.windea.bbcode.codeInsight.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.*
import com.intellij.openapi.editor.*
import com.intellij.util.*
import icu.windea.bbcode.*
import icu.windea.bbcode.lang.schema.*
import icu.windea.bbcode.psi.*

class BBCodeAttributeNameCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, result: CompletionResultSet) {
        val attribute = parameters.position.parent?.castOrNull<BBCodeAttribute>() ?: return
        val tag = attribute.parent?.castOrNull<BBCodeTag>() ?: return
        val tagSchema = BBCodeSchemaManager.resolveForTag(tag) ?: return
        val existingAttributeNames = tag.attributeList.mapNotNullTo(mutableSetOf()) { if (it === attribute) null else it.name }
        tagSchema.attributes.forEach f@{ attributeSchema ->
            if (attributeSchema.name in existingAttributeNames) return@f //skip existing attributes
            addLookupElement(attributeSchema, result)
        }
    }

    private fun addLookupElement(attributeSchema: BBCodeSchema.Attribute, result: CompletionResultSet) {
        val tailText = if(attributeSchema.optional) null else " (required)"
        val lookupElement = LookupElementBuilder.create(attributeSchema.name)
            .withTailText(tailText, true)
            .withInsertHandler { c, _ ->
                val editor = c.editor
                val caretOffset = editor.caretModel.offset
                val content = c.document.charsSequence
                val nextCharOffset = content.nextCharOffsetSkippingWhiteSpace(caretOffset)
                val nextChar = content.getOrNull(caretOffset + nextCharOffset)
                if (nextChar == '=') {
                    //move caret to the right bound of "="
                    EditorModificationUtil.moveCaretRelatively(editor, 1 + nextCharOffset)
                } else {
                    //insert "=" and move caret to the right bound
                    EditorModificationUtil.insertStringAtCaret(editor, "=")
                }
            }
        result.addElement(lookupElement)
    }
}
