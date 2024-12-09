package icu.windea.bbcode.psi

import com.intellij.psi.*
import com.intellij.psi.tree.*

object BBCodeTokenSets {
    val WHITE_SPACES: TokenSet = TokenSet.create(TokenType.WHITE_SPACE)
    val COMMENTS: TokenSet = TokenSet.EMPTY
    val STRINGS: TokenSet = TokenSet.create(BBCodeTypes.TEXT_TOKEN)
}
