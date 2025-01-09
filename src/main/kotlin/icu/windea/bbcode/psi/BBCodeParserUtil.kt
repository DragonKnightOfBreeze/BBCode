package icu.windea.bbcode.psi

import com.intellij.lang.*
import com.intellij.lang.parser.*
import com.intellij.psi.*
import icu.windea.bbcode.psi.BBCodeTypes.*

@Suppress("UNUSED_PARAMETER")
object BBCodeParserUtil : GeneratedParserUtilBase() {
    @JvmStatic
    fun isEmptyOrIncompleteTag(b: PsiBuilder, l: Int): Boolean {
        var prevTokenType = b.rawLookup(-1)
        if (prevTokenType == TokenType.WHITE_SPACE) prevTokenType = b.rawLookup(-2)
        return when (prevTokenType) {
            TAG_PREFIX_END -> false
            EMPTY_TAG_PREFIX_END -> true
            else -> true
        }
    }
}
