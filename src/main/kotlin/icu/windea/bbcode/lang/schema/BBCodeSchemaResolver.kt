package icu.windea.bbcode.lang.schema

import com.intellij.openapi.project.*
import com.intellij.psi.*
import com.intellij.psi.xml.*
import icu.windea.bbcode.*

object BBCodeSchemaResolver {
    fun resolve(xmlFile: PsiFile): BBCodeSchema? {
        if (xmlFile !is XmlFile) return null
        val schemaTag = xmlFile.rootTag?.takeIf { it.name == "schema" } ?: return null

        val project = xmlFile.project
        val subTags = schemaTag.subTags.groupBy { it.name }
        val description = subTags["description"]?.firstOrNull()?.value?.trimmedText
        val url = subTags["url"]?.firstOrNull()?.value?.trimmedText
        val tags = subTags["tags"]?.firstOrNull()?.subTags?.mapNotNull f@{ tagTag -> resolveTagSchema(tagTag, project) }.orEmpty()
        return BBCodeSchema(
            description = description,
            url = url,
            tags = tags,
        )
    }

    private fun resolveTagSchema(tag: XmlTag, project: Project): BBCodeSchema.Tag? {
        val attributes = tag.attributes.associateBy({ it.name }, { it.value })
        val subTags = tag.subTags.groupBy { it.name }
        return BBCodeSchema.Tag(
            pointer = tag.createPointer(project),
            name = attributes["name"] ?: return null,
            parentNames = attributes["parentNames"]?.toCommaDelimitedStringSet(),
            childNames = attributes["childNames"]?.toCommaDelimitedStringSet(),
            textType = attributes["textType"],
            inline = attributes["inline"].toBoolean(),
            attribute = subTags["attribute"]?.firstOrNull()?.let { resolveSimpleAttributeSchema(it, project) },
            attributes = subTags["attributes"]?.firstOrNull()?.subTags?.mapNotNull { resolveAttributeSchema(it, project) }.orEmpty(),
            description = subTags["description"]?.firstOrNull()?.value?.trimmedText,
        )
    }

    private fun resolveSimpleAttributeSchema(tag: XmlTag, project: Project): BBCodeSchema.SimpleAttribute {
        val attributes = tag.attributes.associateBy({ it.name }, { it.value })
        val subTags = tag.subTags.groupBy { it.name }
        return BBCodeSchema.SimpleAttribute(
            pointer = tag.createPointer(project),
            type = attributes["type"] ?: "string",
            optional = attributes["optional"].toBoolean(),
            description = subTags["description"]?.firstOrNull()?.value?.trimmedText,
        )
    }

    private fun resolveAttributeSchema(tag: XmlTag, project: Project): BBCodeSchema.Attribute? {
        val attributes = tag.attributes.associateBy({ it.name }, { it.value })
        val subTags = tag.subTags.groupBy { it.name }
        return BBCodeSchema.Attribute(
            pointer = tag.createPointer(project),
            name = attributes["name"] ?: return null,
            type = attributes["type"] ?: "string",
            optional = attributes["optional"].toBoolean(),
            swap = attributes["swap"].toBoolean(),
            description = subTags["description"]?.firstOrNull()?.value?.trimmedText,
        )
    }
}
