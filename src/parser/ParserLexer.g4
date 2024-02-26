lexer grammar ParserLexer;

// Default Mode
// Lexer Rules for variable name

// SET STUFF
SET: 'SET' WS* -> mode(EXP_MODE);
EQUAL: '=' WS* -> mode(EXP_MODE);

COLON_DEFAULT: ':' WS*;

// setup
SET_UP: 'setup';

//command
COMMAND: 'command' WS* -> mode(EXP_MODE);

// Routine
ROUTINE: 'routine' WS* -> mode(EXP_MODE);

// Code stuff

// Bot Functions
SEND_MESSAGE: 'Send Message' WS* -> mode(EXP_MODE);
SEND_REPLY: 'Send Reply' -> mode(EXP_MODE);
SEND_IN: 'in' WS* -> mode (EXP_MODE);
SEND_TO: 'to' WS* -> mode(EXP_MODE);

// Give Role
GIVE_USER: 'Give User' WS* -> mode(EXP_MODE);
ROLE: 'Role' WS*-> mode(EXP_MODE);

// Kick USER
KICK_USER: 'Kick User' WS*-> mode(EXP_MODE);

// Ban User
BAN_USER: 'Ban User' WS*-> mode(EXP_MODE);

// if else
IF: 'if' WS* -> mode(EXP_MODE);
LCURLY_BRACE: '{' WS*;
RCURLY_BRACE: '}';
ELSE: 'else' WS*;

// while stuff
WHILE: 'while' WS*-> mode(EXP_MODE);

// conditional stuff
IS_IN: 'isin' WS* -> mode(EXP_MODE);
IS: 'is' WS* -> mode(EXP_MODE);

// Delete
DELETE: 'Delete';

// DONE
DONE: ';';

// Line breaks are ignored during tokenization (note that this rule only applies in DEFAULT_MODE, not IDENT_MODE)
STMT_NEWLINE : [\r\n\t]+;

WS: [ \n\t\r]+ -> channel(HIDDEN);

//for array
COMMA: ',' WS* -> mode(EXP_MODE);
R_BRACKET: ']';

// Math stuff
OPERATOR: ('+' | '-' | '/' | '*') WS* ->mode(EXP_MODE);

// Mode for exp
mode EXP_MODE;
// Message Stuff
WAIT_MESSAGE: 'Wait Message' WS* -> mode(EXP_MODE);
WAIT_MESSAGE_IN: 'Wait Message in' WS* -> mode(EXP_MODE);

GET_MESSAGE: 'Get Message' WS* -> mode(EXP_MODE);

GET_USER: 'Get User' WS* -> mode(EXP_MODE);

// Choose
CHOOSE: 'Choose' WS*-> mode(EXP_MODE);

// Lexer Rules for Data Types
NAME: [a-zA-Z_][a-zA-Z_0-9]* -> mode(DEFAULT_MODE);
INT: [0-9]+ -> mode(DEFAULT_MODE);
BOOL: ('True' | 'False') -> mode(DEFAULT_MODE); // TODO: CHECK IF WORK
STRING: '"' ('\\' ["\\] | ~["\\\r\n])* '"' -> mode(DEFAULT_MODE);
L_BRACKET: '[';

// Line breaks are ignored during tokenization (note that this rule only applies in DEFAULT_MODE, not IDENT_MODE)
NEWLINE: [\r\n]+;

