package net.chinfeng.open.sqlbuilder;

import net.chinfeng.open.sqlbuilder.dialect.Dialect;
import net.chinfeng.open.sqlbuilder.sql.Insert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertBuilder {
    private static final Logger LOGGER;
    private final Dialect dialect;
    private Insert insert;

    static {
        LOGGER = LoggerFactory.getLogger(InsertBuilder.class);
    }

    public InsertBuilder(Dialect dialect) {
        this.dialect = dialect;
        this.insert = new Insert(this.dialect);
    }

    public static InsertBuilder create(Dialect dialect) {
        return new InsertBuilder(dialect);
    }

    public InsertBuilder table(String tableName) {
        this.insert.setTableName(tableName);
        return this;
    }

    public InsertBuilder addColumn(String columnName, String valueExpression) {
        this.insert.addColumn(columnName, valueExpression);
        return this;
    }

    public String toStatementString() {
        LOGGER.debug(this.insert.toStatementString());
        return this.insert.toStatementString();
    }
}
