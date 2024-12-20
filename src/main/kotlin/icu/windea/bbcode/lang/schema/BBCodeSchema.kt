package icu.windea.bbcode.lang.schema

import com.intellij.psi.*
import com.intellij.psi.xml.*

data class BBCodeSchema(
    val referenceUrl: String,
    val tags: List<Tag>,
) {
    val tagMap by lazy { tags.associateBy { it.name } }

    data class Tag(
        val pointer: SmartPsiElementPointer<XmlTag>,
        val name: String,
        val parentNames: Set<String>? = null,
        val childNames: Set<String>? = null,
        val textType: String? = null,
        val inline: Boolean = false,
        val attribute: SimpleAttribute? = null,
        val attributes: List<Attribute> = emptyList(),
        val doc: String? = null,
        val urls: Set<String> = emptySet(),
    ) {
        val attributeMap by lazy { attributes.associateBy { it.name } }
        val requiredAttributeNames by lazy { attributes.filter { !it.optional }.mapTo(mutableSetOf()) { it.name } }
    }

    data class SimpleAttribute(
        val pointer: SmartPsiElementPointer<XmlTag>,
        val type: String,
        val optional: Boolean = false,
        val doc: String? = null,
    )

    data class Attribute(
        val pointer: SmartPsiElementPointer<XmlTag>,
        val name: String,
        val type: String,
        val optional: Boolean = false,
        val swap: Boolean = false,
        val doc: String? = null,
    )
}
