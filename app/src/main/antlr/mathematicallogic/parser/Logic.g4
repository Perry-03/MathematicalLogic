grammar Logic;

formula   : formula '=>' formula      # ImplExpr
          | formula '||' formula      # OrExpr
          | formula '&&' formula      # AndExpr
          | formula '^'  formula      # XorExpr
          | unary                     # UnaryExpr
          ;

unary     : '!' unary                 # NotExpr
          | atom                      # AtomExpr
          ;

atom      : '(' formula ')'           # ParenExpr
          | ID                        # VarExpr
          ;

ID : [a-zA-Z][a-zA-Z0-9_]* ;
WS : [ \t\r\n]+ -> skip ;