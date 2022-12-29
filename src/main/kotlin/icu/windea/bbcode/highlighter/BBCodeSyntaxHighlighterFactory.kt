package icu.windea.bbcode.highlighter

import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class BBCodeSyntaxHighlighterFactory :SyntaxHighlighterFactory(){
	override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?) = BBCodeSyntaxHighlighter()
}
