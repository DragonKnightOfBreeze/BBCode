package icu.windea.bbcode.editor

import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

//see: com.intellij.lang.xml.XmlSyntaxHighlighterFactory

class BBCodeSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?) = BBCodeSyntaxHighlighter()
}
