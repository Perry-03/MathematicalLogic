package mathematicallogic;

import mathematicallogic.bdd.BDDNode;
import mathematicallogic.builder.BDDFactory;
import mathematicallogic.formula.*;
import mathematicallogic.util.Utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String... args) {
        // definizione variabili
        Formula p = new Var("p");
        Formula q = new Var("q");
        Formula r = new Var("r");

        // mappa nome-file -> formula
        Map<String, Formula> formulas = new LinkedHashMap<>();
        formulas.put("p_or_q", new Or(p, q));
        formulas.put("p_or_q_or_r", new Or(new Or(p, q), r));
        formulas.put("p_and_q", new And(p, q));
        formulas.put("p_and_q_and_r", new And(new And(p, q), r));
        formulas.put("not_p_and_q", new Not(new And(p, q)));
        formulas.put("p_xor_q", new Xor(p, q)) ;
        formulas.put("p_xor_q_xor_r",  new Xor(new Xor(p, q), r));

        // generazione BDD e export in .dot
        for (Map.Entry<String, Formula> entry : formulas.entrySet()) {
            String name = entry.getKey();
            Formula formula = entry.getValue();

            BDDNode bdd = BDDFactory.ast_to_bdd(formula);
            Utils.export_to_dot(bdd, "app/build/generated/graphs/" + name + ".dot");
        }
    }
}
