package com.lobato.desafiomeetime.config.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public abstract class JsonUtils {
    public static String getJsonContentFromFile(String folder, String fileName) {
        String path = "%s/%s.json".formatted(folder, fileName);
        ClassLoader classLoader = JsonUtils.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new RuntimeException("Arquivo não encontrado: " + path);
            }
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo: " + path, e);
        }
    }
}
