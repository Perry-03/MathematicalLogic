grammar Logic;

// Regola principale
formula
    : formula '||' formula      # OrExpr
    | formula '&&' formula      # AndExpr
    | formula '^' formula       # XorExpr
    | '!' formula               # NotExpr
    | '(' formula ')'           # ParenExpr
    | ID                        # VarExpr
    ;

// Tokens
ID : [a-zA-Z][a-zA-Z0-9_]* ;
WS : [ \t\r\n]+ -> skip ;