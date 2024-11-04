package icu.windea.bbcode.lang.schema

import com.intellij.psi.SmartPsiElementPointer
import com.intellij.psi.xml.XmlTag

data class BBCodeSchema(
    val referenceUrl: String,
    val tags: List<Tag>,
) {
    data class Tag(
        val pointer: SmartPsiElementPointer<XmlTag>,
        val id: String,
        val childIds: Set<String> = emptySet(),
        val textType: String? = null,
        val inline: Boolean = false,
        val attribute: SimpleAttribute? = null,
        val attributes: List<Attribute> = emptyList(),
        val doc: String? = null,
        val urls: Set<String> = emptySet(),
    )

    data class SimpleAttribute(
        val pointer: SmartPsiElementPointer<XmlTag>,
        val type: String,
        val optional: Boolean = false,
    )

    data class Attribute(
        val pointer: SmartPsiElementPointer<XmlTag>,
        val id: String,
        val type: String,
        val optional: Boolean = false,
        val swap: Boolean = false,
    )
}
