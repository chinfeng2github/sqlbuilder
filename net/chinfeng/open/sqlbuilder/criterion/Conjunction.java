package net.chinfeng.open.sqlbuilder.criterion;

import net.chinfeng.open.sqlbuilder.sql.SQLKeyWords;

public class Conjunction extends Junction {
    public Conjunction() {
        super(SQLKeyWords.AND);
    }

    protected Conjunction(Criterion... criterion) {
        super(SQLKeyWords.AND, criterion);
    }
}
