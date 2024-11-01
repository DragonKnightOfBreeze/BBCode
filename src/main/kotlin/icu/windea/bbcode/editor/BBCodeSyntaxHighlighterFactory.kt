package icu.windea.bbcode.editor

import com.intellij.openapi.fileTypes.*
import com.intellij.openapi.project.*
import com.intellij.openapi.vfs.*

//see: com.intellij.lang.xml.XmlSyntaxHighlighterFactory

class BBCodeSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?) = BBCodeSyntaxHighlighter()
}
