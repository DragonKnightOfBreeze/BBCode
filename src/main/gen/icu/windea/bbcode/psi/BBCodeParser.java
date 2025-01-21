// This is a generated file. Not intended for manual editing.
package icu.windea.bbcode.psi;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static icu.windea.bbcode.psi.BBCodeTypes.*;
import static icu.windea.bbcode.psi.BBCodeParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;
import static com.intellij.lang.WhitespacesBinders.*;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class BBCodeParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return root(b, l + 1);
  }

  /* ********************************************************** */
  // ATTRIBUTE_NAME EQUAL_SIGN attribute_value
  public static boolean attribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute")) return false;
    if (!nextTokenIs(b, ATTRIBUTE_NAME)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ATTRIBUTE, null);
    r = consumeTokens(b, 1, ATTRIBUTE_NAME, EQUAL_SIGN);
    p = r; // pin = 1
    r = r && attribute_value(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // attribute +
  static boolean attribute_group(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_group")) return false;
    if (!nextTokenIs(b, ATTRIBUTE_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attribute(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!attribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attribute_group", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ATTRIBUTE_VALUE_TOKEN
  public static boolean attribute_value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_value")) return false;
    if (!nextTokenIs(b, ATTRIBUTE_VALUE_TOKEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ATTRIBUTE_VALUE_TOKEN);
    exit_section_(b, m, ATTRIBUTE_VALUE, r);
    return r;
  }

  /* ********************************************************** */
  // simple_attribute | attribute_group
  static boolean attributes(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attributes")) return false;
    if (!nextTokenIs(b, "", ATTRIBUTE_NAME, EQUAL_SIGN)) return false;
    boolean r;
    r = simple_attribute(b, l + 1);
    if (!r) r = attribute_group(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // (tag | text) *
  static boolean root(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root")) return false;
    while (true) {
      int c = current_position_(b);
      if (!root_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "root", c)) break;
    }
    return true;
  }

  // tag | text
  private static boolean root_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = tag(b, l + 1);
    if (!r) r = text(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // EQUAL_SIGN attribute_value
  static boolean simple_attribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_attribute")) return false;
    if (!nextTokenIs(b, EQUAL_SIGN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, EQUAL_SIGN);
    p = r; // pin = 1
    r = r && attribute_value(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // tag_prefix (<<isIncompleteTag>> | <<isInlineTag>> | <<isLineTag>> tag_body <<exitLineTag>> | tag_body tag_suffix)
  public static boolean tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag")) return false;
    if (!nextTokenIs(b, TAG_PREFIX_START)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG, null);
    r = tag_prefix(b, l + 1);
    p = r; // pin = 1
    r = r && tag_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // <<isIncompleteTag>> | <<isInlineTag>> | <<isLineTag>> tag_body <<exitLineTag>> | tag_body tag_suffix
  private static boolean tag_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = isIncompleteTag(b, l + 1);
    if (!r) r = isInlineTag(b, l + 1);
    if (!r) r = tag_1_2(b, l + 1);
    if (!r) r = tag_1_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // <<isLineTag>> tag_body <<exitLineTag>>
  private static boolean tag_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_1_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = isLineTag(b, l + 1);
    r = r && tag_body(b, l + 1);
    r = r && exitLineTag(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // tag_body tag_suffix
  private static boolean tag_1_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_1_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = tag_body(b, l + 1);
    r = r && tag_suffix(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (&<<checkTagBody>> (tag | text)) *
  static boolean tag_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_body")) return false;
    while (true) {
      int c = current_position_(b);
      if (!tag_body_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tag_body", c)) break;
    }
    return true;
  }

  // &<<checkTagBody>> (tag | text)
  private static boolean tag_body_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_body_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = tag_body_0_0(b, l + 1);
    r = r && tag_body_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &<<checkTagBody>>
  private static boolean tag_body_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_body_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = checkTagBody(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // tag | text
  private static boolean tag_body_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_body_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = tag(b, l + 1);
    if (!r) r = text(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_PREFIX_START <<putTagName>> TAG_NAME attributes? (TAG_PREFIX_END | EMPTY_TAG_PREFIX_END)
  static boolean tag_prefix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_prefix")) return false;
    if (!nextTokenIs(b, TAG_PREFIX_START)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, TAG_PREFIX_START);
    p = r; // pin = 1
    r = r && report_error_(b, putTagName(b, l + 1));
    r = p && report_error_(b, consumeToken(b, TAG_NAME)) && r;
    r = p && report_error_(b, tag_prefix_3(b, l + 1)) && r;
    r = p && tag_prefix_4(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // attributes?
  private static boolean tag_prefix_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_prefix_3")) return false;
    attributes(b, l + 1);
    return true;
  }

  // TAG_PREFIX_END | EMPTY_TAG_PREFIX_END
  private static boolean tag_prefix_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_prefix_4")) return false;
    boolean r;
    r = consumeToken(b, TAG_PREFIX_END);
    if (!r) r = consumeToken(b, EMPTY_TAG_PREFIX_END);
    return r;
  }

  /* ********************************************************** */
  // TAG_SUFFIX_START TAG_NAME TAG_SUFFIX_END
  static boolean tag_suffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_suffix")) return false;
    if (!nextTokenIs(b, TAG_SUFFIX_START)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeTokens(b, 1, TAG_SUFFIX_START, TAG_NAME, TAG_SUFFIX_END);
    p = r; // pin = 1
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // TEXT_TOKEN +
  public static boolean text(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "text")) return false;
    if (!nextTokenIs(b, TEXT_TOKEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TEXT_TOKEN);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, TEXT_TOKEN)) break;
      if (!empty_element_parsed_guard_(b, "text", c)) break;
    }
    register_hook_(b, WS_BINDERS, GREEDY_LEFT_BINDER, GREEDY_RIGHT_BINDER);
    exit_section_(b, m, TEXT, r);
    return r;
  }

}
