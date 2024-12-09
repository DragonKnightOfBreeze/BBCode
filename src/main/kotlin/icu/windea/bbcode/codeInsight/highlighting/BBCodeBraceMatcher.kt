package icu.windea.bbcode.codeInsight.highlighting

import com.intellij.codeInsight.highlighting.*
import com.intellij.lang.*
import com.intellij.openapi.editor.highlighter.*
import com.intellij.openapi.fileTypes.*
import com.intellij.psi.*
import com.intellij.psi.tree.*
import com.intellij.util.containers.*
import icu.windea.bbcode.psi.*

//see: com.intellij.xml.impl.XmlBraceMatcher

class BBCodeBraceMatcher : XmlAwareBraceMatcher {
    object Constants {
        const val TAG_TOKEN_GROUP = 1

        val PAIRING_TOKENS = BidirectionalMap<IElementType, IElementType>()

        init {
            PAIRING_TOKENS.put(BBCodeTypes.TAG_PREFIX_START, BBCodeTypes.TAG_SUFFIX_END)
        }
    }

    override fun getBraceTokenGroupId(tokenType: IElementType): Int {
        val matcher = getPairedBraceMatcher(tokenType)
        if (matcher != null) {
            val pairs = matcher.pairs
            for (pair in pairs) {
                if (pair.leftBraceType === tokenType || pair.rightBraceType === tokenType) {
                    return tokenType.language.hashCode()
                }
            }
        }
        return Constants.TAG_TOKEN_GROUP
    }

    override fun isLBraceToken(iterator: HighlighterIterator, text: CharSequence, fileType: FileType): Boolean {
        val tokenType = iterator.tokenType
        val matcher = getPairedBraceMatcher(tokenType)
        if (matcher != null) {
            val pairs = matcher.pairs
            for (pair in pairs) {
                if (pair.leftBraceType === tokenType) return true
            }
        }
        return tokenType == BBCodeTypes.TAG_PREFIX_START
    }

    override fun isRBraceToken(iterator: HighlighterIterator, fileText: CharSequence, fileType: FileType): Boolean {
        val tokenType = iterator.tokenType
        val matcher = getPairedBraceMatcher(tokenType)
        if (matcher != null) {
            val pairs = matcher.pairs
            for (pair in pairs) {
                if (pair.rightBraceType === tokenType) return true
            }
        }
        return tokenType == BBCodeTypes.TAG_SUFFIX_END
    }

    override fun isPairBraces(tokenType1: IElementType, tokenType2: IElementType): Boolean {
        val matcher = getPairedBraceMatcher(tokenType1)
        if (matcher != null) {
            val pairs = matcher.pairs
            for (pair in pairs) {
                if (pair.leftBraceType === tokenType1) return pair.rightBraceType === tokenType2
                if (pair.rightBraceType === tokenType1) return pair.leftBraceType === tokenType2
            }
        }
        return when {
            tokenType2 == Constants.PAIRING_TOKENS.get(tokenType1) -> true
            Constants.PAIRING_TOKENS.getKeysByValue(tokenType1)?.contains(tokenType2) == true -> true
            else -> false
        }
    }

    override fun isStructuralBrace(iterator: HighlighterIterator, text: CharSequence, fileType: FileType): Boolean {
        val tokenType = iterator.tokenType
        val matcher = getPairedBraceMatcher(tokenType)
        if (matcher != null) {
            val pairs = matcher.pairs
            for (pair in pairs) {
                if ((pair.leftBraceType === tokenType || pair.rightBraceType === tokenType) &&
                    pair.isStructural
                ) return true
            }
        }
        return tokenType == BBCodeTypes.TAG_PREFIX_START || tokenType == BBCodeTypes.TAG_SUFFIX_END
    }

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean {
        return true
    }

    override fun isStrictTagMatching(fileType: FileType, braceGroupId: Int): Boolean {
        return when {
            braceGroupId == Constants.TAG_TOKEN_GROUP -> true
            else -> false
        }
    }

    override fun areTagsCaseSensitive(fileType: FileType, braceGroupId: Int): Boolean {
        return when {
            braceGroupId == Constants.TAG_TOKEN_GROUP -> true
            else -> false
        }
    }

    override fun getTagName(fileText: CharSequence, iterator: HighlighterIterator): String? {
        val tokenType = iterator.tokenType
        var name: String? = null
        when {
            tokenType == BBCodeTypes.TAG_PREFIX_START -> {
                iterator.advance()
                var tokenType1 = if (iterator.atEnd()) null else iterator.tokenType

                var wasWhiteSpace = false
                if (isWhitespace(tokenType1)) {
                    wasWhiteSpace = true
                    iterator.advance()
                    tokenType1 = if (iterator.atEnd()) null else iterator.tokenType
                }

                if (tokenType1 === BBCodeTypes.TAG_NAME) {
                    name = fileText.subSequence(iterator.start, iterator.end).toString()
                }
                if (wasWhiteSpace) iterator.retreat()
                iterator.retreat()
            }
            tokenType == BBCodeTypes.TAG_SUFFIX_END -> {
                iterator.retreat()
                var tokenType1 = if (iterator.atEnd()) null else iterator.tokenType

                var wasWhiteSpace = false
                if (isWhitespace(tokenType1)) {
                    wasWhiteSpace = true
                    iterator.retreat()
                    tokenType1 = if (iterator.atEnd()) null else iterator.tokenType
                }

                if (tokenType1 === BBCodeTypes.TAG_NAME) {
                    name = fileText.subSequence(iterator.start, iterator.end).toString()
                }
                if (wasWhiteSpace) iterator.advance()
                iterator.advance()
            }
        }
        return name
    }

    private fun isWhitespace(tokenType1: IElementType?): Boolean {
        return tokenType1 === TokenType.WHITE_SPACE
    }

    override fun getOppositeBraceTokenType(type: IElementType): IElementType? {
        val matcher = getPairedBraceMatcher(type)
        if (matcher != null) {
            val pairs = matcher.pairs
            for (pair in pairs) {
                if (pair.leftBraceType === type) return pair.rightBraceType
                if (pair.rightBraceType === type) return pair.leftBraceType
            }
        }
        return null
    }

    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int): Int {
        return openingBraceOffset
    }

    private fun getPairedBraceMatcher(tokenType: IElementType): PairedBraceMatcher? {
        return LanguageBraceMatching.INSTANCE.forLanguage(tokenType.language)
    }
}

