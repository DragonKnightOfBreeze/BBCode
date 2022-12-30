package icu.windea.bbcode

import com.intellij.codeInsight.highlighting.XmlAwareBraceMatcher
import com.intellij.lang.*
import com.intellij.openapi.editor.highlighter.HighlighterIterator
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.*
import com.intellij.psi.tree.*
import com.intellij.psi.xml.XmlTokenType
import com.intellij.util.containers.BidirectionalMap
import icu.windea.bbcode.psi.BBCodeTypes

//com.intellij.xml.impl.XmlBraceMatcher

class BBCodeBraceMatcher : XmlAwareBraceMatcher {
    companion object {
        private const val TAG_TOKEN_GROUP = 1

        private val PAIRING_TOKENS = BidirectionalMap<IElementType, IElementType>()

        init {
            PAIRING_TOKENS.put(BBCodeTypes.TAG_PREFIX_START, BBCodeTypes.TAG_SUFFIX_END)
        }
    }

    override fun getBraceTokenGroupId(tokenType: IElementType): Int {
        return TAG_TOKEN_GROUP
    }

    override fun isLBraceToken(iterator: HighlighterIterator, text: CharSequence, fileType: FileType): Boolean {
        val tokenType = iterator.tokenType
        return tokenType == BBCodeTypes.TAG_PREFIX_START
    }

    override fun isRBraceToken(iterator: HighlighterIterator, fileText: CharSequence, fileType: FileType): Boolean {
        val tokenType = iterator.tokenType
        return tokenType == BBCodeTypes.TAG_SUFFIX_END
    }

    override fun isPairBraces(tokenType: IElementType, tokenType2: IElementType): Boolean {
        return when {
            tokenType2 == PAIRING_TOKENS.get(tokenType) -> true
            PAIRING_TOKENS.getKeysByValue(tokenType)?.contains(tokenType2) == true -> true
            else -> false
        }
    }

    override fun isStructuralBrace(iterator: HighlighterIterator, text: CharSequence, fileType: FileType): Boolean {
        val tokenType = iterator.tokenType
        return tokenType == BBCodeTypes.TAG_PREFIX_START
    }

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean {
        return true
    }

    override fun isStrictTagMatching(fileType: FileType, braceGroupId: Int): Boolean {
        return when {
            braceGroupId == TAG_TOKEN_GROUP -> true
            else -> false
        }
    }

    override fun areTagsCaseSensitive(fileType: FileType, braceGroupId: Int): Boolean {
        return when {
            braceGroupId == TAG_TOKEN_GROUP -> true
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
        return null
    }

    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int): Int {
        return openingBraceOffset
    }
}

