// This is a generated file. Not intended for manual editing.
package icu.windea.bbcode.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ContributedReferenceHost;
import com.intellij.openapi.util.Iconable.IconFlags;
import com.intellij.psi.PsiReference;
import javax.swing.Icon;

public interface BBCodeTag extends BBCodeNamedElement, ContributedReferenceHost {

  @NotNull
  List<BBCodeAttribute> getAttributeList();

  @Nullable
  BBCodeAttributeValue getAttributeValue();

  @NotNull
  List<BBCodeTag> getTagList();

  @NotNull
  List<BBCodeText> getTextList();

  @NotNull
  Icon getIcon(@IconFlags int flags);

  @NotNull
  String getName();

  @NotNull
  PsiElement setName(@NotNull String name);

  @Nullable
  PsiElement getNameIdentifier();

  int getTextOffset();

  @Nullable
  String getValue();

  @NotNull
  PsiReference[] getReferences();

}
