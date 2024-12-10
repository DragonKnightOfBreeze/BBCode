// This is a generated file. Not intended for manual editing.
package icu.windea.bbcode.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ContributedReferenceHost;
import com.intellij.psi.PsiReference;

public interface BBCodeAttribute extends BBCodeNamedElement, ContributedReferenceHost {

  @Nullable
  BBCodeAttributeValue getAttributeValue();

  @NotNull
  PsiElement getAttributeName();

  @NotNull
  String getName();

  @NotNull
  PsiElement setName(@NotNull String name);

  @NotNull
  PsiElement getNameIdentifier();

  int getTextOffset();

  @Nullable
  String getValue();

  @NotNull
  PsiReference[] getReferences();

}
