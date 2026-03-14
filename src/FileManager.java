import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class FileManager {


    public static String readBufferedToFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(Path.of(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }


    public static void writeBufferedToFile(String path, String content) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
            writer.write(content);
        }
    }
}