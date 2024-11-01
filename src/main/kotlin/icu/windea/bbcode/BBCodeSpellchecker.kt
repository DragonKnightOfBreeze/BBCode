package icu.windea.bbcode

import com.intellij.psi.*
import com.intellij.psi.util.*
import com.intellij.spellchecker.tokenizer.*
import icu.windea.bbcode.psi.BBCodeTypes.*

class BBCodeSpellchecker : SpellcheckingStrategy() {
    override fun getTokenizer(element: PsiElement?): Tokenizer<*> {
        return when (element.elementType) {
            ATTRIBUTE_VALUE_TOKEN -> TEXT_TOKENIZER
            TEXT_TOKEN -> TEXT_TOKENIZER
            else -> EMPTY_TOKENIZER
        }
    }
}
