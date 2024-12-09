// This is a generated file. Not intended for manual editing.
package icu.windea.bbcode.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralValue;
import com.intellij.psi.ContributedReferenceHost;

public class BBCodeVisitor extends PsiElementVisitor {

  public void visitAttribute(@NotNull BBCodeAttribute o) {
    visitNamedElement(o);
    // visitContributedReferenceHost(o);
  }

  public void visitAttributeValue(@NotNull BBCodeAttributeValue o) {
    visitPsiLiteralValue(o);
  }

  public void visitTag(@NotNull BBCodeTag o) {
    visitNamedElement(o);
    // visitContributedReferenceHost(o);
  }

  public void visitText(@NotNull BBCodeText o) {
    visitPsiLiteralValue(o);
  }

  public void visitPsiLiteralValue(@NotNull PsiLiteralValue o) {
    visitElement(o);
  }

  public void visitNamedElement(@NotNull BBCodeNamedElement o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
