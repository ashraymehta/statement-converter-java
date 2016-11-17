package com.ashraymehta.statement;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;

class Arguments {
    @Parameter(names = {"-f", "--file"}, converter = FileConverter.class, required = true)
    File file;
}