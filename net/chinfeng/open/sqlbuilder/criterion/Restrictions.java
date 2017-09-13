package net.chinfeng.open.sqlbuilder.criterion;

import java.util.Collection;
import net.chinfeng.open.sqlbuilder.sql.SQLKeyWords;

public final class Restrictions {
    public static SimpleExpression eq(String column, String value) {
        return new SimpleExpression(column, value, SQLKeyWords.EQUALITY_SIGN);
    }

    public static SimpleExpression gt(String column, String value) {
        return new SimpleExpression(column, value, ">");
    }

    public static SimpleExpression ge(String column, String value) {
        return new SimpleExpression(column, value, ">=");
    }

    public static SimpleExpression lt(String column, String value) {
        return new SimpleExpression(column, value, "<");
    }

    public static SimpleExpression le(String column, String value) {
        return new SimpleExpression(column, value, "<=");
    }

    public static SimpleExpression ne(String column, String value) {
        return new SimpleExpression(column, value, "<>");
    }

    public static LogicalExpression and(Criterion lhs, Criterion rhs) {
        return new LogicalExpression(lhs, rhs, SQLKeyWords.AND);
    }

    public static Conjunction and(Criterion... predicates) {
        return conjunction(predicates);
    }

    public static LogicalExpression or(Criterion lhs, Criterion rhs) {
        return new LogicalExpression(lhs, rhs, "or");
    }

    public static Disjunction or(Criterion... predicates) {
        return disjunction(predicates);
    }

    public static Conjunction conjunction() {
        return new Conjunction();
    }

    public static Conjunction conjunction(Criterion... conditions) {
        return new Conjunction(conditions);
    }

    public static Disjunction disjunction() {
        return new Disjunction();
    }

    public static Disjunction disjunction(Criterion... conditions) {
        return new Disjunction(conditions);
    }

    public static Criterion in(String columnName, String... values) {
        return new InExpression(columnName, values);
    }

    public static Criterion in(String propertyName, Collection<String> values) {
        String[] stringValues = new String[values.size()];
        values.toArray(stringValues);
        return new InExpression(propertyName, stringValues);
    }

    private Restrictions() {
    }
}
