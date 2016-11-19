package com.ashraymehta.statement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.poi.ss.util.CellAddress;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfiguredHeader {
    @JsonProperty
    private String name;
    @JsonProperty
    private String outputField;
    @JsonProperty
    private List<String> aliases;
    @JsonProperty
    private boolean isNumeric;

    private CellAddress address;

    ConfiguredHeader() {
    }

    ConfiguredHeader(String name, String outputField, List<String> aliases) {
        this.name = name;
        this.outputField = outputField;
        this.aliases = aliases;
    }

    boolean hasNameOrAlias(String text) {
        if (StringUtils.isNotEmpty(name) && name.equals(text)) {
            return true;
        }
        if (CollectionUtils.isNotEmpty(aliases) && aliases.contains(text)) {
            return true;
        }
        return false;
    }

    public String getOutputField() {
        return outputField;
    }

    public void setAddress(CellAddress address) {
        this.address = address;
    }

    public int getRow() {
        return this.address.getRow();
    }

    public int getColumn() {
        return this.address.getColumn();
    }

    private boolean isNumeric() {
        return isNumeric;
    }

    public boolean wasFound() {
        return this.address != null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}