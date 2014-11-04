grammar SituationAction;

// Reglas de producción
program: sa_rule+ EOF ;

sa_rule: situation IMPLIES action DOT ;

situation: situation AND situation         # And
         | situation OR situation          # Or
         | NOT? LEFTPAR situation RIGHTPAR # Parens
         | term                            # SingleTerm
         ;

term: direction NOT? (FREE | WALL | VISITED | AGENT | OFFLIMITS) ;

action: MOVE direction
      | STOP ;

direction: UP
         | DOWN
         | LEFT
         | RIGHT
         ;

// Tokens
IMPLIES: '=>'
       | '->' ;
DOT:     '.' ;

AND:      '&' ;
OR:       '|' ;
LEFTPAR:  '(' ;
RIGHTPAR: ')' ;

NOT: N O T
   | '!'
   | '~'
   ;

FREE:      F R E E ;
WALL:      W A L L ;
VISITED:   V I S I T E D ;
AGENT:     A G E N T ;
OFFLIMITS: O F F L I M I T S ;

MOVE: M O V E
    | G O
    ;
STOP: S T O P ;

UP:    U P ;
DOWN:  D O W N ;
LEFT:  L E F T ;
RIGHT: R I G H T ;

BLANK : [ \t\n\r]+ -> skip ;

// Cosas de Antlr para poder casar con cadenas sin diferenciar mayúsculas y
// minúsculas
fragment A: ('a'|'A');
fragment B: ('b'|'B');
fragment C: ('c'|'C');
fragment D: ('d'|'D');
fragment E: ('e'|'E');
fragment F: ('f'|'F');
fragment G: ('g'|'G');
fragment H: ('h'|'H');
fragment I: ('i'|'I');
fragment J: ('j'|'J');
fragment K: ('k'|'K');
fragment L: ('l'|'L');
fragment M: ('m'|'M');
fragment N: ('n'|'N');
fragment O: ('o'|'O');
fragment P: ('p'|'P');
fragment Q: ('q'|'Q');
fragment R: ('r'|'R');
fragment S: ('s'|'S');
fragment T: ('t'|'T');
fragment U: ('u'|'U');
fragment V: ('v'|'V');
fragment W: ('w'|'W');
fragment X: ('x'|'X');
fragment Y: ('y'|'Y');
fragment Z: ('z'|'Z');
