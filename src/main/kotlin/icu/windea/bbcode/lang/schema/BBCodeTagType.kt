package icu.windea.bbcode.lang.schema

enum class BBCodeTagType {
    Default,
    Inline,
    Empty,
    Line,
    ;

    companion object {
        fun resolve(name: String): BBCodeTagType? {
            return entries.find { it.name.equals(name, true) }
        }
    }
}
