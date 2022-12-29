package icu.windea.bbcode.editor

import com.intellij.psi.*
import com.intellij.usageView.*
import icu.windea.bbcode.BBCodeBundle
import icu.windea.bbcode.psi.*

class BBCodeDescriptionProvider: ElementDescriptionProvider {
	override fun getElementDescription(element: PsiElement, location: ElementDescriptionLocation): String? {
		return when(element){
			is BBCodeTag -> {
				if(location == UsageViewTypeLocation.INSTANCE) BBCodeBundle.message("bbcode.description.tag")
				else element.name
			}
			is BBCodeAttribute -> {
				if(location == UsageViewTypeLocation.INSTANCE) BBCodeBundle.message("bbcode.description.attribute")
				else element.name
			}
			else -> null
		}
	}
}
