package net.chinfeng.open.sqlbuilder.sql;

import net.chinfeng.open.sqlbuilder.dialect.Dialect;

public final class Alias {
    private final int length;
    private final String suffix;

    public Alias(int length, String suffix) {
        if (suffix != null) {
            length -= suffix.length();
        }
        this.length = length;
        this.suffix = suffix;
    }

    public Alias(String suffix) {
        this.length = Integer.MAX_VALUE;
        this.suffix = suffix;
    }

    public String toAliasString(String sqlIdentifier) {
        char begin = sqlIdentifier.charAt(0);
        int quoteType = Dialect.QUOTE.indexOf(begin);
        String unquoted = getUnquotedAliasString(sqlIdentifier, quoteType);
        if (quoteType < 0) {
            return unquoted;
        }
        return new StringBuilder(String.valueOf(begin)).append(unquoted).append(Dialect.CLOSED_QUOTE.charAt(quoteType)).toString();
    }

    public String toUnquotedAliasString(String sqlIdentifier) {
        return getUnquotedAliasString(sqlIdentifier);
    }

    private String getUnquotedAliasString(String sqlIdentifier) {
        return getUnquotedAliasString(sqlIdentifier, Dialect.QUOTE.indexOf(sqlIdentifier.charAt(0)));
    }

    private String getUnquotedAliasString(String sqlIdentifier, int quoteType) {
        String unquoted = sqlIdentifier;
        if (quoteType >= 0) {
            unquoted = unquoted.substring(1, unquoted.length() - 1);
        }
        if (unquoted.length() > this.length) {
            unquoted = unquoted.substring(0, this.length);
        }
        return this.suffix == null ? unquoted : new StringBuilder(String.valueOf(unquoted)).append(this.suffix).toString();
    }

    public String[] toUnquotedAliasStrings(String[] sqlIdentifiers) {
        String[] aliases = new String[sqlIdentifiers.length];
        for (int i = 0; i < sqlIdentifiers.length; i++) {
            aliases[i] = toUnquotedAliasString(sqlIdentifiers[i]);
        }
        return aliases;
    }

    public String[] toAliasStrings(String[] sqlIdentifiers) {
        String[] aliases = new String[sqlIdentifiers.length];
        for (int i = 0; i < sqlIdentifiers.length; i++) {
            aliases[i] = toAliasString(sqlIdentifiers[i]);
        }
        return aliases;
    }
}
