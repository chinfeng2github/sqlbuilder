package net.chinfeng.open.sqlbuilder.criterion;

import net.chinfeng.open.sqlbuilder.util.SQLUtils;

public class SimpleExpression implements Criterion {
    private final String column;
    private final String operator;
    private final String value;

    protected SimpleExpression(String column, String value, String operator) {
        this.column = column;
        this.value = value;
        this.operator = operator;
    }

    public String toSqlString() {
        return new StringBuilder(String.valueOf(SQLUtils.backquote(this.column))).append(this.operator).append(SQLUtils.quote(this.value)).toString();
    }
}
