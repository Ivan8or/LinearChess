package util;

import java.util.Optional;

public class ResourceAsString {


    public static Optional<String> at(String path) {
        try {
            return Optional.of(
                    new String(ResourceAsString.class.getClassLoader()
                            .getResourceAsStream(path)
                            .readAllBytes()
                    ));
        } catch (Exception e) {
            throw new IllegalStateException("Missing test resource file "+path);
        }
    }
}
