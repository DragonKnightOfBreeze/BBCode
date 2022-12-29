package icu.windea.bbcode.psi

import com.intellij.openapi.project.*
import com.intellij.psi.*
import icu.windea.bbcode.*

object BBCodeElementFactory {
	@JvmStatic
	fun createDummyFile(project: Project, text: String): BBCodeFile {
		return PsiFileFactory.getInstance(project).createFileFromText(BBCodeLanguage, text) as BBCodeFile
	}
}

