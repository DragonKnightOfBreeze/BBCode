@file:Suppress("DEPRECATION")

package icu.windea.bbcode.highlighter

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors.*
import com.intellij.openapi.editor.HighlighterColors.*
import com.intellij.openapi.editor.colors.TextAttributesKey.*
import java.awt.*

object BBCodeAttributesKeys {
	private val BBCODE_ATTRIBUTE = MARKUP_ATTRIBUTE.defaultAttributes.clone().apply{ foregroundColor = Color(0xBABABA)}
	private val BBCODE_KEYWORD = KEYWORD.defaultAttributes.clone().apply { foregroundColor = Color(0xE8BF6A) }

	@JvmField val MARKER_KEY = createTextAttributesKey("BBCODE.MARKER", BBCODE_KEYWORD)
	@JvmField val TAG_NAME_KEY = createTextAttributesKey("BBCODE.TAG_NAME", BBCODE_KEYWORD)
	@JvmField val ATTRIBUTE_NAME_KEY = createTextAttributesKey("BBCODE.ATTRIBUTE_NAME",BBCODE_ATTRIBUTE)
	@JvmField val ATTRIBUTE_VALUE_KEY = createTextAttributesKey("BBCODE.ATTRIBUTE_VALUE", STRING)
	@JvmField val TEXT_KEY = createTextAttributesKey("BBCODE.TEXT", TEXT)
	@JvmField val BAD_CHARACTER_KEY = createTextAttributesKey("BBCODE.BAD_CHARACTER", BAD_CHARACTER)
}
