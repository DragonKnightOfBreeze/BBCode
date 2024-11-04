package icu.windea.bbcode

import com.intellij.lang.*
import com.intellij.psi.tree.*

object BBCodeLanguage : Language("BBCode") {
    val FileElementType = IFileElementType(BBCodeLanguage)
}
