package net.chinfeng.open.sqlbuilder.criterion.projection;

import net.chinfeng.open.sqlbuilder.util.SQLUtils;

public class ColumnProjection extends SimpleProjection implements Projection {
    private String column;
    private boolean grouped;

    protected ColumnProjection(String column, boolean grouped) {
        this.column = column;
        this.grouped = grouped;
    }

    protected ColumnProjection(String column) {
        this(column, false);
    }

    public String toSqlString() {
        return SQLUtils.backquote(this.column);
    }

    public boolean isGrouped() {
        return this.grouped;
    }
}
