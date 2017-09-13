package net.chinfeng.open.sqlbuilder.sql;

public interface SQLKeyWords {
    public static final String AND = "and";
    public static final String AS = "as";
    public static final String ASC = "asc";
    public static final String BACKQUOTE = "`";
    public static final String COMMA = ",";
    public static final String DESC = "desc";
    public static final String EQUALITY_SIGN = "=";
    public static final String FROM = "from";
    public static final String GROUP_BY = "group by";
    public static final String HAVING = "having";
    public static final String LEFTBRACKET = "(";
    public static final String ORDER_BY = "order by";
    public static final String QUOTE = "'";
    public static final String RIGHTBRACKET = ")";
    public static final String SELECT = "select";
    public static final String SET = "set";
    public static final String SPACE = " ";
    public static final String UPDATE = "update";
    public static final String WHERE = "where";

    public interface Function {
        public static final String COUNT = "count";
        public static final String SUM = "sum";
    }
}
