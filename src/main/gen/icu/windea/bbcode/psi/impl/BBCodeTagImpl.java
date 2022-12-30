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
import com.intellij.openapi.util.Iconable.IconFlags;
import javax.swing.Icon;

public class BBCodeTagImpl extends BBCodeNamedElementImpl implements BBCodeTag {

  public BBCodeTagImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull BBCodeVisitor visitor) {
    visitor.visitTag(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof BBCodeVisitor) accept((BBCodeVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<BBCodeAttribute> getAttributeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, BBCodeAttribute.class);
  }

  @Override
  @Nullable
  public BBCodeAttributeValue getAttributeValue() {
    return findChildByClass(BBCodeAttributeValue.class);
  }

  @Override
  @NotNull
  public List<BBCodeTag> getTagList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, BBCodeTag.class);
  }

  @Override
  @NotNull
  public List<BBCodeText> getTextList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, BBCodeText.class);
  }

  @Override
  @NotNull
  public Icon getIcon(@IconFlags int flags) {
    return BBCodePsiImplUtil.getIcon(this, flags);
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
  @Nullable
  public PsiElement getNameIdentifier() {
    return BBCodePsiImplUtil.getNameIdentifier(this);
  }

  @Override
  public int getTextOffset() {
    return BBCodePsiImplUtil.getTextOffset(this);
  }

  @Override
  @Nullable
  public PsiElement getTagName() {
    return BBCodePsiImplUtil.getTagName(this);
  }

  @Override
  @NotNull
  public List<BBCodeAttribute> getAttributes() {
    return BBCodePsiImplUtil.getAttributes(this);
  }

  @Override
  @Nullable
  public String getValue() {
    return BBCodePsiImplUtil.getValue(this);
  }

}
