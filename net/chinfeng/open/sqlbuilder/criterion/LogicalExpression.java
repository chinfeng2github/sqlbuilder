package net.chinfeng.open.sqlbuilder.criterion;

public class LogicalExpression implements Criterion {
    private final Criterion lhs;
    private final String operator;
    private final Criterion rhs;

    protected LogicalExpression(Criterion lhs, Criterion rhs, String operator) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.operator = operator;
    }

    public String toSqlString() {
        return new StringBuilder(String.valueOf('(')).append(this.lhs.toSqlString()).append(' ').append(this.operator).append(' ').append(this.rhs.toSqlString()).append(')').toString();
    }
}
