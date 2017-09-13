package net.chinfeng.open.sqlbuilder.criterion.projection;

public interface Projection {
    boolean isGrouped();

    String toSqlString();
}
