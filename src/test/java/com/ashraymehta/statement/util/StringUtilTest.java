package com.ashraymehta.statement.util;

import org.junit.Test;

import static com.ashraymehta.statement.util.StringUtil.removeNonNumericCharacters;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StringUtilTest {

    @Test
    public void shouldRemoveAlphabetsFromString() {
        assertThat(removeNonNumericCharacters("100AB"), is("100"));
    }

    @Test
    public void shouldKeepTheFirstDotInString() {
        assertThat(removeNonNumericCharacters("100.0AB"), is("100.0"));
    }

    @Test
    public void shouldRemoveCharactersAfterSecondDotInString() {
        assertThat(removeNonNumericCharacters("100.0.123AB"), is("100.0"));
        assertThat(removeNonNumericCharacters("100..012AB"), is("100"));
        assertThat(removeNonNumericCharacters("100..012.AB"), is("100"));
        assertThat(removeNonNumericCharacters("180.50 Dr."), is("180.50"));
    }
}