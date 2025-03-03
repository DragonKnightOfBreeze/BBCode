@file:Suppress("HasPlatformType")

package icu.windea.bbcode.editor

import com.intellij.openapi.fileTypes.*
import com.intellij.openapi.options.colors.*
import icu.windea.bbcode.*

//see: com.intellij.openapi.options.colors.pages.XMLColorsPage

class BBCodeColorSettingsPage : ColorSettingsPage {
    private val _attributesDescriptors = arrayOf(
        AttributesDescriptor(BBCodeBundle.message("bbcode.color.tag"), BBCodeHighlighterColors.TAG),
        AttributesDescriptor(BBCodeBundle.message("bbcode.color.tagName"), BBCodeHighlighterColors.TAG_NAME),
        AttributesDescriptor(BBCodeBundle.message("bbcode.color.customTagName"), BBCodeHighlighterColors.CUSTOM_TAG_NAME),
        AttributesDescriptor(BBCodeBundle.message("bbcode.color.matchedTagName"), BBCodeHighlighterColors.MATCHED_TAG_NAME),
        AttributesDescriptor(BBCodeBundle.message("bbcode.color.attributeName"), BBCodeHighlighterColors.ATTRIBUTE_NAME),
        AttributesDescriptor(BBCodeBundle.message("bbcode.color.attributeValue"), BBCodeHighlighterColors.ATTRIBUTE_VALUE),
        AttributesDescriptor(BBCodeBundle.message("bbcode.color.tagData"), BBCodeHighlighterColors.TAG_DATA),
    )
    
    private val _tagToDescriptorMap = buildMap { 
        this["custom_tag_name"] = BBCodeHighlighterColors.CUSTOM_TAG_NAME
        this["matched"] = BBCodeHighlighterColors.MATCHED_TAG_NAME
    }

    override fun getDisplayName() = BBCodeBundle.message("options.bbcode.displayName")

    override fun getIcon() = BBCodeFileType.icon

    override fun getAttributeDescriptors() = _attributesDescriptors

    override fun getColorDescriptors() = ColorDescriptor.EMPTY_ARRAY

    override fun getHighlighter() = SyntaxHighlighterFactory.getSyntaxHighlighter(BBCodeLanguage, null, null)

    override fun getDemoText() = BBCodeConstants.sampleText

    override fun getAdditionalHighlightingTagToDescriptorMap() = _tagToDescriptorMap
}
