package net.chinfeng.open.sqlbuilder.dialect;

public abstract class Dialect {
    public static final String CLOSED_QUOTE = "`\"]";
    public static final String QUOTE = "`\"[";

    public String transformSelectString(String select) {
        return select;
    }
}
