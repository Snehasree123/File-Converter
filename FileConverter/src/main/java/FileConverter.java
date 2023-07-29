
import java.io.*;
import java.util.*;

public class FileConverter {
    
    // Convert command implementation
    public static void convert(String source, String destination) {
        try {
            // Get the file path from the resources folder
            ClassLoader classLoader = FileConverter.class.getClassLoader();
            File file = new File(classLoader.getResource(source).getFile());
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(destination));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] cells = line.split(",");
                for (int i = 0; i < cells.length; i++) {
                    if (i > 0) {
                        writer.write("\t");
                    }
                    writer.write(cells[i]);
                }
                writer.newLine();
            }
            reader.close();
            writer.close();
            System.out.println("Conversion completed.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    // Normalize command implementation
    public static void normalize(String source) {
        try {
            // Get the file path from the resources folder
            ClassLoader classLoader = FileConverter.class.getClassLoader();
            File file = new File(classLoader.getResource(source).getFile());
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] cells = line.split(",");
                for (int i = 0; i < cells.length; i++) {
                    String cell = cells[i];
                    if (cell.isEmpty()) {
                        cells[i] = "N/A";
                    } else if (cell.matches("-?\\d+")) {
                        int value = Integer.parseInt(cell);
                        cells[i] = String.format("%s%010d", value >= 0 ? "+" : "-", Math.abs(value));
                    } else if (cell.matches("-?\\d+\\.\\d+")) {
                        double value = Double.parseDouble(cell);
                        if (value > 100 || value < 0.01) {
                            cells[i] = String.format("%.2e", value);
                        } else {
                            cells[i] = String.format("%.2f", value);
                        }
                    } else if (cell.length() > 13) {
                        cells[i] = cell.substring(0, 10) + "...";
                    }
                }
                lines.add(String.join("\t", cells));
            }
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(source));
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
            writer.close();
            System.out.println("Normalization completed.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String getFileExtension(String fileName) {
        String extension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1).toLowerCase();
        }
        return extension;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Enter command: \n format: 1. convert source.xxx destination.yyy \t 2. normalize source.xxx");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            // format: convert source.xxx destination.yyy
            // format: normalize source.xxx
            if (parts.length > 0) {
                switch (parts[0]) {
                    case "convert":
                        if (parts.length == 3 && (parts[1].endsWith(".csv") || parts[1].endsWith(".txt")) &&
                                (parts[2].endsWith(".csv") || parts[2].endsWith(".txt")) && !getFileExtension(parts[1]).equalsIgnoreCase(getFileExtension(parts[2]))) {
                            convert(parts[1], parts[2]);
                        } else {
                            System.out.println("source and destination files extension can only be either csv or txt and both should not be same");
                        }
                        break;
                    case "normalize":
                        if (parts.length == 2 && (parts[1].endsWith(".csv") || parts[1].endsWith(".txt"))) {
                            normalize(parts[1]);
                        } else {
                            System.out.println("source file extension can only be either csv or txt");
                        }
                        break;
                    case "quit":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid command.");
                        break;
                }
            }
        }
        scanner.close();
    }
}
