package icu.windea.bbcode.formatter

import com.intellij.formatting.*

//com.intellij.lang.xml.XmlFormattingModelBuilder

class BBCodeFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        return FormattingModelProvider.createFormattingModelForPsiFile(
            formattingContext.containingFile,
            BBCodeBlock(formattingContext.psiElement.node, formattingContext.codeStyleSettings),
            formattingContext.codeStyleSettings
        )
    }
}

