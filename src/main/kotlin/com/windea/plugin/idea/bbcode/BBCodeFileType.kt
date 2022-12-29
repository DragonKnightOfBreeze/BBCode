package com.windea.plugin.idea.bbcode

import com.intellij.openapi.fileTypes.*
import icons.BBCodeIcons

object BBCodeFileType : LanguageFileType(BBCodeLanguage) {
	override fun getName() = "BBCode"

	override fun getDescription() = BBCodeBundle.message("filetype.bbcode.description")

	override fun getDisplayName() = BBCodeBundle.message("filetype.bbcode.displayName")

	override fun getDefaultExtension() = ""

	override fun getIcon() = BBCodeIcons.BBCode
}
