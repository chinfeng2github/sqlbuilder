package net.chinfeng.open.sqlbuilder.sql;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.chinfeng.open.sqlbuilder.dialect.Dialect;

public class Update {
    private String assignments;
    private Map columns;
    private String comment;
    private Dialect dialect;
    private Map primaryKeyColumns;
    private String tableName;
    private String versionColumnName;
    private String where;
    private Map whereColumns;

    public Update(Dialect dialect) {
        this.primaryKeyColumns = new LinkedHashMap();
        this.columns = new LinkedHashMap();
        this.whereColumns = new LinkedHashMap();
        this.dialect = dialect;
    }

    public String getTableName() {
        return this.tableName;
    }

    public Update appendAssignmentFragment(String fragment) {
        if (this.assignments == null) {
            this.assignments = fragment;
        } else {
            this.assignments += ", " + fragment;
        }
        return this;
    }

    public Update setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public Update setPrimaryKeyColumnNames(String[] columnNames) {
        this.primaryKeyColumns.clear();
        addPrimaryKeyColumns(columnNames);
        return this;
    }

    public Update addPrimaryKeyColumns(String[] columnNames) {
        for (String columnName : columnNames) {
            addPrimaryKeyColumn(columnName, "?");
        }
        return this;
    }

    public Update addPrimaryKeyColumns(String[] columnNames, boolean[] includeColumns, String[] valueExpressions) {
        for (int i = 0; i < columnNames.length; i++) {
            if (includeColumns[i]) {
                addPrimaryKeyColumn(columnNames[i], valueExpressions[i]);
            }
        }
        return this;
    }

    public Update addPrimaryKeyColumns(String[] columnNames, String[] valueExpressions) {
        for (int i = 0; i < columnNames.length; i++) {
            addPrimaryKeyColumn(columnNames[i], valueExpressions[i]);
        }
        return this;
    }

    public Update addPrimaryKeyColumn(String columnName, String valueExpression) {
        this.primaryKeyColumns.put(columnName, valueExpression);
        return this;
    }

    public Update setVersionColumnName(String versionColumnName) {
        this.versionColumnName = versionColumnName;
        return this;
    }

    public Update setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Update addColumns(String[] columnNames) {
        for (String columnName : columnNames) {
            addColumn(columnName);
        }
        return this;
    }

    public Update addColumns(String[] columnNames, boolean[] updateable, String[] valueExpressions) {
        for (int i = 0; i < columnNames.length; i++) {
            if (updateable[i]) {
                addColumn(columnNames[i], valueExpressions[i]);
            }
        }
        return this;
    }

    public Update addColumns(String[] columnNames, String valueExpression) {
        for (String columnName : columnNames) {
            addColumn(columnName, valueExpression);
        }
        return this;
    }

    public Update addColumn(String columnName) {
        return addColumn(columnName, "?");
    }

    public Update addColumn(String columnName, String valueExpression) {
        this.columns.put(columnName, valueExpression);
        return this;
    }

    public Update addWhereColumns(String[] columnNames) {
        for (String columnName : columnNames) {
            addWhereColumn(columnName);
        }
        return this;
    }

    public Update addWhereColumns(String[] columnNames, String valueExpression) {
        for (String columnName : columnNames) {
            addWhereColumn(columnName, valueExpression);
        }
        return this;
    }

    public Update addWhereColumn(String columnName) {
        return addWhereColumn(columnName, "=?");
    }

    public Update addWhereColumn(String columnName, String valueExpression) {
        this.whereColumns.put(columnName, valueExpression);
        return this;
    }

    public Update setWhere(String where) {
        this.where = where;
        return this;
    }

    public String toStatementString() {
        StringBuilder buf = new StringBuilder(((this.columns.size() * 15) + this.tableName.length()) + 10);
        if (this.comment != null) {
            buf.append("/* ").append(this.comment).append(" */ ");
        }
        buf.append("update ").append(this.tableName).append(" set ");
        boolean assignmentsAppended = false;
        Iterator iter = this.columns.entrySet().iterator();
        while (iter.hasNext()) {
            Entry e = (Entry) iter.next();
            buf.append(e.getKey()).append('=').append(e.getValue());
            if (iter.hasNext()) {
                buf.append(", ");
            }
            assignmentsAppended = true;
        }
        if (this.assignments != null) {
            if (assignmentsAppended) {
                buf.append(", ");
            }
            buf.append(this.assignments);
        }
        boolean conditionsAppended = false;
        if (!(this.primaryKeyColumns.isEmpty() && this.where == null && this.whereColumns.isEmpty() && this.versionColumnName == null)) {
            buf.append(" where ");
        }
        iter = this.primaryKeyColumns.entrySet().iterator();
        while (iter.hasNext()) {
            e = (Entry) iter.next();
            buf.append(e.getKey()).append('=').append(e.getValue());
            if (iter.hasNext()) {
                buf.append(" and ");
            }
            conditionsAppended = true;
        }
        if (this.where != null) {
            if (conditionsAppended) {
                buf.append(" and ");
            }
            buf.append(this.where);
            conditionsAppended = true;
        }
        for (Entry e2 : this.whereColumns.entrySet()) {
            if (conditionsAppended) {
                buf.append(" and ");
            }
            buf.append(e2.getKey()).append(e2.getValue());
            conditionsAppended = true;
        }
        if (this.versionColumnName != null) {
            if (conditionsAppended) {
                buf.append(" and ");
            }
            buf.append(this.versionColumnName).append("=?");
        }
        return buf.toString();
    }
}
