package net.chinfeng.open.sqlbuilder.criterion.projection;

import net.chinfeng.open.sqlbuilder.sql.SQLKeyWords.Function;

public class RowCountProjection extends AggregateProjection implements Projection {
    public RowCountProjection() {
        super(Function.COUNT, "*");
    }
}
