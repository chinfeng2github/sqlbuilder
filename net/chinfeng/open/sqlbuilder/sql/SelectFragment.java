package net.chinfeng.open.sqlbuilder.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import net.chinfeng.open.sqlbuilder.util.StringHelper;

public class SelectFragment {
    private List<String> columnAliases;
    private List<String> columns;
    private String extraSelectList;
    private String suffix;
    private String[] usedAliases;

    public SelectFragment() {
        this.columns = new ArrayList();
        this.columnAliases = new ArrayList();
    }

    public List<String> getColumns() {
        return this.columns;
    }

    public String getExtraSelectList() {
        return this.extraSelectList;
    }

    public SelectFragment setUsedAliases(String[] aliases) {
        this.usedAliases = aliases;
        return this;
    }

    public SelectFragment setExtraSelectList(String extraSelectList) {
        this.extraSelectList = extraSelectList;
        return this;
    }

    public SelectFragment setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public SelectFragment addColumn(String columnName) {
        addColumn(null, columnName);
        return this;
    }

    public SelectFragment addColumns(String[] columnNames) {
        for (String columnName : columnNames) {
            addColumn(columnName);
        }
        return this;
    }

    public SelectFragment addColumn(String tableAlias, String columnName) {
        return addColumn(tableAlias, columnName, columnName);
    }

    public SelectFragment addColumn(String tableAlias, String columnName, String columnAlias) {
        this.columns.add(StringHelper.qualify(tableAlias, columnName));
        this.columnAliases.add(columnAlias);
        return this;
    }

    public SelectFragment addColumns(String tableAlias, String[] columnNames) {
        for (String columnName : columnNames) {
            addColumn(tableAlias, columnName);
        }
        return this;
    }

    public SelectFragment addColumns(String tableAlias, String[] columnNames, String[] columnAliases) {
        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i] != null) {
                addColumn(tableAlias, columnNames[i], columnAliases[i]);
            }
        }
        return this;
    }

    public String toFragmentString() {
        StringBuilder buf = new StringBuilder(this.columns.size() * 10);
        Iterator<String> columnAliasIter = this.columnAliases.iterator();
        HashSet<String> columnsUnique = new HashSet();
        if (this.usedAliases != null) {
            columnsUnique.addAll(Arrays.asList(this.usedAliases));
        }
        for (String column : this.columns) {
            String columnAlias = (String) columnAliasIter.next();
            if (columnsUnique.add(columnAlias)) {
                if (buf.length() > 0) {
                    buf.append(", ");
                }
                buf.append(column).append(" as ");
                if (this.suffix == null) {
                    buf.append(columnAlias);
                } else {
                    buf.append(new Alias(this.suffix).toAliasString(columnAlias));
                }
            }
        }
        if (this.extraSelectList != null) {
            buf.append(", ").append(this.extraSelectList);
        }
        return buf.toString();
    }
}
