// This is a generated file. Not intended for manual editing.
package icu.windea.bbcode.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static icu.windea.bbcode.psi.BBCodeTypes.*;
import icu.windea.bbcode.psi.*;
import com.intellij.psi.PsiReference;

public class BBCodeAttributeImpl extends BBCodeNamedElementImpl implements BBCodeAttribute {

  public BBCodeAttributeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull BBCodeVisitor visitor) {
    visitor.visitAttribute(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof BBCodeVisitor) accept((BBCodeVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public BBCodeAttributeValue getAttributeValue() {
    return findChildByClass(BBCodeAttributeValue.class);
  }

  @Override
  @NotNull
  public PsiElement getAttributeName() {
    return findNotNullChildByType(ATTRIBUTE_NAME);
  }

  @Override
  @NotNull
  public String getName() {
    return BBCodePsiImplUtil.getName(this);
  }

  @Override
  @NotNull
  public PsiElement setName(@NotNull String name) {
    return BBCodePsiImplUtil.setName(this, name);
  }

  @Override
  @NotNull
  public PsiElement getNameIdentifier() {
    return BBCodePsiImplUtil.getNameIdentifier(this);
  }

  @Override
  public int getTextOffset() {
    return BBCodePsiImplUtil.getTextOffset(this);
  }

  @Override
  @Nullable
  public String getValue() {
    return BBCodePsiImplUtil.getValue(this);
  }

  @Override
  @NotNull
  public PsiReference[] getReferences() {
    return BBCodePsiImplUtil.getReferences(this);
  }

}
