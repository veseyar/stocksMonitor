package com.veseyar.monitor.client.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IoTools {

    public static void checkDirectoryExists(Path path) throws IOException {
        if (!(Files.exists(path) && Files.isDirectory(path)))
            throw new IOException(String.format("Directory %s does not exist", path));
    }

    public static void createDirectoryIfNotExists(Path path) throws IOException {
        try {
            checkDirectoryExists(path);
        } catch (IOException e) {
            Files.createDirectory(path);
        }
    }

    public static void createFileIfNotExists(String path) throws IOException {
        Path filePath = Paths.get(path);
        try {
            checkDirectoryExists(filePath);
        } catch (IOException e) {
            Files.createFile(filePath);
        }
    }

}
