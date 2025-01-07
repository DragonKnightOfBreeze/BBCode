package icu.windea.bbcode.test

import com.intellij.testFramework.fixtures.*
import icu.windea.bbcode.*
import icu.windea.bbcode.lang.schema.*

class BBCodeSchemaResolvingTest : BasePlatformTestCase() {
    override fun setUp() {
        super.setUp()
        defaultProject = project
    }

    fun testStandard() {
        val schema = BBCodeSchemaManager.getStandardSchema(project)
        assert(schema != null)
        schema!!
        assert(schema.tags.isNotEmpty())
        assert(schema.tagMap["line"]?.inline == true)
    }
}
