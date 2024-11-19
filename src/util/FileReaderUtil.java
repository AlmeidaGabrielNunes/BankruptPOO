package util;

import model.Property;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileReaderUtil {
    public static List<Property> loadProperties(String filePath) {
        List<Property> properties = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Remove espaços extras
                if (line.isEmpty()) continue; // Ignora linhas vazias

                String[] parts = line.split(" ");
                if (parts.length != 2) {
                    System.err.println("Linha inválida ignorada: " + line);
                    continue;
                }

                try {
                    int price = Integer.parseInt(parts[0]);
                    int rent = Integer.parseInt(parts[1]);
                    properties.add(new Property(price, rent));
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao processar linha: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
