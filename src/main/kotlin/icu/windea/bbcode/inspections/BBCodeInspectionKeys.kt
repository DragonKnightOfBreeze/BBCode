package icu.windea.bbcode.inspections

import icu.windea.bbcode.util.*

object BBCodeInspectionKeys: KeyRegistry() {
    val SchemaValidation by createKey<BBCodeSchemaValidationInspection>("BBCodeSchemaValidation")
}
