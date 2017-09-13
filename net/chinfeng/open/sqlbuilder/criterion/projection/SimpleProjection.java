package net.chinfeng.open.sqlbuilder.criterion.projection;

public class SimpleProjection implements Projection {
    public boolean isGrouped() {
        return false;
    }

    public String toSqlString() {
        return null;
    }

    public Projection as(String alias) {
        return Projections.alias(this, alias);
    }
}
