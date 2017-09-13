package net.chinfeng.open.sqlbuilder.criterion;

import net.chinfeng.open.sqlbuilder.sql.SQLKeyWords;

public class Order implements Criterion {
    private boolean ascending;
    private String columnName;

    protected Order(String columnName, boolean ascending) {
        this.columnName = columnName;
        this.ascending = ascending;
    }

    public static Order asc(String columnName) {
        return new Order(columnName, true);
    }

    public static Order desc(String columnName) {
        return new Order(columnName, false);
    }

    public String toSqlString() {
        return new StringBuilder(SQLKeyWords.BACKQUOTE).append(this.columnName).append("` ").append(this.ascending ? SQLKeyWords.ASC : SQLKeyWords.DESC).toString();
    }
}
