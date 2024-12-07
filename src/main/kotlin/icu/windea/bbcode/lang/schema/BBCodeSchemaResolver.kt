package icu.windea.bbcode.lang.schema

import com.intellij.openapi.project.*
import com.intellij.psi.*
import com.intellij.psi.xml.*
import icu.windea.bbcode.*

object BBCodeSchemaResolver {
    fun resolveFromXmlFile(file: PsiFile): BBCodeSchema? {
        if (file !is XmlFile) return null
        val schemaTag = file.rootTag?.takeIf { it.name == "schema" } ?: return null

        val project = file.project
        val subTags = schemaTag.subTags.groupBy { it.name }
        val referenceUrl = subTags["referenceUrl"]?.firstOrNull()?.value?.trimmedText ?: return null
        val tags = subTags["tags"]?.firstOrNull()?.subTags?.mapNotNull f@{ tagTag -> resolveTagSchema(tagTag, project) }.orEmpty()
        return BBCodeSchema(
            referenceUrl = referenceUrl,
            tags = tags,
        )
    }

    private fun resolveTagSchema(tag: XmlTag, project: Project): BBCodeSchema.Tag? {
        val attributes = tag.attributes.associateBy({ it.name }, { it.value })
        val subTags = tag.subTags.groupBy { it.name }
        return BBCodeSchema.Tag(
            pointer = tag.createPointer(project),
            id = attributes["id"] ?: return null,
            childIds = attributes["childIds"]?.toCommaDelimitedStringSet().orEmpty(),
            textType = attributes["textType"],
            inline = attributes["inline"].toBoolean(),
            attribute = subTags["attribute"]?.firstOrNull()?.let { resolveSimpleAttributeSchema(it, project) },
            attributes = subTags["attributes"]?.mapNotNull { resolveAttributeSchema(it, project) }.orEmpty(),
            doc = subTags["doc"]?.firstOrNull()?.value?.trimmedText,
            urls = subTags["url"]?.mapNotNullTo(mutableSetOf()) { it.value.trimmedText }.orEmpty(),
        )
    }

    private fun resolveSimpleAttributeSchema(tag: XmlTag, project: Project): BBCodeSchema.SimpleAttribute {
        val attributes = tag.attributes.associateBy({ it.name }, { it.value })
        return BBCodeSchema.SimpleAttribute(
            pointer = tag.createPointer(project),
            type = attributes["type"] ?: "string",
            optional = attributes["optional"].toBoolean(),
        )
    }

    private fun resolveAttributeSchema(tag: XmlTag, project: Project): BBCodeSchema.Attribute? {
        val attributes = tag.attributes.associateBy({ it.name }, { it.value })
        return BBCodeSchema.Attribute(
            pointer = tag.createPointer(project),
            id = attributes["id"] ?: return null,
            type = attributes["type"] ?: "string",
            optional = attributes["optional"].toBoolean(),
            swap = attributes["swap"].toBoolean(),
        )
    }
}
