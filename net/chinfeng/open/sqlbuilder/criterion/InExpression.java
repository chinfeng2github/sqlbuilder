package net.chinfeng.open.sqlbuilder.criterion;

import net.chinfeng.open.sqlbuilder.sql.SQLKeyWords;

public class InExpression implements Criterion {
    private final String columnName;
    private final String[] values;

    protected InExpression(String columnName, String[] values) {
        this.columnName = columnName;
        this.values = values;
    }

    public String toSqlString() {
        StringBuilder valueString = new StringBuilder();
        for (String string : this.values) {
            if (valueString.length() > 0) {
                valueString.append(SQLKeyWords.COMMA);
            }
            valueString.append(SQLKeyWords.QUOTE).append(string).append(SQLKeyWords.QUOTE);
        }
        return new StringBuilder(SQLKeyWords.BACKQUOTE).append(this.columnName).append("` ").append(" in (").append(valueString.toString()).append(SQLKeyWords.RIGHTBRACKET).toString();
    }
}
