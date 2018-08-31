package interpreter;


%%
%class Lexer
%type Token

LOWERCASELETTER = [a-z]
UPPERCASELETTER = [A-Z]
DIGIT = [0-9]
NUMBER = {DIGIT}+
SPECIAL = [_,@,*]
IDENTIFIER = {LOWERCASELETTER}({LOWERCASELETTER}|{UPPERCASELETTER}|{DIGIT}|{SPECIAL})*
WHITESPACE      = [ \t\r\n]

%{
 public String lexeme;
%}

%%
{WHITESPACE} {/*ignore*/}
"=" {return ASSIGN;}
{IDENTIFIER} {lexeme = yylex(); return ID;}
{NUMBER} {lexeme = yylex(); return NUMBER;}
"Var" {lexeme = yylex(); return KEYWORD;}
"Set" {lexeme = yylex(); return KEYWORD;}
"Add+" {lexeme = yylex(); return KEYWORD;}
"Less-" {lexeme = yylex(); return KEYWORD;}
"(" {return LEFT_BRACKET;}
")" {return RIGHT_BRACKET;}
"place-block" {lexeme = yylex(); return KEYWORD;}
"Place-Block" {lexeme = yylex(); return KEYWORD;}
"high-block" {lexeme = yylex(); return KEYWORD;}
"put-light" {lexeme = yylex(); return KEYWORD;}
"Keep" {lexeme = yylex(); return KEYWORD;}
"Kend" {lexeme = yylex(); return KEYWORD;}
"For" {lexeme = yylex(); return KEYWORD;}
"Fend" {lexeme = yylex(); return KEYWORD;}
"Id" {lexeme = yylex(); return KEYWORD;}
"Times" {lexeme = yylex(); return KEYWORD;}
"When" {lexeme = yylex(); return KEYWORD;}
"Whend" {lexeme = yylex(); return KEYWORD;}
"Then" {lexeme = yylex(); return KEYWORD;}
"Call" {lexeme = yylex(); return KEYWORD;}
"Skip" {lexeme = yylex(); return KEYWORD;}
. {return ERROR;}


