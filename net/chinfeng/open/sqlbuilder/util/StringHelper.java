package net.chinfeng.open.sqlbuilder.util;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import net.chinfeng.open.sqlbuilder.sql.SQLKeyWords;

public class StringHelper {
    private static final int ALIAS_TRUNCATE_LENGTH = 10;
    public static final String[] EMPTY_STRINGS;
    public static final String WHITESPACE = " \n\r\f\t";

    public interface Renderer<T> {
        String render(T t);
    }

    static {
        EMPTY_STRINGS = new String[0];
    }

    private StringHelper() {
    }

    public static int lastIndexOfLetter(String string) {
        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            if (!Character.isLetter(character) && '_' != character) {
                return i - 1;
            }
        }
        return string.length() - 1;
    }

    public static String join(String seperator, String[] strings) {
        int length = strings.length;
        if (length == 0) {
            return "";
        }
        StringBuilder buf = new StringBuilder(strings[0].length() * length).append(strings[0]);
        for (int i = 1; i < length; i++) {
            buf.append(seperator).append(strings[i]);
        }
        return buf.toString();
    }

    public static String joinWithQualifierAndSuffix(String[] values, String qualifier, String suffix, String deliminator) {
        int length = values.length;
        if (length == 0) {
            return "";
        }
        StringBuilder buf = new StringBuilder((values[0].length() + suffix.length()) * length).append(qualify(qualifier, values[0])).append(suffix);
        for (int i = 1; i < length; i++) {
            buf.append(deliminator).append(qualify(qualifier, values[i])).append(suffix);
        }
        return buf.toString();
    }

    public static String join(String seperator, Iterator objects) {
        StringBuilder buf = new StringBuilder();
        if (objects.hasNext()) {
            buf.append(objects.next());
        }
        while (objects.hasNext()) {
            buf.append(seperator).append(objects.next());
        }
        return buf.toString();
    }

    public static String join(String separator, Iterable objects) {
        return join(separator, objects.iterator());
    }

    public static String[] add(String[] x, String sep, String[] y) {
        String[] result = new String[x.length];
        for (int i = 0; i < x.length; i++) {
            result[i] = x[i] + sep + y[i];
        }
        return result;
    }

    public static String repeat(String string, int times) {
        StringBuilder buf = new StringBuilder(string.length() * times);
        for (int i = 0; i < times; i++) {
            buf.append(string);
        }
        return buf.toString();
    }

    public static String repeat(String string, int times, String deliminator) {
        StringBuilder buf = new StringBuilder((string.length() * times) + (deliminator.length() * (times - 1))).append(string);
        for (int i = 1; i < times; i++) {
            buf.append(deliminator).append(string);
        }
        return buf.toString();
    }

    public static String repeat(char character, int times) {
        char[] buffer = new char[times];
        Arrays.fill(buffer, character);
        return new String(buffer);
    }

    public static String replace(String template, String placeholder, String replacement) {
        return replace(template, placeholder, replacement, false);
    }

    public static String[] replace(String[] templates, String placeholder, String replacement) {
        String[] result = new String[templates.length];
        for (int i = 0; i < templates.length; i++) {
            result[i] = replace(templates[i], placeholder, replacement);
        }
        return result;
    }

    public static String replace(String template, String placeholder, String replacement, boolean wholeWords) {
        return replace(template, placeholder, replacement, wholeWords, false);
    }

    public static String replace(String template, String placeholder, String replacement, boolean wholeWords, boolean encloseInParensIfNecessary) {
        if (template == null) {
            return null;
        }
        int loc = template.indexOf(placeholder);
        return loc >= 0 ? replace(template.substring(0, loc), template.substring(placeholder.length() + loc), placeholder, replacement, wholeWords, encloseInParensIfNecessary) : template;
    }

    public static String replace(String beforePlaceholder, String afterPlaceholder, String placeholder, String replacement, boolean wholeWords, boolean encloseInParensIfNecessary) {
        boolean actuallyReplace;
        String str;
        boolean encloseInParens = true;
        if (wholeWords && afterPlaceholder.length() != 0 && Character.isJavaIdentifierPart(afterPlaceholder.charAt(0))) {
            actuallyReplace = false;
        } else {
            actuallyReplace = true;
        }
        if (!(actuallyReplace && encloseInParensIfNecessary && getLastNonWhitespaceCharacter(beforePlaceholder) != '(')) {
            encloseInParens = false;
        }
        StringBuilder buf = new StringBuilder(beforePlaceholder);
        if (encloseInParens) {
            buf.append('(');
        }
        if (actuallyReplace) {
            str = replacement;
        } else {
            str = placeholder;
        }
        buf.append(str);
        if (encloseInParens) {
            buf.append(')');
        }
        buf.append(replace(afterPlaceholder, placeholder, replacement, wholeWords, encloseInParensIfNecessary));
        return buf.toString();
    }

    public static char getLastNonWhitespaceCharacter(String str) {
        if (str != null && str.length() > 0) {
            for (int i = str.length() - 1; i >= 0; i--) {
                char ch = str.charAt(i);
                if (!Character.isWhitespace(ch)) {
                    return ch;
                }
            }
        }
        return '\u0000';
    }

    public static char getFirstNonWhitespaceCharacter(String str) {
        if (str != null && str.length() > 0) {
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (!Character.isWhitespace(ch)) {
                    return ch;
                }
            }
        }
        return '\u0000';
    }

    public static String replaceOnce(String template, String placeholder, String replacement) {
        if (template == null) {
            return null;
        }
        int loc = template.indexOf(placeholder);
        return loc >= 0 ? template.substring(0, loc) + replacement + template.substring(placeholder.length() + loc) : template;
    }

    public static String[] split(String separators, String list) {
        return split(separators, list, false);
    }

    public static String[] split(String separators, String list, boolean include) {
        StringTokenizer tokens = new StringTokenizer(list, separators, include);
        String[] result = new String[tokens.countTokens()];
        int i = 0;
        while (tokens.hasMoreTokens()) {
            int i2 = i + 1;
            result[i] = tokens.nextToken();
            i = i2;
        }
        return result;
    }

    public static String[] splitTrimmingTokens(String separators, String list, boolean include) {
        StringTokenizer tokens = new StringTokenizer(list, separators, include);
        String[] result = new String[tokens.countTokens()];
        int i = 0;
        while (tokens.hasMoreTokens()) {
            int i2 = i + 1;
            result[i] = tokens.nextToken().trim();
            i = i2;
        }
        return result;
    }

    public static String unqualify(String qualifiedName) {
        int loc = qualifiedName.lastIndexOf(".");
        return loc < 0 ? qualifiedName : qualifiedName.substring(loc + 1);
    }

    public static String qualifier(String qualifiedName) {
        int loc = qualifiedName.lastIndexOf(".");
        return loc < 0 ? "" : qualifiedName.substring(0, loc);
    }

    public static String collapse(String name) {
        if (name == null) {
            return null;
        }
        int breakPoint = name.lastIndexOf(46);
        return breakPoint >= 0 ? new StringBuilder(String.valueOf(collapseQualifier(name.substring(0, breakPoint), true))).append(name.substring(breakPoint)).toString() : name;
    }

    public static String collapseQualifier(String qualifier, boolean includeDots) {
        StringTokenizer tokenizer = new StringTokenizer(qualifier, ".");
        String collapsed = Character.toString(tokenizer.nextToken().charAt(0));
        while (tokenizer.hasMoreTokens()) {
            if (includeDots) {
                collapsed = new StringBuilder(String.valueOf(collapsed)).append('.').toString();
            }
            collapsed = new StringBuilder(String.valueOf(collapsed)).append(tokenizer.nextToken().charAt(0)).toString();
        }
        return collapsed;
    }

    public static String partiallyUnqualify(String name, String qualifierBase) {
        return (name == null || !name.startsWith(qualifierBase)) ? name : name.substring(qualifierBase.length() + 1);
    }

    public static String collapseQualifierBase(String name, String qualifierBase) {
        if (name == null || !name.startsWith(qualifierBase)) {
            return collapse(name);
        }
        return collapseQualifier(qualifierBase, true) + name.substring(qualifierBase.length());
    }

    public static String[] suffix(String[] columns, String suffix) {
        if (suffix == null) {
            return columns;
        }
        String[] qualified = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            qualified[i] = suffix(columns[i], suffix);
        }
        return qualified;
    }

    private static String suffix(String name, String suffix) {
        return suffix == null ? name : new StringBuilder(String.valueOf(name)).append(suffix).toString();
    }

    public static String root(String qualifiedName) {
        int loc = qualifiedName.indexOf(".");
        return loc < 0 ? qualifiedName : qualifiedName.substring(0, loc);
    }

    public static String unroot(String qualifiedName) {
        int loc = qualifiedName.indexOf(".");
        return loc < 0 ? qualifiedName : qualifiedName.substring(loc + 1, qualifiedName.length());
    }

    public static boolean booleanValue(String tfString) {
        String trimmed = tfString.trim().toLowerCase(Locale.ROOT);
        return trimmed.equals("true") || trimmed.equals("t");
    }

    public static String toString(Object[] array) {
        int len = array.length;
        if (len == 0) {
            return "";
        }
        StringBuilder buf = new StringBuilder(len * 12);
        for (int i = 0; i < len - 1; i++) {
            buf.append(array[i]).append(", ");
        }
        return buf.append(array[len - 1]).toString();
    }

    public static String[] multiply(String string, Iterator placeholders, Iterator replacements) {
        String[] result = new String[]{string};
        while (placeholders.hasNext()) {
            result = multiply(result, (String) placeholders.next(), (String[]) replacements.next());
        }
        return result;
    }

    private static String[] multiply(String[] strings, String placeholder, String[] replacements) {
        String[] results = new String[(replacements.length * strings.length)];
        int n = 0;
        int length = replacements.length;
        int i = 0;
        while (i < length) {
            String replacement = replacements[i];
            int length2 = strings.length;
            int i2 = 0;
            int n2 = n;
            while (i2 < length2) {
                n = n2 + 1;
                results[n2] = replaceOnce(strings[i2], placeholder, replacement);
                i2++;
                n2 = n;
            }
            i++;
            n = n2;
        }
        return results;
    }

    public static int countUnquoted(String string, char character) {
        if ('\'' == character) {
            throw new IllegalArgumentException("Unquoted count of quotes is invalid");
        } else if (string == null) {
            return 0;
        } else {
            int count = 0;
            int stringLength = string.length();
            boolean inQuote = false;
            for (int indx = 0; indx < stringLength; indx++) {
                char c = string.charAt(indx);
                if (inQuote) {
                    if ('\'' == c) {
                        inQuote = false;
                    }
                } else if ('\'' == c) {
                    inQuote = true;
                } else if (c == character) {
                    count++;
                }
            }
            return count;
        }
    }

    public static boolean isNotEmpty(String string) {
        return string != null && string.length() > 0;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static boolean isEmptyOrWhiteSpace(String string) {
        return isEmpty(string) || isEmpty(string.trim());
    }

    public static String qualify(String prefix, String name) {
        if (name != null && prefix != null) {
            return new StringBuilder(String.valueOf(prefix)).append('.').append(name).toString();
        }
        throw new NullPointerException("prefix or name were null attempting to build qualified name");
    }

    public static String[] qualify(String prefix, String[] names) {
        if (prefix == null) {
            return names;
        }
        int len = names.length;
        String[] qualified = new String[len];
        for (int i = 0; i < len; i++) {
            qualified[i] = qualify(prefix, names[i]);
        }
        return qualified;
    }

    public static String[] qualifyIfNot(String prefix, String[] names) {
        if (prefix == null) {
            return names;
        }
        int len = names.length;
        String[] qualified = new String[len];
        for (int i = 0; i < len; i++) {
            if (names[i].indexOf(46) < 0) {
                qualified[i] = qualify(prefix, names[i]);
            } else {
                qualified[i] = names[i];
            }
        }
        return qualified;
    }

    public static int firstIndexOfChar(String sqlString, BitSet keys, int startindex) {
        int size = sqlString.length();
        for (int i = startindex; i < size; i++) {
            if (keys.get(sqlString.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public static int firstIndexOfChar(String sqlString, String string, int startindex) {
        BitSet keys = new BitSet();
        int size = string.length();
        for (int i = 0; i < size; i++) {
            keys.set(string.charAt(i));
        }
        return firstIndexOfChar(sqlString, keys, startindex);
    }

    public static String truncate(String string, int length) {
        return string.length() <= length ? string : string.substring(0, length);
    }

    public static String generateAlias(String description) {
        return generateAliasRoot(description) + '_';
    }

    public static String generateAlias(String description, int unique) {
        return generateAliasRoot(description) + Integer.toString(unique) + '_';
    }

    private static String generateAliasRoot(String description) {
        String result = cleanAlias(truncate(unqualifyEntityName(description), ALIAS_TRUNCATE_LENGTH).toLowerCase(Locale.ROOT).replace('/', '_').replace('$', '_'));
        if (Character.isDigit(result.charAt(result.length() - 1))) {
            return new StringBuilder(String.valueOf(result)).append("x").toString();
        }
        return result;
    }

    private static String cleanAlias(String alias) {
        char[] chars = alias.toCharArray();
        if (Character.isLetter(chars[0])) {
            return alias;
        }
        for (int i = 1; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                return alias.substring(i);
            }
        }
        return alias;
    }

    public static String unqualifyEntityName(String entityName) {
        String result = unqualify(entityName);
        int slashPos = result.indexOf(47);
        if (slashPos > 0) {
            return result.substring(0, slashPos - 1);
        }
        return result;
    }

    public static String moveAndToBeginning(String filter) {
        if (filter.trim().length() <= 0) {
            return filter;
        }
        filter = new StringBuilder(String.valueOf(filter)).append(" and ").toString();
        if (filter.startsWith(" and ")) {
            return filter.substring(4);
        }
        return filter;
    }

    public static boolean isQuoted(String name) {
        if (name == null || name.length() == 0) {
            return false;
        }
        return (name.charAt(0) == '`' && name.charAt(name.length() - 1) == '`') || (name.charAt(0) == '\"' && name.charAt(name.length() - 1) == '\"');
    }

    public static String quote(String name) {
        if (isEmpty(name) || isQuoted(name)) {
            return name;
        }
        if (name.startsWith("\"") && name.endsWith("\"")) {
            name = name.substring(1, name.length() - 1);
        }
        return new StringBuilder(SQLKeyWords.BACKQUOTE).append(name).append('`').toString();
    }

    public static String unquote(String name) {
        return isQuoted(name) ? name.substring(1, name.length() - 1) : name;
    }

    public static String[] toArrayElement(String s) {
        if (s == null || s.length() == 0) {
            return new String[0];
        }
        return new String[]{s};
    }

    public static String nullIfEmpty(String value) {
        return isEmpty(value) ? null : value;
    }

    public static List<String> parseCommaSeparatedString(String incomingString) {
        return Arrays.asList(incomingString.split("\\s*,\\s*"));
    }

    public static <T> String join(Collection<T> values, Renderer<T> renderer) {
        StringBuilder buffer = new StringBuilder();
        boolean firstPass = true;
        Iterator it = values.iterator();
        while (it.hasNext()) {
            Object value = it.next();
            if (firstPass) {
                firstPass = false;
            } else {
                buffer.append(", ");
            }
            buffer.append(renderer.render(value));
        }
        return buffer.toString();
    }

    public static <T> String join(T[] values, Renderer<T> renderer) {
        return join(Arrays.asList(values), (Renderer) renderer);
    }
}
