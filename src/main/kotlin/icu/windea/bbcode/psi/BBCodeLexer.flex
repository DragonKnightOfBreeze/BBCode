package icu.windea.bbcode.psi;

import com.intellij.psi.tree.IElementType;

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
  private int tagStatus = 0; // 0/1/2/3 none/start_prefix/end_prefix/start_suffix 
%}

EOL=\R
WHITE_SPACE=\s+

TAG_NAME=[\w*\-_]+
ATTRIBUTE_NAME=[\w*\-_]+
ATTRIBUTE_VALUE=([^=\[\]\s\"][^=\[\]\s]*)|(\"([^\"\\]|\\.)*\"?)
TEXT_TOKEN=[^\[\]\s]+

%%
<YYINITIAL> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  "[/" {
      tagStatus=3; yybegin(WAITING_TAG_SUFFIX); return TAG_SUFFIX_START;
  }
  "[" {
      tagStatus=1; yybegin(WAITING_TAG_PREFIX); return TAG_PREFIX_START;
  }
  
  {TEXT_TOKEN} { return TEXT_TOKEN; }
}
<WAITING_TAG_PREFIX> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  {TAG_NAME} { yybegin(WAITING_ATTRIBUTES); return TAG_NAME; }
  "]" { tagStatus=2; yypushback(yylength()); yybegin(WAITING_TAG_PREFIX_END); }
}
<WAITING_ATTRIBUTES> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  "=" { yybegin(WAITING_SIMPLE_ATTRIBUTE_VALUE); return EQUAL_SIGN; }
  {ATTRIBUTE_NAME} { yybegin(WAITING_EQUAL_SIGN); return ATTRIBUTE_NAME; }
  "]" { tagStatus=2; yypushback(yylength()); yybegin(WAITING_TAG_PREFIX_END); }
}
<WAITING_ATTRIBUTE_NAME> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  {ATTRIBUTE_NAME} { yybegin(WAITING_EQUAL_SIGN); return ATTRIBUTE_NAME; }
  "]" { yypushback(yylength()); yybegin(WAITING_TAG_PREFIX_END); }
}
<WAITING_SIMPLE_ATTRIBUTE_VALUE> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  {ATTRIBUTE_VALUE} { yybegin(WAITING_TAG_PREFIX_END); return ATTRIBUTE_VALUE_TOKEN; }
  "]" { yypushback(yylength()); yybegin(WAITING_TAG_PREFIX_END); }
}
<WAITING_EQUAL_SIGN> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  "=" { yybegin(WAITING_ATTRIBUTE_VALUE); return EQUAL_SIGN; }
  "]" { yypushback(yylength()); yybegin(WAITING_TAG_PREFIX_END); }
}
<WAITING_ATTRIBUTE_VALUE> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  {ATTRIBUTE_VALUE} { yybegin(WAITING_ATTRIBUTE_NAME); return ATTRIBUTE_VALUE_TOKEN; }
  "]" { yypushback(yylength()); yybegin(WAITING_TAG_PREFIX_END); }
}
<WAITING_TAG_PREFIX_END> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  "]" { tagStatus=2; yybegin(WAITING_TAG_BODY); return TAG_PREFIX_END; }
}
<WAITING_TAG_SUFFIX> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  {TAG_NAME} { return TAG_NAME; }
  "]" { tagStatus=0; yybegin(YYINITIAL); return TAG_SUFFIX_END; }
}

[^] { return BAD_CHARACTER; }
