// This is a generated file. Not intended for manual editing.
package icu.windea.bbcode.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface BBCodeAttribute extends BBCodeNamedElement {

  @Nullable
  BBCodeAttributeValue getAttributeValue();

  @NotNull
  PsiElement getAttributeName();

  @Nullable
  String getName();

  @NotNull
  PsiElement setName(@NotNull String name);

  @NotNull
  PsiElement getNameIdentifier();

  int getTextOffset();

  @Nullable
  String getValue();

}
