package com.lobato.desafiomeetime.config.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public abstract class JsonUtils {

    public static String getJsonContentFromFile(String folder, String fileName) {
        String path = "json/%s/%s.json".formatted(folder, fileName);
        try (InputStream inputStream = JsonUtils.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new RuntimeException("Arquivo não encontrado: " + path);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo: " + path, e);
        }
    }
}
