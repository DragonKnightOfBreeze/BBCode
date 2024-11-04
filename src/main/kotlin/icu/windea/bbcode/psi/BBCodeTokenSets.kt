package icu.windea.bbcode.psi

import com.intellij.psi.*
import com.intellij.psi.tree.*

object BBCodeTokenSets {
    val WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE)
    val COMMENTS = TokenSet.EMPTY
    val STRINGS = TokenSet.create(BBCodeTypes.TEXT_TOKEN)
}
