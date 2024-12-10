package icu.windea.bbcode.lang.schema

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.psi.*
import com.intellij.psi.util.*
import icu.windea.bbcode.*
import icu.windea.bbcode.psi.*
import icu.windea.bbcode.util.*

object BBCodeSchemaManager {
    object Keys : KeyRegistry() {
        val schemaForTag by createKey<CachedValue<BBCodeSchema.Tag>>(Keys)
        val schemaForAttribute by createKey<CachedValue<BBCodeSchema.Attribute>>(Keys)
    }
    
    fun getStandardSchema(project: Project) : BBCodeSchema? {
        return project.service<BBCodeSchemaProvider>().standardSchema
    }

    fun getSchema(project: Project): BBCodeSchema? {
        return getStandardSchema(project)
    }
    
    fun getSchema(file: PsiFile): BBCodeSchema? {
        return getStandardSchema(file.project)
    }

    fun resolveForTag(element: BBCodeTag): BBCodeSchema.Tag? {
        return CachedValuesManager.getCachedValue(element, Keys.schemaForTag) {
            val file = element.containingFile
            val value = doResolveForTag(element, file)
            CachedValueProvider.Result.create(value, element)
        }
    }

    private fun doResolveForTag(element: BBCodeTag, file: PsiFile?): BBCodeSchema.Tag? {
        if (file == null) return null
        val schema = getSchema(file) ?: return null
        val tagName = element.name.orNull() ?: return null
        return schema.tagMap[tagName]
    }
    
    fun resolveForAttribute(element: BBCodeAttribute): BBCodeSchema.Attribute? {
        return CachedValuesManager.getCachedValue(element, Keys.schemaForAttribute) {
            val tag = element.parentOfType<BBCodeTag>(withSelf = false)
            val value = doResolveForAttribute(element, tag)
            CachedValueProvider.Result.create(value, tag ?: element)
        }
    }
    
    private fun doResolveForAttribute(element: BBCodeAttribute, tag: BBCodeTag?): BBCodeSchema.Attribute? {
        if(tag == null) return null
        val tagSchema = resolveForTag(tag) ?: return null
        val attributeName = element.name.orNull() ?: return null
        return tagSchema.attributeMap[attributeName]
    }
    
    fun isEmptyTag(tagName: String?): Boolean {
        if(tagName.isNullOrEmpty()) return false
        val schema = getSchema(ProjectManager.getInstance().defaultProject) ?: return false
        val tagSchema = schema.tagMap[tagName] ?: return false
        return tagSchema.inline
    }
}
