package net.chinfeng.open.sqlbuilder;

import java.util.ArrayList;
import java.util.Iterator;
import net.chinfeng.open.sqlbuilder.criterion.Criterion;
import net.chinfeng.open.sqlbuilder.dialect.Dialect;
import net.chinfeng.open.sqlbuilder.sql.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteBuilder {
    private static final Logger LOGGER;
    private final Dialect dialect;
    private ArrayList<Criterion> restrictions;
    private String tableName;

    static {
        LOGGER = LoggerFactory.getLogger(DeleteBuilder.class);
    }

    public DeleteBuilder(Dialect dialect) {
        this.dialect = dialect;
        this.restrictions = new ArrayList();
    }

    public static DeleteBuilder create(Dialect dialect) {
        return new DeleteBuilder(dialect);
    }

    public DeleteBuilder table(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DeleteBuilder add(Criterion criterion) {
        this.restrictions.add(criterion);
        return this;
    }

    public String toStatementString() {
        Delete delete = new Delete(this.dialect);
        delete.setTableName(this.tableName);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.setLength(0);
        if (this.restrictions.size() > 0) {
            Iterator it = this.restrictions.iterator();
            while (it.hasNext()) {
                Criterion criterion = (Criterion) it.next();
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(" and ");
                }
                stringBuilder.append(criterion.toSqlString());
            }
            delete.setWhere(stringBuilder.toString());
        }
        LOGGER.debug(delete.toStatementString());
        return delete.toStatementString();
    }

    public String getTableName() {
        return this.tableName;
    }
}
