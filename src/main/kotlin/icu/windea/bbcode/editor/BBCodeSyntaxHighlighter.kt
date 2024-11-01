@file:Suppress("HasPlatformType")

package icu.windea.bbcode.editor

import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import icu.windea.bbcode.psi.BBCodeLexerAdapter
import icu.windea.bbcode.psi.BBCodeTypes.*

class BBCodeSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer() = BBCodeLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType?): Array<out TextAttributesKey> {
        return when (tokenType) {
            TAG_PREFIX_START, TAG_PREFIX_END, TAG_SUFFIX_START, TAG_SUFFIX_END -> pack(BBCodeHighlighterColors.TAG)
            TAG_NAME -> pack(BBCodeHighlighterColors.TAG_NAME)
            ATTRIBUTE_NAME -> pack(BBCodeHighlighterColors.ATTRIBUTE_NAME)
            EQUAL_SIGN -> pack(BBCodeHighlighterColors.ATTRIBUTE_VALUE)
            ATTRIBUTE_VALUE_TOKEN -> pack(BBCodeHighlighterColors.ATTRIBUTE_VALUE)
            TEXT_TOKEN -> pack(BBCodeHighlighterColors.TAG_DATA)
            TokenType.BAD_CHARACTER -> pack(HighlighterColors.BAD_CHARACTER)
            else -> TextAttributesKey.EMPTY_ARRAY
        }
    }
}
