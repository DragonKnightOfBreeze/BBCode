// This is a generated file. Not intended for manual editing.
package icu.windea.bbcode.psi.impl;

import icu.windea.bbcode.psi.BBCodeAttribute;
import icu.windea.bbcode.psi.BBCodeTypes;
import icu.windea.bbcode.psi.BBCodeVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import icu.windea.bbcode.psi.*;

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
  @NotNull
  public PsiElement getAttributeName() {
    return findNotNullChildByType(BBCodeTypes.ATTRIBUTE_NAME);
  }

  @Override
  @Nullable
  public PsiElement getAttributeValue() {
    return findChildByType(BBCodeTypes.ATTRIBUTE_VALUE);
  }

  @Override
  @Nullable
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

}
