package icu.windea.bbcode.codeStyle

import com.intellij.configurationStore.*
import com.intellij.psi.codeStyle.*

@Suppress("PropertyName")
class BBCodeCodeStyleSettings(container: CodeStyleSettings): CustomCodeStyleSettings("BBCode", container) {
    @Property(externalName = "space_around_equals_in_attribute")
    @JvmField
    var SPACE_AROUND_EQUALITY_IN_ATTRIBUTE: Boolean = false

    @Property(externalName = "space_after_tag_name")
    @JvmField
    var SPACE_AFTER_TAG_NAME: Boolean = false

    @Property(externalName = "space_inside_empty_tag")
    @JvmField
    var SPACE_INSIDE_EMPTY_TAG: Boolean = false
}
