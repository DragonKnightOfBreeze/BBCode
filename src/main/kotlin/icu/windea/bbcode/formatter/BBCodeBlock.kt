package icu.windea.bbcode.formatter

import com.intellij.formatting.*
import com.intellij.formatting.Indent
import com.intellij.lang.*
import com.intellij.psi.*
import com.intellij.psi.codeStyle.*
import com.intellij.psi.formatter.common.*
import com.intellij.psi.tree.*
import icu.windea.bbcode.*
import icu.windea.bbcode.psi.*

//com.intellij.psi.formatter.xml.XmlBlock

class BBCodeBlock(
    node: ASTNode,
    private val settings: CodeStyleSettings
) : AbstractBlock(node, createWrap(), createAlignment()) {
    companion object {
        private val TAG_NAME_END = TokenSet.create(BBCodeTypes.TAG_PREFIX_END, BBCodeTypes.EMPTY_TAG_PREFIX_END, BBCodeTypes.TAG_SUFFIX_END)
        
        private fun createWrap(): Wrap? {
            return null
        }

        private fun createAlignment(): Alignment? {
            return null
        }

        private fun createSpacingBuilder(settings: CodeStyleSettings): SpacingBuilder {
            val customSettings = settings.getCustomSettings(BBCodeCodeStyleSettings::class.java)
            return SpacingBuilder(settings, BBCodeLanguage)
                .around(BBCodeTypes.EQUAL_SIGN).spaceIf(customSettings.SPACE_AROUND_EQUALITY_IN_ATTRIBUTE)
                .between(BBCodeTypes.TAG_NAME, TAG_NAME_END).spaceIf(customSettings.SPACE_AFTER_TAG_NAME)
                .between(BBCodeTypes.TAG_NAME, BBCodeTypes.EMPTY_TAG_PREFIX_END).spaceIf(customSettings.SPACE_INSIDE_EMPTY_TAG)
        }
    }

    private val spacingBuilder = createSpacingBuilder(settings)

    override fun buildChildren(): List<Block> {
        val children = mutableListOf<Block>()
        val childNodes = myNode.getChildren(null)
        childNodes.forEach { node ->
            if (node.elementType != TokenType.WHITE_SPACE) {
                children += BBCodeBlock(node, settings)
            }
        }
        return children
    }

    override fun getIndent(): Indent? {
        //val elementType = myNode.elementType
        val parentElementType = myNode.treeParent?.elementType
        return when {
            parentElementType == BBCodeTypes.TAG -> Indent.getNormalIndent()
            else -> Indent.getNoneIndent()
        }
    }

    override fun getChildIndent(): Indent? {
        val elementType = myNode.elementType
        return when {
            elementType is IFileElementType -> Indent.getNoneIndent()
            elementType == BBCodeTypes.TAG -> Indent.getNormalIndent()
            else -> null
        }
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return spacingBuilder.getSpacing(this, child1, child2)
    }

    override fun isLeaf(): Boolean {
        return myNode.firstChildNode == null
    }
}
