package icu.windea.bbcode.psi

import com.intellij.lang.*
import com.intellij.lang.parser.*
import com.intellij.psi.*
import icu.windea.bbcode.lang.schema.*
import icu.windea.bbcode.psi.BBCodeTypes.*
import icu.windea.bbcode.util.*

@Suppress("UNUSED_PARAMETER")
object BBCodeParserUtil : GeneratedParserUtilBase() {
    object Keys: KeyRegistry() {
        val tagName by createKey<String>(Keys)
    }

    @JvmStatic
    fun putTagName(b: PsiBuilder, l: Int): Boolean {
        if(b.tokenType == TAG_NAME) {
            b.putUserData(Keys.tagName, b.tokenText)
        }
        return true
    }

    @JvmStatic
    fun isIncompleteTag(b: PsiBuilder, l: Int): Boolean {
        var prevTokenType = b.rawLookup(-1)
        if (prevTokenType == TokenType.WHITE_SPACE) prevTokenType = b.rawLookup(-2)
        return prevTokenType != TAG_PREFIX_END && prevTokenType != EMPTY_TAG_PREFIX_END
    }

    @JvmStatic
    fun isInlineTag(b: PsiBuilder, l: Int): Boolean {
        val tagName = b.getUserData(Keys.tagName) ?: return false
        val tagType = BBCodeSchemaManager.getSchema(b.project)?.tagMap?.get(tagName)?.type ?: return false
        return tagType == BBCodeTagType.Inline
    }

    @JvmStatic
    fun isLineTag(b: PsiBuilder, l: Int): Boolean {
        val tagName = b.getUserData(Keys.tagName) ?: return false
        val tagType = BBCodeSchemaManager.getSchema(b.project)?.tagMap?.get(tagName)?.type ?: return false
        return tagType == BBCodeTagType.Line
    }
}
