import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GenerateInputFile {

    private static final String[] COMMANDS = {
            "ADD", "REMOVE", "SEARCH", "UPDATE"
    };
    
    private static final String[] SYMBOLS = {
            "AAPL", "GOOGL", "AMZN", "FB", "NFLX", "MSFT", "TSLA", "JPM", "V", "BABA"
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many commands would you like to create? ");
        int numberOfCommands = scanner.nextInt();
        generateInputFile(numberOfCommands);
        scanner.close();
    }

    private static void generateInputFile(int numberOfCommands) {
        Random random = new Random();
        try (FileWriter writer = new FileWriter("input.txt")) {
            for (int i = 0; i < numberOfCommands; i++) {
                String command = COMMANDS[random.nextInt(COMMANDS.length)];
                String symbol1 = SYMBOLS[random.nextInt(SYMBOLS.length)];
                String line = "";
                switch (command) {
                    case "ADD":
                        line = String.format("%s %s %.2f %d %d", command, symbol1, 
                                random.nextDouble() * 4000, 
                                random.nextInt(1000000), 
                                (long)(Math.random() * 5000000000L));
                        break;
                    case "REMOVE":
                        line = String.format("%s %s", command, symbol1);
                        break;
                    case "SEARCH":
                        line = String.format("%s %s", command, symbol1);
                        break;
                    case "UPDATE":
                        String symbol2 = SYMBOLS[random.nextInt(SYMBOLS.length)];
                        line = String.format("%s %s %s %.2f %d %d", command, symbol1, symbol2, 
                                random.nextDouble() * 4000, 
                                random.nextInt(1000000), 
                                (long)(Math.random() * 5000000000L));
                        break;
                }
                writer.write(line + "\n");
            }
            System.out.println("input.txt file was created successfully!");
        } catch (IOException e) {
            System.err.println("File write error: " + e.getMessage());
        }
    }
}
