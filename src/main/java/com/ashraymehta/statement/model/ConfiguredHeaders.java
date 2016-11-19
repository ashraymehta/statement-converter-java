package com.ashraymehta.statement.model;

import java.util.ArrayList;
import java.util.Optional;

public class ConfiguredHeaders extends ArrayList<ConfiguredHeader> {

    public Optional<ConfiguredHeader> get(String name) {
        for (ConfiguredHeader configuredHeader : this) {
            if (configuredHeader.hasNameOrAlias(name))
                return Optional.of(configuredHeader);
        }
        return Optional.empty();
    }

    public String getOutputValue(char separator) {
        StringBuilder builder = new StringBuilder();
        forEach(headerConfig -> builder.append(headerConfig.getOutputField()).append(separator));
        return builder.toString();
    }
}