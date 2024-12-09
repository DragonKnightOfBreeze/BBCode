@file:Suppress("UnstableApiUsage")

package icu.windea.bbcode.documentation

import com.intellij.model.*
import com.intellij.platform.backend.documentation.*
import com.intellij.platform.backend.presentation.*
import com.intellij.pom.*
import com.intellij.psi.*
import com.intellij.refactoring.suggested.*
import icu.windea.bbcode.*
import icu.windea.bbcode.lang.schema.*
import icu.windea.bbcode.psi.BBCodeAttribute
import icu.windea.bbcode.psi.BBCodeTag

class BBCodeSchemaBasedDocumentationTarget(
    val element: PsiElement
) : DocumentationTarget {
    override fun createPointer(): Pointer<out DocumentationTarget> {
        val elementPtr = element.createSmartPointer()
        return Pointer {
            val element = elementPtr.dereference() ?: return@Pointer null
            BBCodeSchemaBasedDocumentationTarget(element)
        }
    }

    override val navigatable: Navigatable?
        get() = (element.navigationElement ?: element) as? Navigatable

    override fun computePresentation(): TargetPresentation {
        return computeLocalPresentation(element) ?: TargetPresentation.builder("").presentation()
    }

    override fun computeDocumentationHint(): String? {
        return computeLocalDocumentationHint(element)
    }

    override fun computeDocumentation(): DocumentationResult {
        return DocumentationResult.asyncDocumentation {
            computeLocalDocumentation(element)
        }
    }
}

private fun computeLocalPresentation(element: PsiElement): TargetPresentation? {
    return when(element) {
        is BBCodeTag -> {
            val name = element.name ?: return null
            return TargetPresentation.builder(name).containerText("Tag").presentation()
        }
        is BBCodeAttribute -> {
            val name = element.name ?: return null
            return TargetPresentation.builder(name).containerText("Attribute").presentation()
        }
        else -> null
    }
}

private fun computeLocalDocumentationHint(element: PsiElement): String? {
    return when(element) {
        is BBCodeTag -> {
            val name = element.name ?: return null
            buildDocumentation {
                definition {
                    append("(tag) <b>").append(name.escapeXml()).append("</b>")
                }
            }
        }
        is BBCodeAttribute -> {
            val name = element.name ?: return null
            buildDocumentation {
                definition {
                    append("(attribute) <b>").append(name.escapeXml()).append("</b>")
                }
            }
        }
        else -> null
    }
}

private fun computeLocalDocumentation(element: PsiElement): DocumentationResult.Documentation? {
    return when(element) {
        is BBCodeTag -> {
            val name = element.name ?: return null
            val schema = BBCodeSchemaManager.resolveForTag(element)
            val html = buildDocumentation b@{
                definition {
                    append("(tag) <b>").append(name.escapeXml()).append("</b>")
                }
                if(schema == null) return@b
                if(!schema.doc.isNullOrEmpty()) {
                    content { append(schema.doc) }
                }
                if(schema.urls.isNotEmpty()) {
                    schema.urls.forEach { content { append("See also: ").appendLink(it, it) } }
                }
            }
            DocumentationResult.documentation(html)
        }
        is BBCodeAttribute -> {
            val name = element.name ?: return null
            val schema = BBCodeSchemaManager.resolveForAttribute(element)
            val html = buildDocumentation b@{
                definition {
                    append("(attribute) <b>").append(name.escapeXml()).append("</b>")
                }
                if(schema == null) return@b
                if(!schema.doc.isNullOrEmpty()) {
                    content {
                        append(schema.doc)
                    }
                }
            }
            DocumentationResult.documentation(html)
        }
        else -> null
    }
}

private fun DocumentationBuilder.appendLink(url: String, text: String): DocumentationBuilder {
    this.append("<a href=\"").append(url).append("\">").append(text).append("</a>")
    return this
}
