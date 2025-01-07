@file:Suppress("UnstableApiUsage")

package icu.windea.bbcode.documentation

import com.intellij.model.*
import com.intellij.openapi.application.*
import com.intellij.platform.backend.documentation.*
import com.intellij.platform.backend.presentation.*
import com.intellij.pom.*
import com.intellij.psi.*
import icu.windea.bbcode.*
import icu.windea.bbcode.lang.schema.*
import icu.windea.bbcode.psi.*

class BBCodeSchemaBasedDocumentationTarget(
    private val element: PsiElement,
    private val elementForTarget: PsiElement,
) : DocumentationTarget {
    override fun createPointer(): Pointer<out DocumentationTarget> {
        val elementPtr = element.createSmartPointer()
        val elementForTargetPtr = elementForTarget.createSmartPointer()
        return Pointer {
            val element = elementPtr.dereference() ?: return@Pointer null
            val elementForTarget = elementForTargetPtr.dereference() ?: return@Pointer null
            BBCodeSchemaBasedDocumentationTarget(element, elementForTarget)
        }
    }

    override val navigatable: Navigatable?
        get() = (element.navigationElement ?: element) as? Navigatable

    override fun computePresentation(): TargetPresentation {
        return computeLocalPresentation(elementForTarget) ?: TargetPresentation.builder("").presentation()
    }

    override fun computeDocumentationHint(): String? {
        return computeLocalDocumentationHint(elementForTarget)
    }

    override fun computeDocumentation(): DocumentationResult {
        return DocumentationResult.asyncDocumentation {
            computeLocalDocumentation(elementForTarget)
        }
    }
}

private fun computeLocalPresentation(element: PsiElement): TargetPresentation? {
    return when (element) {
        is BBCodeTag -> {
            val name = element.name.orNull() ?: return null
            return TargetPresentation.builder(name).containerText("Tag").presentation()
        }
        is BBCodeAttribute -> {
            val name = element.name.orNull() ?: return null
            return TargetPresentation.builder(name).containerText("Attribute").presentation()
        }
        else -> null
    }
}

private fun computeLocalDocumentationHint(element: PsiElement): String? {
    return when (element) {
        is BBCodeTag -> {
            val name = element.name.orNull() ?: return null
            buildDocumentation {
                definition {
                    append("(tag) <b>").append(name.escapeXml()).append("</b>")
                }
            }
        }
        is BBCodeAttribute -> {
            val name = element.name.orNull() ?: return null
            buildDocumentation {
                definition {
                    append("(attribute) <b>").append(name.escapeXml()).append("</b>")
                }
            }
        }
        else -> null
    }
}

private fun computeLocalDocumentation(element: PsiElement ): DocumentationResult.Documentation? {
    return when (element) {
        is BBCodeTag -> {
            val name = element.name.orNull() ?: return null
            val schema = runReadAction { BBCodeSchemaManager.resolveForTag(element) }
            val html = buildDocumentation b@{
                definition {
                    append("(tag) <b>").append(name.escapeXml()).append("</b>")
                }
                if (schema == null) return@b
                if (!schema.description.isNullOrEmpty()) {
                    content { append(schema.description.replaceBr()) }
                }
            }
            DocumentationResult.documentation(html)
        }
        is BBCodeAttribute -> {
            val name = element.name.orNull() ?: return null
            val schema = runReadAction { BBCodeSchemaManager.resolveForAttribute(element) }
            val html = buildDocumentation b@{
                definition {
                    append("(attribute) <b>").append(name.escapeXml()).append("</b>")
                }
                if (schema == null) return@b
                if (!schema.description.isNullOrEmpty()) {
                    content {
                        append(schema.description.replaceBr())
                    }
                }
            }
            DocumentationResult.documentation(html)
        }
        else -> null
    }
}

private fun String.replaceBr() = replace("\n", "<br/>")
