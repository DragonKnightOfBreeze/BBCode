package icu.windea.bbcode.lang.schema

enum class BBCodeTagType {
    Block,
    Inline,
    Line,
    ;

    companion object {
        fun resolve(name: String): BBCodeTagType? {
            return entries.find { it.name.equals(name, true) }
        }
    }
}
