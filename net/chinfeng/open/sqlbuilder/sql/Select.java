package net.chinfeng.open.sqlbuilder.sql;

import net.chinfeng.open.sqlbuilder.dialect.Dialect;
import net.chinfeng.open.sqlbuilder.util.StringHelper;

public class Select {
    private String comment;
    public final Dialect dialect;
    private String fromClause;
    private String groupByClause;
    private int guesstimatedBufferSize;
    private String orderByClause;
    private String outerJoinsAfterFrom;
    private String outerJoinsAfterWhere;
    private String selectClause;
    private String whereClause;

    public Select(Dialect dialect) {
        this.guesstimatedBufferSize = 20;
        this.dialect = dialect;
    }

    public String toStatementString() {
        StringBuilder buf = new StringBuilder(this.guesstimatedBufferSize);
        if (StringHelper.isNotEmpty(this.comment)) {
            buf.append("/* ").append(this.comment).append(" */ ");
        }
        buf.append("select ").append(this.selectClause).append(" from ").append(this.fromClause);
        if (StringHelper.isNotEmpty(this.outerJoinsAfterFrom)) {
            buf.append(this.outerJoinsAfterFrom);
        }
        if (StringHelper.isNotEmpty(this.whereClause) || StringHelper.isNotEmpty(this.outerJoinsAfterWhere)) {
            buf.append(" where ");
            if (StringHelper.isNotEmpty(this.outerJoinsAfterWhere)) {
                buf.append(this.outerJoinsAfterWhere);
                if (StringHelper.isNotEmpty(this.whereClause)) {
                    buf.append(" and ");
                }
            }
            if (StringHelper.isNotEmpty(this.whereClause)) {
                buf.append(this.whereClause);
            }
        }
        if (StringHelper.isNotEmpty(this.groupByClause)) {
            buf.append(" group by ").append(this.groupByClause);
        }
        if (StringHelper.isNotEmpty(this.orderByClause)) {
            buf.append(" order by ").append(this.orderByClause);
        }
        return this.dialect.transformSelectString(buf.toString());
    }

    public Select setFromClause(String fromClause) {
        this.fromClause = fromClause;
        this.guesstimatedBufferSize += fromClause.length();
        return this;
    }

    public Select setFromClause(String tableName, String alias) {
        this.fromClause = new StringBuilder(String.valueOf(tableName)).append(' ').append(alias).toString();
        this.guesstimatedBufferSize += this.fromClause.length();
        return this;
    }

    public Select setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
        this.guesstimatedBufferSize += orderByClause.length();
        return this;
    }

    public Select setGroupByClause(String groupByClause) {
        this.groupByClause = groupByClause;
        this.guesstimatedBufferSize += groupByClause.length();
        return this;
    }

    public Select setOuterJoins(String outerJoinsAfterFrom, String outerJoinsAfterWhere) {
        this.outerJoinsAfterFrom = outerJoinsAfterFrom;
        String tmpOuterJoinsAfterWhere = outerJoinsAfterWhere.trim();
        if (tmpOuterJoinsAfterWhere.startsWith(SQLKeyWords.AND)) {
            tmpOuterJoinsAfterWhere = tmpOuterJoinsAfterWhere.substring(4);
        }
        this.outerJoinsAfterWhere = tmpOuterJoinsAfterWhere;
        this.guesstimatedBufferSize += outerJoinsAfterFrom.length() + outerJoinsAfterWhere.length();
        return this;
    }

    public Select setSelectClause(String selectClause) {
        this.selectClause = selectClause;
        this.guesstimatedBufferSize += selectClause.length();
        return this;
    }

    public Select setSelectClause(SelectFragment selectFragment) {
        setSelectClause(selectFragment.toFragmentString().substring(2));
        return this;
    }

    public Select setWhereClause(String whereClause) {
        this.whereClause = whereClause;
        this.guesstimatedBufferSize += whereClause.length();
        return this;
    }

    public Select setComment(String comment) {
        this.comment = comment;
        this.guesstimatedBufferSize += comment.length();
        return this;
    }
}
