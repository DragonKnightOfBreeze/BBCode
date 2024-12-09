package icu.windea.bbcode.references

import com.intellij.openapi.util.*
import com.intellij.psi.*
import com.intellij.util.*
import icu.windea.bbcode.lang.schema.*
import icu.windea.bbcode.psi.*

@Suppress("UNUSED_PARAMETER")
class BBCodeSchemaBasedReferenceProvider : PsiReferenceProvider() {
    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<out PsiReference> {
        return when (element) {
            is BBCodeTag -> getReferencesForTag(element, context)
            is BBCodeAttribute -> getReferencesForAttribute(element, context)
            else -> PsiReference.EMPTY_ARRAY
        }
    }

    private fun getReferencesForTag(element: BBCodeTag, context: ProcessingContext): Array<out PsiReference> {
        val range = element.tagName?.textRangeInParent ?: return PsiReference.EMPTY_ARRAY
        val reference = TagReference(element, range)
        return arrayOf(reference)
    }

    private fun getReferencesForAttribute(element: BBCodeAttribute, context: ProcessingContext): Array<out PsiReference> {
        val range = element.attributeName.textRangeInParent
        val reference = AttributeReference(element, range)
        return arrayOf(reference)
    }

    class TagReference(
        element: BBCodeTag,
        rangeInElement: TextRange,
    ) : PsiReferenceBase<BBCodeTag>(element, rangeInElement) {
        override fun handleElementRename(newElementName: String): PsiElement {
            throw IncorrectOperationException()
        }

        override fun resolve(): PsiElement? {
            val schema = BBCodeSchemaManager.resolveForTag(element)
            return schema?.pointer?.element
        }
    }

    class AttributeReference(
        element: BBCodeAttribute,
        rangeInElement: TextRange,
    ) : PsiReferenceBase<BBCodeAttribute>(element, rangeInElement) {
        override fun handleElementRename(newElementName: String): PsiElement {
            throw IncorrectOperationException()
        }

        override fun resolve(): PsiElement? {
            val schema = BBCodeSchemaManager.resolveForAttribute(element)
            return schema?.pointer?.element
        }
    }
}
