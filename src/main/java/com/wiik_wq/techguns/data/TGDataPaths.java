package com.wiik_wq.techguns.data;

import java.nio.file.Files;
import java.nio.file.Path;

public final class TGDataPaths {

    private static final Path PROJECT_ROOT = findProjectRoot();

    private TGDataPaths() {
    }

    public static Path resolve(String first, String... more) {
        return PROJECT_ROOT.resolve(Path.of(first, more));
    }

    private static Path findProjectRoot() {
        Path current = Path.of("").toAbsolutePath().normalize();

        for (Path path = current; path != null; path = path.getParent()) {
            if (Files.exists(path.resolve("build.gradle"))
                    && Files.isDirectory(path.resolve("src"))
                    && Files.isDirectory(path.resolve("techguns_old"))) {
                return path;
            }
        }

        return current;
    }
}
