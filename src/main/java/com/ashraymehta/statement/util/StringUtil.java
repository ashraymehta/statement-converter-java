package com.ashraymehta.statement.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    private static final char DOT_CHARACTER = '.';

    public static String removeNonNumericCharacters(String stringValue) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringValue.chars()
                .mapToObj(i -> (char) i)
                .filter((ch) -> Character.isDigit(ch) || ch.equals(DOT_CHARACTER))
                .forEach(stringBuilder::append);

        final int dotOccurrences = StringUtils.countMatches(stringBuilder, DOT_CHARACTER);
        if (dotOccurrences > 1) {
            final int firstIndex = stringBuilder.indexOf(String.valueOf(DOT_CHARACTER));
            int nextIndex = stringBuilder.indexOf(String.valueOf(DOT_CHARACTER), firstIndex + 1);
            stringBuilder.delete(nextIndex, stringBuilder.length());
        }

        if (stringBuilder.charAt(stringBuilder.length() - 1) == DOT_CHARACTER) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        return stringBuilder.toString();
    }
}