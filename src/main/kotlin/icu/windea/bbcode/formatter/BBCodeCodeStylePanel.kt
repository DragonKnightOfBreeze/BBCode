package icu.windea.bbcode.formatter

import com.intellij.application.options.*
import com.intellij.application.options.codeStyle.*
import com.intellij.ide.highlighter.*
import com.intellij.openapi.editor.colors.*
import com.intellij.openapi.editor.highlighter.*
import com.intellij.openapi.fileTypes.*
import com.intellij.openapi.options.*
import com.intellij.psi.codeStyle.*
import com.intellij.psi.codeStyle.CodeStyleSettings.*
import com.intellij.psi.formatter.xml.*
import com.intellij.ui.*
import com.intellij.ui.components.*
import com.intellij.xml.*
import icu.windea.bbcode.*
import icu.windea.bbcode.editor.*
import org.jetbrains.annotations.*
import java.util.*
import javax.swing.*

//com.intellij.application.options.CodeStyleXmlPanel

class BBCodeCodeStylePanel(settings: CodeStyleSettings) : CodeStyleAbstractPanel(settings) {
    private var myPanel: JPanel? = null
    private var myPreviewPanel: JPanel? = null

    private val mySpacesAroundEquality: JCheckBox? = null
    private val mySpacesAfterTagName: JCheckBox? = null
    private val myInEmptyTag: JCheckBox? = null
    private val myRightMarginForm: RightMarginForm? = null

    init {
        installPreviewPanel(myPreviewPanel!!)
        addPanelToWatch(myPanel)
    }

    override fun createHighlighter(scheme: EditorColorsScheme): EditorHighlighter {
        return HighlighterFactory.createHighlighter(BBCodeSyntaxHighlighter(), scheme)
    }

    override fun getRightMargin(): Int {
        return 60
    }

    @Throws(ConfigurationException::class)
    override fun apply(settings: CodeStyleSettings) {
        val customSettings = settings.getCustomSettings(BBCodeCodeStyleSettings::class.java)
        customSettings.SPACE_AROUND_EQUALITY_IN_ATTRIBUTE = mySpacesAroundEquality!!.isSelected
        customSettings.SPACE_AFTER_TAG_NAME = mySpacesAfterTagName!!.isSelected
        customSettings.SPACE_INSIDE_EMPTY_TAG = myInEmptyTag!!.isSelected
        myRightMarginForm!!.apply(settings)
    }

    override fun resetImpl(settings: CodeStyleSettings) {
        val customSettings = settings.getCustomSettings(BBCodeCodeStyleSettings::class.java)
        mySpacesAfterTagName!!.isSelected = customSettings.SPACE_AFTER_TAG_NAME
        mySpacesAroundEquality!!.isSelected = customSettings.SPACE_AROUND_EQUALITY_IN_ATTRIBUTE
        myInEmptyTag!!.isSelected = customSettings.SPACE_INSIDE_EMPTY_TAG
        myRightMarginForm!!.reset(settings)
    }

    override fun isModified(settings: CodeStyleSettings): Boolean {
        val customSettings = settings.getCustomSettings(BBCodeCodeStyleSettings::class.java)
        if (customSettings.SPACE_AROUND_EQUALITY_IN_ATTRIBUTE != mySpacesAroundEquality!!.isSelected) {
            return true
        }
        if (customSettings.SPACE_AFTER_TAG_NAME != mySpacesAfterTagName!!.isSelected) {
            return true
        }
        if (customSettings.SPACE_INSIDE_EMPTY_TAG != myInEmptyTag!!.isSelected) {
            return true
        }
        return myRightMarginForm!!.isModified(settings)
    }

    override fun getPanel(): JComponent? {
        return myPanel
    }

    override fun getPreviewText(): String {
        return BBCodeConstants.sampleText
    }

    override fun getFileType(): FileType {
        return BBCodeFileType
    }
}
