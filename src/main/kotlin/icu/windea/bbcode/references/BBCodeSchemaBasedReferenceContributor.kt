package icu.windea.bbcode.references

import com.intellij.patterns.*
import com.intellij.patterns.PlatformPatterns.*
import com.intellij.psi.*
import icu.windea.bbcode.psi.BBCodeTypes.*

class BBCodeSchemaBasedReferenceContributor: PsiReferenceContributor() {
    private val pattern = StandardPatterns.or(
        psiElement(TAG_NAME).afterLeaf(psiElement(TAG_PREFIX_START)),
        psiElement(ATTRIBUTE_NAME),
    )
    
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(pattern, BBCodeSchemaBasedReferenceProvider())
    }
}
