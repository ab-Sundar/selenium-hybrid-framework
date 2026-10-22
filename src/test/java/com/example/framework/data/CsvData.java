package com.example.framework.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class CsvData {
    private CsvData() {}
    // For this demo, credentials are simple comma-separated fields without quoted commas.
    public static Object[][] read(String resource) {
        try (InputStream in = CsvData.class.getClassLoader().getResourceAsStream(resource)) {
            if (in == null) throw new IllegalArgumentException("Missing CSV resource: " + resource);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                List<Object[]> rows = new ArrayList<>();
                String line;
                boolean header = true;
                while ((line = reader.readLine()) != null) {
                    if (header) { header = false; continue; }
                    if (line.isBlank() || line.startsWith("#")) continue;
                    String[] fields = line.split(",", -1);
                    if (fields.length != 2) throw new IllegalArgumentException("Expected 2 columns: " + line);
                    rows.add(new Object[]{fields[0].trim(), fields[1].trim()});
                }
                return rows.toArray(new Object[0][]);
            }
        } catch (Exception e) { throw new IllegalStateException("Unable to load CSV: " + resource, e); }
    }
}
