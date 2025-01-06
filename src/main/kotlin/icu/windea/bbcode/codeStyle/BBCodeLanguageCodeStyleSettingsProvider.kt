package icu.windea.bbcode.codeStyle

import com.intellij.application.options.*
import com.intellij.lang.*
import com.intellij.psi.codeStyle.*
import icu.windea.bbcode.*

//com.intellij.application.options.XmlLanguageCodeStyleSettingsProvider

class BBCodeLanguageCodeStyleSettingsProvider: LanguageCodeStyleSettingsProvider() {
    override fun createConfigurable(baseSettings: CodeStyleSettings, modelSettings: CodeStyleSettings): CodeStyleConfigurable {
        return object : CodeStyleAbstractConfigurable(baseSettings, modelSettings, "BBCode") {
            override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel {
                return BBCodeCodeStyleMainPanel(currentSettings, settings)
            }
        }
    }

    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings {
        return BBCodeCodeStyleSettings(settings)
    }

    override fun getLanguage(): Language {
        return BBCodeLanguage
    }

    override fun getCodeSample(settingsType: SettingsType): String {
        return BBCodeConstants.sampleText
    }

    override fun customizeSettings(consumer: CodeStyleSettingsCustomizable, settingsType: SettingsType) {

    }

    override fun customizeDefaults(commonSettings: CommonCodeStyleSettings, indentOptions: CommonCodeStyleSettings.IndentOptions) {
        indentOptions.INDENT_SIZE = 0
    }

    override fun getIndentOptionsEditor(): IndentOptionsEditor {
        return SmartIndentOptionsEditor()
    }
}
