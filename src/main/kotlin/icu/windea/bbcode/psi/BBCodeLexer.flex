package icu.windea.bbcode.psi;

import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import icu.windea.bbcode.lang.schema.BBCodeSchemaManager;

import static com.intellij.psi.TokenType.*;
import static icu.windea.bbcode.psi.BBCodeTypes.*;

%%

%{
  public BBCodeLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class BBCodeLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

%state WAITING_TAG_PREFIX
%state WAITING_TAG_PREFIX_END
%state WAITING_ATTRIBUTES
%state WAITING_ATTRIBUTE_NAME
%state WAITING_EQUAL_SIGN
%state WAITING_ATTRIBUTE_VALUE
%state WAITING_SIMPLE_ATTRIBUTE_VALUE
%state WAITING_TAG_BODY
%state WAITING_TAG_SUFFIX

%{
    private String tagName = null;
%}

EOL=\R
WHITE_SPACE=\s+

TAG_NAME=[\w*\-_]+
ATTRIBUTE_NAME=[\w*\-_]+
ATTRIBUTE_VALUE=([^=\[\]\s\"][^=\[\]\s]*)|(\"([^\"\\]|\\.)*\"?)
TEXT_TOKEN=([^\[\]\s]|\\\S)+

%%
<YYINITIAL> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  "[/" { yybegin(WAITING_TAG_SUFFIX); return TAG_SUFFIX_START; }
  "[" { yybegin(WAITING_TAG_PREFIX); return TAG_PREFIX_START; }
  {TEXT_TOKEN} { return TEXT_TOKEN; }
}
<WAITING_TAG_PREFIX> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  {TAG_NAME} { tagName = yytext().toString(); yybegin(WAITING_ATTRIBUTES); return TAG_NAME; }
  "]" { yypushback(yylength()); yybegin(WAITING_TAG_PREFIX_END); }
  "[" { yybegin(WAITING_TAG_PREFIX); return TAG_PREFIX_START; }
}
<WAITING_ATTRIBUTES> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  "=" { yybegin(WAITING_SIMPLE_ATTRIBUTE_VALUE); return EQUAL_SIGN; }
  {ATTRIBUTE_NAME} { yybegin(WAITING_EQUAL_SIGN); return ATTRIBUTE_NAME; }
  "]" { yypushback(yylength()); yybegin(WAITING_TAG_PREFIX_END); }
  "[" { yybegin(WAITING_TAG_PREFIX); return TAG_PREFIX_START; }
}
<WAITING_ATTRIBUTE_NAME> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  {ATTRIBUTE_NAME} { yybegin(WAITING_EQUAL_SIGN); return ATTRIBUTE_NAME; }
  "]" { yypushback(yylength()); yybegin(WAITING_TAG_PREFIX_END); }
  "[" { yybegin(WAITING_TAG_PREFIX); return TAG_PREFIX_START; }
}
<WAITING_SIMPLE_ATTRIBUTE_VALUE> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  {ATTRIBUTE_VALUE} { yybegin(WAITING_TAG_PREFIX_END); return ATTRIBUTE_VALUE_TOKEN; }
  "]" { yypushback(yylength()); yybegin(WAITING_TAG_PREFIX_END); }
  "[" { yybegin(WAITING_TAG_PREFIX); return TAG_PREFIX_START; }
}
<WAITING_EQUAL_SIGN> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  "=" { yybegin(WAITING_ATTRIBUTE_VALUE); return EQUAL_SIGN; }
  "]" { yypushback(yylength()); yybegin(WAITING_TAG_PREFIX_END); }
  "[" { yybegin(WAITING_TAG_PREFIX); return TAG_PREFIX_START; }
}
<WAITING_ATTRIBUTE_VALUE> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  {ATTRIBUTE_VALUE} { yybegin(WAITING_ATTRIBUTE_NAME); return ATTRIBUTE_VALUE_TOKEN; }
  "]" { yypushback(yylength()); yybegin(WAITING_TAG_PREFIX_END); }
  "[" { yybegin(WAITING_TAG_PREFIX); return TAG_PREFIX_START; }
}
<WAITING_TAG_PREFIX_END> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  "]" {
        yybegin(WAITING_TAG_BODY);
        boolean isEmptyTag = BBCodeSchemaManager.INSTANCE.isInlineTag(tagName);
        tagName = null;
        return isEmptyTag ? EMPTY_TAG_PREFIX_END : TAG_PREFIX_END;
  }
  "[" { yybegin(WAITING_TAG_PREFIX); return TAG_PREFIX_START; }
}
<WAITING_TAG_BODY> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  "[/" { yybegin(WAITING_TAG_SUFFIX); return TAG_SUFFIX_START; }
  "[" { yybegin(WAITING_TAG_PREFIX); return TAG_PREFIX_START; }
  {TEXT_TOKEN} { return TEXT_TOKEN; }
}
<WAITING_TAG_SUFFIX> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  {TAG_NAME} { return TAG_NAME; }
  "]" { yybegin(YYINITIAL); return TAG_SUFFIX_END; }
  "[" { yybegin(WAITING_TAG_PREFIX); return TAG_PREFIX_START; }
}

[^] { return BAD_CHARACTER; }
