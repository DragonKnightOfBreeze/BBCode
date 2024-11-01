package icu.windea.bbcode.psi

import com.intellij.extapi.psi.*
import com.intellij.psi.*
import icu.windea.bbcode.*

class BBCodeFile(
    viewProvider: FileViewProvider
) : PsiFileBase(viewProvider, BBCodeLanguage) {
    override fun getFileType() = BBCodeFileType
}

