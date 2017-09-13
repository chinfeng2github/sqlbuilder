package net.chinfeng.open.sqlbuilder.criterion.projection;

import net.chinfeng.open.sqlbuilder.sql.SQLKeyWords;

public class AliasedProjection implements Projection {
    private final String alias;
    private final Projection projection;

    protected AliasedProjection(Projection projection, String alias) {
        this.projection = projection;
        this.alias = alias;
    }

    public boolean isGrouped() {
        return this.projection.isGrouped();
    }

    public String toSqlString() {
        return new StringBuilder(String.valueOf(this.projection.toSqlString())).append(" as `").append(this.alias).append(SQLKeyWords.BACKQUOTE).toString();
    }
}
