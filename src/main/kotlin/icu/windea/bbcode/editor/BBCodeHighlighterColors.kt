package icu.windea.bbcode.editor

import com.intellij.openapi.editor.*
import com.intellij.openapi.editor.colors.*
import com.intellij.openapi.editor.colors.TextAttributesKey.*

//see: com.intellij.openapi.editor.XmlHighlighterColors

object BBCodeHighlighterColors {
    @JvmField
    val TAG = createTextAttributesKey("BBCODE.TAG", DefaultLanguageHighlighterColors.MARKUP_TAG)
    @JvmField
    val TAG_NAME = createTextAttributesKey("BBCODE.TAG_NAME", DefaultLanguageHighlighterColors.KEYWORD)
    @JvmField
    val CUSTOM_TAG_NAME = createTextAttributesKey("BBCODE.CUSTOM_TAG_NAME", TAG_NAME)
    @JvmField
    val ATTRIBUTE_NAME = createTextAttributesKey("BBCODE.ATTRIBUTE_NAME", DefaultLanguageHighlighterColors.MARKUP_ATTRIBUTE)
    @JvmField
    val ATTRIBUTE_VALUE = createTextAttributesKey("BBCODE.ATTRIBUTE_VALUE", DefaultLanguageHighlighterColors.STRING)
    @JvmField
    val TAG_DATA = createTextAttributesKey("BBCODE.TAG_DATA", HighlighterColors.TEXT)

    @JvmStatic
    val MATCHED_TAG_NAME = createTextAttributesKey("BBCODE.MATCHED_TAG_NAME", CodeInsightColors.MATCHED_BRACE_ATTRIBUTES)
}
