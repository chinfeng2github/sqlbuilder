package net.chinfeng.open.sqlbuilder;

import net.chinfeng.open.sqlbuilder.dialect.Dialect;

public class SQLBuilder {
    private SQLBuilder() {
    }

    public static SelectBuilder select(Dialect dialect) {
        return SelectBuilder.create(dialect);
    }

    public static UpdateBuilder update(Dialect dialect) {
        return UpdateBuilder.create(dialect);
    }

    public static DeleteBuilder delete(Dialect dialect) {
        return DeleteBuilder.create(dialect);
    }

    public static InsertBuilder insert(Dialect dialect) {
        return InsertBuilder.create(dialect);
    }
}
