package icu.windea.bbcode.lang.schema

import com.intellij.openapi.components.*
import com.intellij.openapi.project.*
import com.intellij.openapi.vfs.*
import com.intellij.psi.util.*
import icu.windea.bbcode.*

@Service(Service.Level.PROJECT)
class BBCodeSchemaProvider(
    private val project: Project
) {
    val standardSchema by lazy {
        val url = "/schemas/standard.xml".toClasspathUrl(this::class.java)
        val vFile = VfsUtil.findFileByURL(url) ?: return@lazy null
        val file = PsiUtilCore.getPsiFile(project, vFile)
        BBCodeSchemaResolver.resolve(file)
    }
}
