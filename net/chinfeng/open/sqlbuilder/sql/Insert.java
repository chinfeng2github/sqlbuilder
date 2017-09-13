package net.chinfeng.open.sqlbuilder.sql;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import net.chinfeng.open.sqlbuilder.dialect.Dialect;

public class Insert {
    private Map columns;
    private String comment;
    private Dialect dialect;
    private String tableName;

    public Insert(Dialect dialect) {
        this.columns = new LinkedHashMap();
        this.dialect = dialect;
    }

    protected Dialect getDialect() {
        return this.dialect;
    }

    public Insert setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Insert addColumn(String columnName) {
        return addColumn(columnName, "?");
    }

    public Insert addColumns(String[] columnNames) {
        for (String addColumn : columnNames) {
            addColumn(addColumn);
        }
        return this;
    }

    public Insert addColumns(String[] columnNames, boolean[] insertable) {
        for (int i = 0; i < columnNames.length; i++) {
            if (insertable[i]) {
                addColumn(columnNames[i]);
            }
        }
        return this;
    }

    public Insert addColumns(String[] columnNames, boolean[] insertable, String[] valueExpressions) {
        for (int i = 0; i < columnNames.length; i++) {
            if (insertable[i]) {
                addColumn(columnNames[i], valueExpressions[i]);
            }
        }
        return this;
    }

    public Insert addColumn(String columnName, String valueExpression) {
        this.columns.put(columnName, valueExpression);
        return this;
    }

    public Insert setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String toStatementString() {
        StringBuilder buf = new StringBuilder(((this.columns.size() * 15) + this.tableName.length()) + 10);
        if (this.comment != null) {
            buf.append("/* ").append(this.comment).append(" */ ");
        }
        buf.append("insert into ").append(new StringBuilder(SQLKeyWords.BACKQUOTE).append(this.tableName).append(SQLKeyWords.BACKQUOTE).toString());
        if (this.columns.size() == 0) {
            buf.append(' ');
        } else {
            buf.append(" (");
            Iterator iter = this.columns.keySet().iterator();
            while (iter.hasNext()) {
                buf.append(new StringBuilder(SQLKeyWords.BACKQUOTE).append(iter.next()).append(SQLKeyWords.BACKQUOTE).toString());
                if (iter.hasNext()) {
                    buf.append(", ");
                }
            }
            buf.append(") values (");
            iter = this.columns.values().iterator();
            while (iter.hasNext()) {
                buf.append(new StringBuilder(SQLKeyWords.QUOTE).append(iter.next()).append(SQLKeyWords.QUOTE).toString());
                if (iter.hasNext()) {
                    buf.append(", ");
                }
            }
            buf.append(')');
        }
        return buf.toString();
    }
}
