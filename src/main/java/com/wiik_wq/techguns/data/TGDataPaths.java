package com.wiik_wq.techguns.data;

import java.nio.file.Files;
import java.nio.file.Path;

public final class TGDataPaths {

    private static final String LEGACY_SOURCE_DIR = "techguns_old";
    private static final Path PROJECT_ROOT = findProjectRoot();

    private TGDataPaths() {
    }

    public static Path resolve(String first, String... more) {
        return PROJECT_ROOT.resolve(Path.of(first, more));
    }

    public static Path resolveLegacy(String first, String... more) {
        Path path = PROJECT_ROOT.resolve(LEGACY_SOURCE_DIR).resolve(first);
        for (String segment : more) {
            path = path.resolve(segment);
        }
        return path;
    }

    public static boolean hasLegacySource() {
        return Files.isDirectory(PROJECT_ROOT.resolve(LEGACY_SOURCE_DIR));
    }

    private static Path findProjectRoot() {
        Path current = Path.of("").toAbsolutePath().normalize();

        for (Path path = current; path != null; path = path.getParent()) {
            if (Files.exists(path.resolve("build.gradle"))
                    && Files.isDirectory(path.resolve("src"))) {
                return path;
            }
        }

        return current;
    }
}
