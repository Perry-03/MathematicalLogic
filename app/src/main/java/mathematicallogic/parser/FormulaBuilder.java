package mathematicallogic.parser;

import mathematicallogic.formula.*;

public class FormulaBuilder extends LogicBaseVisitor<Formula> {

    @Override
    public Formula visitOrExpr(LogicParser.OrExprContext ctx) {
        Formula left = visit(ctx.formula(0));
        Formula right = visit(ctx.formula(1));
        return new Or(left, right);
    }

    @Override
    public Formula visitAndExpr(LogicParser.AndExprContext ctx) {
        Formula left = visit(ctx.formula(0));
        Formula right = visit(ctx.formula(1));
        return new And(left, right);
    }

    @Override
    public Formula visitXorExpr(LogicParser.XorExprContext ctx) {
        Formula left = visit(ctx.formula(0));
        Formula right = visit(ctx.formula(1));
        return new Xor(left, right);
    }

    @Override
    public Formula visitImplExpr(LogicParser.ImplExprContext ctx) {
        Formula left = visit(ctx.formula(0)) ;
        Formula right = visit(ctx.formula(1)) ;
        return new Or(new Not(left), right) ;
    }

    @Override
    public Formula visitUnaryExpr(LogicParser.UnaryExprContext ctx) { return visit(ctx.unary()) ; }

    @Override
    public Formula visitAtomExpr(LogicParser.AtomExprContext ctx) { return visit(ctx.atom()) ; }

    @Override
    public Formula visitNotExpr(LogicParser.NotExprContext ctx) {
        Formula inner = visit(ctx.unary());
        return new Not(inner);
    }

    @Override
    public Formula visitParenExpr(LogicParser.ParenExprContext ctx) {
        return visit(ctx.formula());
    }

    @Override
    public Formula visitVarExpr(LogicParser.VarExprContext ctx) {
        return new Var(ctx.ID().getText());
    }
}