// This is a generated file. Not intended for manual editing.
package icu.windea.bbcode.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import icu.windea.bbcode.psi.impl.*;

public interface BBCodeTypes {

  IElementType ATTRIBUTE = new BBCodeElementType("ATTRIBUTE");
  IElementType ATTRIBUTE_VALUE = new BBCodeElementType("ATTRIBUTE_VALUE");
  IElementType TAG = new BBCodeElementType("TAG");
  IElementType TEXT = new BBCodeElementType("TEXT");

  IElementType ATTRIBUTE_NAME = new BBCodeTokenType("ATTRIBUTE_NAME");
  IElementType ATTRIBUTE_VALUE_TOKEN = new BBCodeTokenType("ATTRIBUTE_VALUE_TOKEN");
  IElementType EMPTY_TAG_PREFIX_END = new BBCodeTokenType("EMPTY_TAG_PREFIX_END");
  IElementType EQUAL_SIGN = new BBCodeTokenType("EQUAL_SIGN");
  IElementType TAG_NAME = new BBCodeTokenType("TAG_NAME");
  IElementType TAG_PREFIX_END = new BBCodeTokenType("TAG_PREFIX_END");
  IElementType TAG_PREFIX_START = new BBCodeTokenType("TAG_PREFIX_START");
  IElementType TAG_SUFFIX_END = new BBCodeTokenType("TAG_SUFFIX_END");
  IElementType TAG_SUFFIX_START = new BBCodeTokenType("TAG_SUFFIX_START");
  IElementType TEXT_TOKEN = new BBCodeTokenType("TEXT_TOKEN");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ATTRIBUTE) {
        return new BBCodeAttributeImpl(node);
      }
      else if (type == ATTRIBUTE_VALUE) {
        return new BBCodeAttributeValueImpl(node);
      }
      else if (type == TAG) {
        return new BBCodeTagImpl(node);
      }
      else if (type == TEXT) {
        return new BBCodeTextImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
