// This is a generated file. Not intended for manual editing.
package icu.windea.bbcode.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static icu.windea.bbcode.psi.BBCodeTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import icu.windea.bbcode.psi.*;

public class BBCodeAttributeValueImpl extends ASTWrapperPsiElement implements BBCodeAttributeValue {

  public BBCodeAttributeValueImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull BBCodeVisitor visitor) {
    visitor.visitAttributeValue(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof BBCodeVisitor) accept((BBCodeVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getAttributeValueToken() {
    return findNotNullChildByType(ATTRIBUTE_VALUE_TOKEN);
  }

  @Override
  @NotNull
  public String getValue() {
    return BBCodePsiImplUtil.getValue(this);
  }

}
