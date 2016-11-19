package com.ashraymehta.statement.model;

import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConfiguredHeaderTest {

    @Test
    public void shouldTellThatItHasNameOrAliasSameAsAGivenString() {
        final ConfiguredHeader configuredHeader = new ConfiguredHeader("name", "Name", Collections.singletonList("firstName"));

        assertThat(configuredHeader.hasNameOrAlias("name"), is(true));
        assertThat(configuredHeader.hasNameOrAlias("firstName"), is(true));
        assertThat(configuredHeader.hasNameOrAlias("hello"), is(false));
    }

    @Test
    public void shouldMatchNameIfThereAreNoAliases() {
        final ConfiguredHeader configuredHeader = new ConfiguredHeader("name", "Name", null);

        assertThat(configuredHeader.hasNameOrAlias("name"), is(true));
        assertThat(configuredHeader.hasNameOrAlias("hello"), is(false));
    }
}