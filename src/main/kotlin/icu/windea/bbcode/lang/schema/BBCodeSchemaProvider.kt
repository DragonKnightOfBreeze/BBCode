package icu.windea.bbcode.lang.schema

import com.intellij.lang.xml.*
import com.intellij.openapi.application.*
import com.intellij.openapi.components.*
import com.intellij.openapi.project.*
import com.intellij.openapi.vfs.*
import com.intellij.psi.*
import com.intellij.psi.util.*
import icu.windea.bbcode.*

@Service(Service.Level.PROJECT)
class BBCodeSchemaProvider(
    private val project: Project
) {
    val standardSchema by lazy { doGetSchema("/schemas/standard.xml") }

    private fun doGetSchema(path: String): BBCodeSchema? {
        val file = doGetSchemaFile(path) ?: return null
        return runReadAction { BBCodeSchemaResolver.resolve(file) }
    }

    private fun doGetSchemaFile(path: String): PsiFile? {
        val url = path.toClasspathUrl(this::class.java)
        if (ApplicationManager.getApplication().isUnitTestMode) {
            val text = url.readText()
            return PsiFileFactory.getInstance(project).createFileFromText(XMLLanguage.INSTANCE, text)
        }
        val vFile = VfsUtil.findFileByURL(url) ?: return null
        return runReadAction { PsiUtilCore.getPsiFile(project, vFile) }
    }
}
