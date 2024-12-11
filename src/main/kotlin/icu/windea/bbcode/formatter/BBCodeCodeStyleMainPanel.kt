package icu.windea.bbcode.formatter

import com.intellij.application.options.*
import com.intellij.psi.codeStyle.*
import icu.windea.bbcode.*

class BBCodeCodeStyleMainPanel(
    currentSettings: CodeStyleSettings,
    settings: CodeStyleSettings
) : TabbedLanguageCodeStylePanel(BBCodeLanguage, currentSettings, settings) {
    override fun initTabs(settings: CodeStyleSettings) {
        addIndentOptionsTab(settings)
        addTab(BBCodeCodeStylePanel(settings))

        for (provider in CodeStyleSettingsProvider.EXTENSION_POINT_NAME.extensionList) {
            if (provider.language === BBCodeLanguage && !provider.hasSettingsPage()) {
                createTab(provider)
            }
        }
    }
}
