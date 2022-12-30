package icu.windea.bbcode.schema

data class BBCodeTagSchema(
    val name: String,
    val title: String,
    val description: String? = null,
    val type: BBCodeTagType = BBCodeTagType.Block,
    val attributes: Set<BBCodeAttributeSchema> = emptySet()
)

