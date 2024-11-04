@file:Suppress("HasPlatformType")

package icu.windea.bbcode.psi

import com.intellij.lang.*
import com.intellij.openapi.project.*
import com.intellij.psi.*
import icu.windea.bbcode.*

class BBCodeParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?) = BBCodeLexerAdapter()

    override fun createParser(project: Project?) = BBCodeParser()

    override fun getFileNodeType() = BBCodeLanguage.FileElementType

    override fun getWhitespaceTokens() = BBCodeTokenSets.WHITE_SPACES

    override fun getCommentTokens() = BBCodeTokenSets.COMMENTS

    override fun getStringLiteralElements() = BBCodeTokenSets.STRINGS

    override fun createFile(viewProvider: FileViewProvider) = BBCodeFile(viewProvider)

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements {
        return when {
            left?.elementType == BBCodeTypes.TAG_NAME && right?.elementType == BBCodeTypes.ATTRIBUTE -> ParserDefinition.SpaceRequirements.MUST
            left?.elementType == BBCodeTypes.ATTRIBUTE && right?.elementType == BBCodeTypes.ATTRIBUTE -> ParserDefinition.SpaceRequirements.MUST
            else -> ParserDefinition.SpaceRequirements.MAY
        }
    }

    override fun createElement(node: ASTNode?) = BBCodeTypes.Factory.createElement(node)
}
