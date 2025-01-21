package icu.windea.bbcode.lang.schema

import com.intellij.psi.*
import com.intellij.psi.xml.*

data class BBCodeSchema(
    val description: String? = null,
    val url: String? = null,
    val tags: List<Tag>,
) {
    val tagMap by lazy { tags.associateBy { it.name } }

    data class Tag(
        val pointer: SmartPsiElementPointer<XmlTag>,
        val name: String,
        val parentNames: Set<String>? = null,
        val childNames: Set<String>? = null,
        val type: BBCodeTagType = BBCodeTagType.Default,
        val textType: String? = null,
        val attribute: SimpleAttribute? = null,
        val attributes: List<Attribute> = emptyList(),
        val description: String? = null,
    ) {
        val attributeMap by lazy { attributes.associateBy { it.name } }
        val requiredAttributeNames by lazy { attributes.filter { !it.optional }.mapTo(mutableSetOf()) { it.name } }
    }

    data class SimpleAttribute(
        val pointer: SmartPsiElementPointer<XmlTag>,
        val valueType: String,
        val optional: Boolean = false,
        val swap: Boolean = false,
        val description: String? = null,
    )

    data class Attribute(
        val pointer: SmartPsiElementPointer<XmlTag>,
        val name: String,
        val valueType: String,
        val optional: Boolean = false,
        val description: String? = null,
    )
}
