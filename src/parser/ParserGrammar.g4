parser grammar ParserGrammar;

options { tokenVocab=ParserLexer; }

// Grammar
program : (statement STMT_NEWLINE?)* EOF;

statement: setStatement | commandStatement | routineStatement | setupStatement | delete
            | sendStatement | replyStatement | giveRoleStatement | kickStatement | banStatement
            | ifElseStatement | whileStatement;

statements: (statement STMT_NEWLINE?)*;

// needed definition
varname: NAME;
expression: STRING | NAME| INT | BOOL | array;
keywordExpression: math | getMessage | waitMessage | waitinMessage | getUser | choose;

// arrayEXP
array: L_BRACKET (expression (COMMA expression)*)? R_BRACKET;

// BINARY EXP
left: INT | NAME;
right: INT | NAME;
operator: OPERATOR;
math: left operator right;

// GET and wait message
getMessage: GET_MESSAGE expression;
waitMessage: WAIT_MESSAGE expression;
waitinMessage: WAIT_MESSAGE_IN expression;

// get user
getUser: GET_USER expression;

// random
choose: CHOOSE (array | NAME);

// Set stuff
setStatement: SET varname EQUAL (expression|keywordExpression);

// Command stuff
commandStatement: COMMAND varname COLON_DEFAULT statements DONE; // TODO: multiple code in command

// Routine stuff
routineStatement: ROUTINE varname COLON_DEFAULT statements DONE; // TODO: multiple code

// setup stuff
setupStatement: SET_UP COLON_DEFAULT statements DONE; // TODO: multiple code so statement
delete: DELETE;

// BOT sending message
sendStatement: SEND_MESSAGE expression (SEND_IN | SEND_TO) expression;
replyStatement: SEND_REPLY expression SEND_TO expression;

// give role
giveRoleStatement: GIVE_USER expression ROLE expression;

// kick user
kickStatement: KICK_USER expression;

// ban user
banStatement: BAN_USER expression;

// if else
ifElseStatement: IF condition LCURLY_BRACE statements RCURLY_BRACE else?; // same problem with command and rooutine TODO:wf
else: ELSE LCURLY_BRACE statements RCURLY_BRACE;
condition: (expression (IS_IN | IS) expression | NAME) ;

// while
whileStatement: WHILE condition LCURLY_BRACE statements RCURLY_BRACE; // TODO: same problem as above