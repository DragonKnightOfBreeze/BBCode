package icu.windea.bbcode.references

import com.intellij.patterns.*
import com.intellij.patterns.PlatformPatterns.*
import com.intellij.psi.*
import icu.windea.bbcode.psi.*

class BBCodeSchemaBasedReferenceContributor: PsiReferenceContributor() {
    private val pattern = StandardPatterns.or(
        psiElement(BBCodeTag::class.java),
        psiElement(BBCodeAttribute::class.java),
    )
    
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(pattern, BBCodeSchemaBasedReferenceProvider())
    }
}
