package com.ashraymehta.statement.util;

import org.junit.Test;

import static com.ashraymehta.statement.util.StringUtil.parseAsAmount;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StringUtilTest {

    @Test
    public void shouldRemoveAlphabetsFromString() {
        assertThat(parseAsAmount("100AB"), is("100"));
    }

    @Test
    public void shouldKeepTheFirstDotInString() {
        assertThat(parseAsAmount("100.0AB"), is("100.0"));
    }

    @Test
    public void shouldRemoveCharactersAfterSecondDotInString() {
        assertThat(parseAsAmount("100.0.123AB"), is("100.0"));
        assertThat(parseAsAmount("100..012AB"), is("100"));
        assertThat(parseAsAmount("100..012.AB"), is("100"));
        assertThat(parseAsAmount("180.50 Dr."), is("180.50"));
    }

    @Test
    public void shouldTreatCharactersCrAsNegativeAmount() {
        assertThat(parseAsAmount("100.0 Cr."), is("-100.0"));
    }
}