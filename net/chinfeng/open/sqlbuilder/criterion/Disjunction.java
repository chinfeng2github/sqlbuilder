package net.chinfeng.open.sqlbuilder.criterion;

public class Disjunction extends Junction {
    protected Disjunction() {
        super("or");
    }

    protected Disjunction(Criterion[] conditions) {
        super("or", conditions);
    }
}
