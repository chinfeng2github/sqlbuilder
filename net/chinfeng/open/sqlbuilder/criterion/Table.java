package net.chinfeng.open.sqlbuilder.criterion;

import net.chinfeng.open.sqlbuilder.sql.SQLKeyWords;

public class Table implements Criterion {
    private String alias;
    private final String tableName;

    public Table(String tableName, String alias) {
        this.tableName = tableName;
        this.alias = alias;
    }

    public Table(String tableName) {
        this(tableName, null);
    }

    public String toSqlString() {
        if (this.alias == null || "".equals(this.alias)) {
            return new StringBuilder(SQLKeyWords.BACKQUOTE).append(this.tableName).append(SQLKeyWords.BACKQUOTE).toString();
        }
        return new StringBuilder(SQLKeyWords.BACKQUOTE).append(this.tableName).append("` as `").append(this.alias).append(SQLKeyWords.BACKQUOTE).toString();
    }

    public Table as(String alias) {
        this.alias = alias;
        return this;
    }
}
