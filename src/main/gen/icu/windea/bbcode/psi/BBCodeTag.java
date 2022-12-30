// This is a generated file. Not intended for manual editing.
package icu.windea.bbcode.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.openapi.util.Iconable.IconFlags;
import javax.swing.Icon;

public interface BBCodeTag extends BBCodeNamedElement {

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

  @Nullable
  String getName();

  @NotNull
  PsiElement setName(@NotNull String name);

  @Nullable
  PsiElement getNameIdentifier();

  int getTextOffset();

  @Nullable
  PsiElement getTagName();

  @NotNull
  List<BBCodeAttribute> getAttributes();

  @Nullable
  String getValue();

}
