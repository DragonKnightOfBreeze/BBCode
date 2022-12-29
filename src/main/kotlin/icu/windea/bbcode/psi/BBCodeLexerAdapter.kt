package icu.windea.bbcode.psi

import com.intellij.lexer.FlexAdapter

class BBCodeLexerAdapter: FlexAdapter(BBCodeLexer(null))

