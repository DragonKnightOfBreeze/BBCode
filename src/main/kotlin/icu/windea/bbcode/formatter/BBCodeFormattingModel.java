package icu.windea.bbcode.formatter;

import com.intellij.formatting.Block;
import com.intellij.formatting.FormattingDocumentModel;
import com.intellij.formatting.FormattingModel;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiFile;
import com.intellij.psi.formatter.FormattingDocumentModelImpl;
import com.intellij.psi.formatter.xml.XmlBlock;
import org.jetbrains.annotations.NotNull;

public class BBCodeFormattingModel implements FormattingModel {
    public BBCodeFormattingModel(PsiFile containingFile, XmlBlock block, FormattingDocumentModelImpl documentModel) {
    }

    @Override
    public @NotNull Block getRootBlock() {
        return null;
    }

    @Override
    public @NotNull FormattingDocumentModel getDocumentModel() {
        return null;
    }

    @Override
    public TextRange replaceWhiteSpace(TextRange textRange, String whiteSpace) {
        return null;
    }

    @Override
    public TextRange shiftIndentInsideRange(ASTNode node, TextRange range, int indent) {
        return null;
    }

    @Override
    public void commitChanges() {

    }
}
