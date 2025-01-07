package icu.windea.bbcode.test

import com.intellij.lang.*
import com.intellij.lang.xml.*
import com.intellij.mock.*
import com.intellij.psi.*
import com.intellij.psi.impl.*
import com.intellij.testFramework.*
import com.intellij.util.indexing.*
import icu.windea.bbcode.*
import icu.windea.bbcode.lang.schema.*
import icu.windea.bbcode.psi.*

class BBCodeParsingTest : ParsingTestCase("", "bbcode", BBCodeParserDefinition()) {
    override fun setUp() {
        super.setUp()
        defaultProject = project
        doSetUp()
    }

    private fun doSetUp() {
        addExplicitExtension(LanguageASTFactory.INSTANCE, XMLLanguage.INSTANCE, XmlASTFactory())
        registerParserDefinition(XMLParserDefinition())
        application.registerService(FileBasedIndex::class.java, EmptyFileBasedIndex())
        project.registerService(PsiFileFactory::class.java, PsiFileFactoryImpl(project))
        project.registerService(SmartPointerManager::class.java, MockSmartPointerManager())
        project.registerService(BBCodeSchemaProvider::class.java, BBCodeSchemaProvider(project))
    }

    fun testSample() {
        doTest(true)
    }

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    override fun includeRanges(): Boolean {
        return true
    }
}
