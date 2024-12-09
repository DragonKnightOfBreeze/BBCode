@file:Suppress("HasPlatformType")

package icu.windea.bbcode.psi

import com.intellij.lang.*
import com.intellij.lang.ParserDefinition.*
import com.intellij.openapi.project.*
import com.intellij.psi.*
import icu.windea.bbcode.*
import icu.windea.bbcode.psi.BBCodeTypes.*

class BBCodeParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?) = BBCodeLexerAdapter()

    override fun createParser(project: Project?) = BBCodeParser()

    override fun getFileNodeType() = BBCodeLanguage.FileElementType

    override fun getWhitespaceTokens() = BBCodeTokenSets.WHITE_SPACES

    override fun getCommentTokens() = BBCodeTokenSets.COMMENTS

    override fun getStringLiteralElements() = BBCodeTokenSets.STRINGS

    override fun createFile(viewProvider: FileViewProvider) = BBCodeFile(viewProvider)

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode, right: ASTNode): SpaceRequirements {
        return when {
            left.elementType == ATTRIBUTE_VALUE_TOKEN || right.elementType == ATTRIBUTE_VALUE_TOKEN -> SpaceRequirements.MUST_NOT
            left.elementType == TAG_NAME && right.elementType == ATTRIBUTE -> SpaceRequirements.MUST
            left.elementType == ATTRIBUTE && right.elementType == ATTRIBUTE -> SpaceRequirements.MUST
            else -> SpaceRequirements.MAY
        }
    }
    
    override fun createElement(node: ASTNode?) = Factory.createElement(node)
}
