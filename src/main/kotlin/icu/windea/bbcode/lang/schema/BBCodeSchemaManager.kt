package icu.windea.bbcode.lang.schema

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.dataformat.yaml.*
import com.fasterxml.jackson.module.kotlin.*
import com.intellij.openapi.components.*
import icu.windea.bbcode.locationClass
import icu.windea.bbcode.toUrl

@Service(Service.Level.APP)
class BBCodeSchemaManager {
    private val mapper = YAMLMapper().apply {
        enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        findAndRegisterModules()
    }

    val schemaMap = mapper.readValue<List<BBCodeTagSchema>>("/schema/bbcode.yml".toUrl(locationClass))
        .associateBy { it.name }

    fun isBlockTag(tagName: String): Boolean {
        val schema = schemaMap[tagName] ?: return true
        return schema.type == BBCodeTagType.Block
    }

    fun isPhraseTag(tagName: String): Boolean {
        val schema = schemaMap[tagName] ?: return false
        return schema.type == BBCodeTagType.Phrase
    }

    fun isLineTag(tagName: String): Boolean {
        val schema = schemaMap[tagName] ?: return false
        return schema.type == BBCodeTagType.Line
    }

    companion object {
        @JvmStatic
        fun getInstance() = service<BBCodeSchemaManager>()
    }
}
