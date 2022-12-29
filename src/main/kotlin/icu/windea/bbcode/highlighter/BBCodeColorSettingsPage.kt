@file:Suppress("HasPlatformType")

package icu.windea.bbcode.highlighter

import com.intellij.openapi.editor.colors.*
import com.intellij.openapi.fileTypes.*
import com.intellij.openapi.options.colors.*
import com.intellij.openapi.util.*
import icons.BBCodeIcons
import icu.windea.bbcode.*
import org.bouncycastle.operator.bc.*
import javax.swing.*

class BBCodeColorSettingsPage : ColorSettingsPage {
    companion object {
        //Capitalized words
        private val attributesDescriptors = arrayOf(
            AttributesDescriptor(BBCodeBundle.message("bbcode.color.marker"), BBCodeAttributesKeys.MARKER_KEY),
            AttributesDescriptor(BBCodeBundle.message("bbcode.color.tagName"), BBCodeAttributesKeys.TAG_NAME_KEY),
            AttributesDescriptor(BBCodeBundle.message("bbcode.color.attributeName"), BBCodeAttributesKeys.ATTRIBUTE_NAME_KEY),
            AttributesDescriptor(BBCodeBundle.message("bbcode.color.attributeValue"), BBCodeAttributesKeys.ATTRIBUTE_VALUE_KEY),
            AttributesDescriptor(BBCodeBundle.message("bbcode.color.text"), BBCodeAttributesKeys.TEXT_KEY),
            AttributesDescriptor(BBCodeBundle.message("bbcode.color.badCharacter"), BBCodeAttributesKeys.BAD_CHARACTER_KEY)
        )
    }

    override fun getHighlighter() = SyntaxHighlighterFactory.getSyntaxHighlighter(BBCodeLanguage, null, null)

    override fun getAdditionalHighlightingTagToDescriptorMap() = null

    override fun getIcon() = BBCodeIcons.BBCode

    override fun getAttributeDescriptors() = attributesDescriptors

    override fun getColorDescriptors() = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName() = BBCodeBundle.message("options.bbcode.displayName")

    override fun getDemoText() = bbcodeDummyText
}
