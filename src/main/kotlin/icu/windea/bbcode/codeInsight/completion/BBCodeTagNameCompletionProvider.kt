package icu.windea.bbcode.codeInsight.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.*
import com.intellij.openapi.editor.*
import com.intellij.psi.util.*
import com.intellij.util.*
import icons.*
import icu.windea.bbcode.*
import icu.windea.bbcode.lang.schema.*
import icu.windea.bbcode.psi.*

class BBCodeTagNameCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, result: CompletionResultSet) {
        val project = parameters.originalFile.project
        if(parameters.position.prevSibling?.elementType != BBCodeTypes.TAG_PREFIX_START) return
        val tag = parameters.position.parent?.castOrNull<BBCodeTag>() ?: return
        val parentTag = tag.parent?.castOrNull<BBCodeTag>()
        if(parentTag == null) {
            val schema = BBCodeSchemaManager.getSchema(project) ?: return
            //typing a root tag
            schema.tags.forEach f@{ tagSchema ->
                if(!tagSchema.parentNames.isNullOrEmpty()) return@f
                addLookupElement(tagSchema, result)
            }
        } else {
            val parentTagSchema = BBCodeSchemaManager.resolveForTag(parentTag) ?: return
            val schema = BBCodeSchemaManager.getSchema(project) ?: return
            parentTagSchema.childNames?.forEach f@{ childName ->
                val tagSchema = schema.tagMap[childName] ?: return@f
                if(tagSchema.parentNames != null && parentTagSchema.name !in tagSchema.parentNames) return@f
                addLookupElement(tagSchema, result)
            }
            if(parentTagSchema.childNames == null) {
                //typing a inline or empty tag
                schema.tags.forEach f@{ tagSchema ->
                    if(!tagSchema.parentNames.isNullOrEmpty()) return@f
                    if(tagSchema.type != BBCodeTagType.Inline && tagSchema.type != BBCodeTagType.Empty) return@f
                    addLookupElement(tagSchema, result)
                }
            }
        }
    }

    private fun addLookupElement(tagSchema: BBCodeSchema.Tag, result: CompletionResultSet) {
        val lookupElement = LookupElementBuilder.create(tagSchema.name)
            .withIcon(BBCodeIcons.Tag)
            .withInsertHandler h@{ c, _ ->
                val editor = c.editor
                val caretOffset = editor.caretModel.offset
                val content = c.document.charsSequence
                val nextCharOffset = content.nextCharOffsetSkippingWhiteSpace(caretOffset)
                val nextChar = content.getOrNull(caretOffset + nextCharOffset)
                if(tagSchema.attribute != null) {
                    if(tagSchema.attribute.optional) {
                        //do nothing
                        return@h
                    }
                    if (nextChar == '=') {
                        //move caret to the right bound of "="
                        EditorModificationUtil.moveCaretRelatively(editor, 1 + nextCharOffset)
                    } else {
                        //insert "=" and move caret to the right bound
                        EditorModificationUtil.insertStringAtCaret(editor, "=")
                    }
                } else if(tagSchema.attributes.isNotEmpty()) {
                    //do nothing
                } else {
                    if(nextChar == ']') {
                        //move caret to the right bound of "]"
                        EditorModificationUtil.moveCaretRelatively(editor, 1 + nextCharOffset)
                    } else if(tagSchema.type == BBCodeTagType.Empty || tagSchema.type == BBCodeTagType.Line) {
                        //insert "]" and move caret to the right bound
                        EditorModificationUtil.insertStringAtCaret(editor, "]", false, 1)
                    } else {
                        //insert "]" and move caret to the right bound, and then insert "[/<tag name>]"
                        EditorModificationUtil.insertStringAtCaret(editor, "][/${tagSchema.name}]", false, 1)
                    }
                }
            }
        result.addElement(lookupElement)
    }
}
