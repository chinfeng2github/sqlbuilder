package net.chinfeng.open.sqlbuilder;

import java.util.ArrayList;
import java.util.Iterator;
import net.chinfeng.open.sqlbuilder.criterion.Criterion;
import net.chinfeng.open.sqlbuilder.criterion.Restrictions;
import net.chinfeng.open.sqlbuilder.criterion.SimpleExpression;
import net.chinfeng.open.sqlbuilder.dialect.Dialect;
import net.chinfeng.open.sqlbuilder.sql.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateBuilder {
    private static final Logger LOGGER;
    private final Dialect dialect;
    private ArrayList<Criterion> restrictions;
    private ArrayList<SimpleExpression> setList;
    private String tableName;

    static {
        LOGGER = LoggerFactory.getLogger(UpdateBuilder.class);
    }

    public UpdateBuilder(Dialect dialect) {
        this.dialect = dialect;
        this.setList = new ArrayList();
        this.restrictions = new ArrayList();
    }

    public static UpdateBuilder create(Dialect dialect) {
        return new UpdateBuilder(dialect);
    }

    public UpdateBuilder table(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getTableName() {
        return this.tableName;
    }

    public UpdateBuilder set(String columnName, String value) {
        this.setList.add(Restrictions.eq(columnName, value));
        return this;
    }

    public UpdateBuilder add(Criterion criterion) {
        this.restrictions.add(criterion);
        return this;
    }

    public String toStatementString() {
        Update update = new Update(this.dialect);
        update.setTableName(this.tableName);
        StringBuilder stringBuilder = new StringBuilder();
        Iterator it = this.setList.iterator();
        while (it.hasNext()) {
            update.appendAssignmentFragment(((SimpleExpression) it.next()).toSqlString());
        }
        stringBuilder.setLength(0);
        if (this.restrictions.size() > 0) {
            it = this.restrictions.iterator();
            while (it.hasNext()) {
                Criterion criterion = (Criterion) it.next();
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(" and ");
                }
                stringBuilder.append(criterion.toSqlString());
            }
            update.setWhere(stringBuilder.toString());
        }
        LOGGER.info(update.toStatementString());
        return update.toStatementString();
    }
}
