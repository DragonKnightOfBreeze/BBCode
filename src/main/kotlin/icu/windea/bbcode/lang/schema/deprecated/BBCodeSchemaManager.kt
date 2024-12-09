package icu.windea.bbcode.lang.schema.deprecated

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.dataformat.yaml.*
import com.fasterxml.jackson.module.kotlin.*
import com.intellij.openapi.components.*
import icu.windea.bbcode.*

@Service(Service.Level.APP)
class BBCodeSchemaManager {
    private val mapper = YAMLMapper().apply {
        enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        findAndRegisterModules()
    }

    val schemaMap = mapper.readValue<List<BBCodeTagSchema>>("/schema/bbcode.yml".toClasspathUrl(this::class.java))
        .associateBy { it.name }
    
    companion object {
        @JvmStatic
        fun getInstance() = service<BBCodeSchemaManager>()
    }
}
