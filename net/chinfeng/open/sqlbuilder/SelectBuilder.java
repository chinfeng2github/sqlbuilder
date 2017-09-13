package net.chinfeng.open.sqlbuilder;

import java.util.ArrayList;
import java.util.Iterator;
import net.chinfeng.open.sqlbuilder.criterion.Criterion;
import net.chinfeng.open.sqlbuilder.criterion.Order;
import net.chinfeng.open.sqlbuilder.criterion.Table;
import net.chinfeng.open.sqlbuilder.criterion.projection.Projection;
import net.chinfeng.open.sqlbuilder.dialect.Dialect;
import net.chinfeng.open.sqlbuilder.sql.SQLKeyWords;
import net.chinfeng.open.sqlbuilder.sql.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectBuilder {
    private static final Logger LOGGER;
    private final Dialect dialect;
    private ArrayList<Projection> groupProjections;
    private ArrayList<Criterion> orders;
    private ArrayList<Projection> projections;
    private ArrayList<Criterion> restrictions;
    private ArrayList<Table> tableList;

    static {
        LOGGER = LoggerFactory.getLogger(SelectBuilder.class);
    }

    public SelectBuilder(Dialect dialect) {
        this.dialect = dialect;
        this.tableList = new ArrayList();
        this.projections = new ArrayList();
        this.groupProjections = new ArrayList();
        this.restrictions = new ArrayList();
        this.orders = new ArrayList();
    }

    public static SelectBuilder create(Dialect dialect) {
        return new SelectBuilder(dialect);
    }

    public SelectBuilder table(String table) {
        this.tableList.add(new Table(table));
        return this;
    }

    public SelectBuilder table(String table, String alias) {
        this.tableList.add(new Table(table).as(alias));
        return this;
    }

    public SelectBuilder add(Projection projection) {
        this.projections.add(projection);
        if (projection.isGrouped()) {
            this.groupProjections.add(projection);
        }
        return this;
    }

    public SelectBuilder add(Criterion criterion) {
        this.restrictions.add(criterion);
        return this;
    }

    public SelectBuilder addOrder(Order order) {
        this.orders.add(order);
        return this;
    }

    public String toStatementString() {
        Iterator it;
        Select select = new Select(this.dialect);
        StringBuilder stringBuilder = new StringBuilder();
        if (this.projections.size() == 0) {
            select.setSelectClause("*");
        } else {
            it = this.projections.iterator();
            while (it.hasNext()) {
                Projection projection = (Projection) it.next();
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(SQLKeyWords.COMMA);
                }
                stringBuilder.append(projection.toSqlString());
            }
            select.setSelectClause(stringBuilder.toString());
        }
        stringBuilder.setLength(0);
        if (this.tableList.size() > 0) {
            it = this.tableList.iterator();
            while (it.hasNext()) {
                Table table = (Table) it.next();
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(table.toSqlString());
            }
            select.setFromClause(stringBuilder.toString());
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
            select.setWhereClause(stringBuilder.toString());
        }
        stringBuilder.setLength(0);
        if (this.orders.size() > 0) {
            it = this.orders.iterator();
            while (it.hasNext()) {
                Criterion order = (Criterion) it.next();
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(order.toSqlString());
            }
            select.setOrderByClause(stringBuilder.toString());
        }
        stringBuilder.setLength(0);
        if (this.groupProjections.size() > 0) {
            it = this.groupProjections.iterator();
            while (it.hasNext()) {
                projection = (Projection) it.next();
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(SQLKeyWords.COMMA);
                }
                stringBuilder.append(projection.toSqlString());
            }
            select.setGroupByClause(stringBuilder.toString());
        }
        LOGGER.debug(select.toStatementString());
        return select.toStatementString();
    }
}
