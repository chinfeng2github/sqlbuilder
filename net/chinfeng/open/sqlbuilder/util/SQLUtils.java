package net.chinfeng.open.sqlbuilder.util;

import net.chinfeng.open.sqlbuilder.sql.SQLKeyWords;

public abstract class SQLUtils {
    public static String quote(String content) {
        return SQLKeyWords.QUOTE + content + SQLKeyWords.QUOTE;
    }

    public static String backquote(String content) {
        return SQLKeyWords.BACKQUOTE + content + SQLKeyWords.BACKQUOTE;
    }

    public static String space(String content) {
        return new StringBuilder(SQLKeyWords.SPACE).append(content).append(SQLKeyWords.SPACE).toString();
    }

    public static String leftSpace(String content) {
        return new StringBuilder(SQLKeyWords.SPACE).append(content).toString();
    }

    public static String rightSpace(String content) {
        return new StringBuilder(String.valueOf(content)).append(SQLKeyWords.SPACE).toString();
    }

    public static String function(String funName, String params) {
        return funName + SQLKeyWords.LEFTBRACKET + params + SQLKeyWords.RIGHTBRACKET;
    }

    public static String alias(String param, String alias) {
        return space(param) + SQLKeyWords.AS + SQLKeyWords.SPACE + backquote(alias);
    }
}
