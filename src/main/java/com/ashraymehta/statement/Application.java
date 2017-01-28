package com.ashraymehta.statement;

import com.beust.jcommander.JCommander;

import java.io.IOException;

public class Application {
    public static void main(String... args) throws IOException {
        final Arguments arguments = new Arguments();
        new JCommander(arguments, args);

        new XLSConverter().convert(arguments.file);
    }
}