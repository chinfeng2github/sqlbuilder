package net.chinfeng.open.sqlbuilder.criterion.projection;

import net.chinfeng.open.sqlbuilder.sql.SQLKeyWords;

public class AggregateProjection extends SimpleProjection {
    protected final String columnName;
    private final String functionName;

    protected AggregateProjection(String functionName, String columnName) {
        this.functionName = functionName;
        this.columnName = columnName;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public String toSqlString() {
        return this.functionName + SQLKeyWords.LEFTBRACKET + this.columnName + SQLKeyWords.RIGHTBRACKET;
    }

    public String getColumnName() {
        return this.columnName;
    }
}
