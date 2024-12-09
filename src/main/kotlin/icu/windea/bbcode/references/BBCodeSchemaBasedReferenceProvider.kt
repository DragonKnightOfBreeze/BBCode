package icu.windea.bbcode.references

import com.intellij.openapi.util.*
import com.intellij.psi.*
import com.intellij.psi.util.*
import com.intellij.util.*
import icu.windea.bbcode.lang.schema.*
import icu.windea.bbcode.psi.*

@Suppress("UNUSED_PARAMETER")
class BBCodeSchemaBasedReferenceProvider : PsiReferenceProvider() {
    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<out PsiReference> {
        return when (element.elementType) {
            BBCodeTypes.TAG_NAME -> getReferencesForTagName(element, context)
            BBCodeTypes.ATTRIBUTE_NAME -> getReferencesForAttributeName(element, context)
            else -> PsiReference.EMPTY_ARRAY
        }
    }

    private fun getReferencesForTagName(element: PsiElement, context: ProcessingContext): Array<out PsiReference> {
        val tag = element.parentOfType<BBCodeTag>(withSelf = false) ?: return PsiReference.EMPTY_ARRAY
        val range = tag.tagName?.textRangeInParent ?: return PsiReference.EMPTY_ARRAY
        val reference = TagReference(tag, range)
        return arrayOf(reference)
    }

    private fun getReferencesForAttributeName(element: PsiElement, context: ProcessingContext): Array<out PsiReference> {
        val attribute = element.parentOfType<BBCodeAttribute>(withSelf = false) ?: return PsiReference.EMPTY_ARRAY
        val range = attribute.attributeName.textRangeInParent
        val reference = AttributeReference(attribute, range)
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
