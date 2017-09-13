package net.chinfeng.open.sqlbuilder.criterion.projection;

import net.chinfeng.open.sqlbuilder.sql.SQLKeyWords.Function;

public final class Projections {
    public static ColumnProjection coloumn(String coloumnName) {
        return new ColumnProjection(coloumnName);
    }

    public static ColumnProjection groupColoumn(String coloumnName) {
        return new ColumnProjection(coloumnName, true);
    }

    public static RowCountProjection rowCount() {
        return new RowCountProjection();
    }

    public static AggregateProjection max(String coloumnName) {
        return new AggregateProjection("max", coloumnName);
    }

    public static AggregateProjection min(String coloumnName) {
        return new AggregateProjection("min", coloumnName);
    }

    public static AggregateProjection avg(String coloumnName) {
        return new AggregateProjection("avg", coloumnName);
    }

    public static AggregateProjection sum(String coloumnName) {
        return new AggregateProjection(Function.SUM, coloumnName);
    }

    public static Projection alias(Projection projection, String alias) {
        return new AliasedProjection(projection, alias);
    }

    private Projections() {
    }
}
