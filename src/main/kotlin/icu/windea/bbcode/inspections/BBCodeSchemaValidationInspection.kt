package icu.windea.bbcode.inspections

import com.intellij.codeInsight.intention.preview.*
import com.intellij.codeInspection.*
import com.intellij.codeInspection.options.*
import com.intellij.openapi.project.*
import com.intellij.profile.codeInspection.*
import com.intellij.psi.*
import com.intellij.psi.util.*
import com.intellij.util.containers.*
import icu.windea.bbcode.*
import icu.windea.bbcode.intentions.*
import icu.windea.bbcode.lang.schema.*
import icu.windea.bbcode.psi.*
import icu.windea.bbcode.util.*

class BBCodeSchemaValidationInspection : LocalInspectionTool() {
    @JvmField
    var customTags: OrderedSet<String> = OrderedSet()
    @JvmField
    var customAttributes: OrderedSet<String> = OrderedSet()

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : BBCodeVisitor() {
            override fun visitTag(element: BBCodeTag) {
                val name = element.name.orNull() ?: return
                if (name in customTags) return
                val nameElement = BBCodeManager.getStartTagNameElement(element) ?: return
                val schema = BBCodeSchemaManager.resolveForTag(element)

                if (schema == null) {
                    val message = BBCodeBundle.message("bbcode.inspection.messages.tag.unknown", name)
                    val fixes = buildList {
                        this += AddToCustomTagFix(name)
                    }
                    holder.registerProblem(nameElement, message, *fixes.toTypedArray())
                    return
                }

                //check whether tag should be inline

                val isInline = BBCodeManager.isInlineTag(element)
                if (schema.inline != isInline) {
                    val message = if (schema.inline) BBCodeBundle.message("bbcode.inspection.messages.tag.inline", name)
                    else BBCodeBundle.message("bbcode.inspection.messages.tag.notInline", name)
                    holder.registerProblem(nameElement, message)
                }

                //check whether tag is unexpected
                val parentTag = element.parentOfType<BBCodeTag>(withSelf = false)
                val parentTagName = parentTag?.name
                val parentSchema = parentTag?.let { BBCodeSchemaManager.resolveForTag(it) }
                val parentFrom = schema.parentNames
                val childrenTo = parentSchema?.childNames
                if ((parentFrom != null && parentTagName !in parentFrom) || (childrenTo != null && name !in childrenTo)) {
                    val message = BBCodeBundle.message("bbcode.inspection.messages.tag.unexpected", name)
                    holder.registerProblem(nameElement, message)
                }

                //check missing simple attribute

                val attributeMap = element.attributeList.associateBy { it.name }
                val missingKeys = schema.requiredAttributeNames - attributeMap.keys
                if (missingKeys.isNotEmpty()) {
                    val s = missingKeys.joinToString(", ", "'", "'")
                    val message = BBCodeBundle.message("bbcode.inspection.messages.attribute.missing", s)
                    holder.registerProblem(nameElement, message)
                }

                //check unexpected simple attribute & check missing simple attribute

                val simpleAttributeRange = BBCodeManager.getSimpleAttributeRange(element)
                if (schema.attribute == null && simpleAttributeRange != null) {
                    val message = BBCodeBundle.message("bbcode.inspection.messages.simpleAttribute.unexpected")
                    val fixes = buildList {
                        this += RemoveSimpleAttributeIntentionFix(true)
                    }
                    holder.registerProblem(element, simpleAttributeRange.shiftLeft(element.startOffset), message, *fixes.toTypedArray())
                }
                if (schema.attribute?.optional == false && simpleAttributeRange == null) {
                    val message = BBCodeBundle.message("bbcode.inspection.messages.simpleAttribute.missing")
                    holder.registerProblem(nameElement, message)
                }
            }

            override fun visitAttribute(element: BBCodeAttribute) {
                val name = element.name.orNull() ?: return
                if (name in customTags) return
                val nameElement = BBCodeManager.getAttributeNameElement(element) ?: return
                val schema = BBCodeSchemaManager.resolveForAttribute(element)

                if (schema == null) {
                    val message = BBCodeBundle.message("bbcode.inspection.messages.attribute.unexpected", name)
                    val fixes = buildList {
                        this += AddToCustomAttributeFix(name)
                        this += RemoveAttributeIntentionFix(name)
                    }
                    holder.registerProblem(nameElement, message, *fixes.toTypedArray())
                    return
                }
            }
        }
    }

    override fun getOptionsPane(): OptPane {
        return OptPane.pane(
            OptPane.stringList("customTags", BBCodeBundle.message("bbcode.inspection.options.customTags")),
            OptPane.stringList("customAttributes", BBCodeBundle.message("bbcode.inspection.options.customAttributes")),
        )
    }

    class AddToCustomTagFix(private val name: String) : LocalQuickFix {
        override fun getFamilyName(): String {
            return BBCodeBundle.message("bbcode.inspection.fix.addTo.customTags", name)
        }

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val element = descriptor.psiElement
            val profile = InspectionProjectProfileManager.getInstance(project).currentProfile
            val inspectionKey = BBCodeInspectionKeys.SchemaValidation
            profile.modifyToolSettings(inspectionKey, element) { it.customTags += name }
        }

        override fun startInWriteAction(): Boolean {
            return false
        }

        override fun generatePreview(project: Project, previewDescriptor: ProblemDescriptor): IntentionPreviewInfo {
            return IntentionPreviewInfo.EMPTY
        }
    }

    class AddToCustomAttributeFix(private val name: String) : LocalQuickFix {
        override fun getFamilyName(): String {
            return BBCodeBundle.message("bbcode.inspection.fix.addTo.customAttributes", name)
        }

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val element = descriptor.psiElement
            val profile = InspectionProjectProfileManager.getInstance(project).currentProfile
            val inspectionKey = BBCodeInspectionKeys.SchemaValidation
            profile.modifyToolSettings(inspectionKey, element) { it.customAttributes += name }
        }

        override fun startInWriteAction(): Boolean {
            return false
        }

        override fun generatePreview(project: Project, previewDescriptor: ProblemDescriptor): IntentionPreviewInfo {
            return IntentionPreviewInfo.EMPTY
        }
    }
}
