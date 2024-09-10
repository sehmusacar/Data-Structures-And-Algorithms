import java.util.Scanner;
/**
 * Main class for the file system management application.
 * Provides a command line interface to manage files and directories using various operations.
 */
public class Main {
    /**
     * Main method that drives the application. It initializes the file system and processes user commands.
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean running = true;

        while (running) {
            System.out.println("\n===== File System Management Menu =====");
            System.out.println("Current directory: " + fs.getCurrentPath());
            System.out.println("1. Change directory");
            System.out.println("2. List directory contents");
            System.out.println("3. Create file/directory");
            System.out.println("4. Delete file/directory");
            System.out.println("5. Move file/directory");
            System.out.println("6. Search for file/directory");
            System.out.println("7. Print directory tree");
            System.out.println("8. Sort directory by date");
            System.out.println("9. Exit");
            System.out.println("Type 'help' for more options.");
            System.out.print("Select an option or 'help': ");
            input = scanner.nextLine();

            switch (input) {
                case "help":
                    printHelp();
                    break;
                case "1":
                    handleChangeDirectory(fs, scanner);
                    break;
                case "2":
                    fs.listContents();
                    break;
                case "3":
                    handleCreate(fs, scanner);
                    break;
                case "4":
                    handleDelete(fs, scanner);
                    break;
                case "5":
                    handleMove(fs, scanner);
                    break;
                case "6":
                    handleSearch(fs, scanner);
                    break;
                case "7":
                    fs.printCurrentDirectoryTree();
                    break;
                case "8":
                    fs.sortDirectoryByDate(fs.getCurrentDirectory());
                    break;
                case "9":
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    /**
     * Handles changing the current directory based on user input.
     * @param fs The file system instance.
     * @param scanner The scanner for reading user input.
     */
    private static void handleChangeDirectory(FileSystem fs, Scanner scanner) {
        System.out.println("Current directory: " + fs.getCurrentPath());  // Show the current directory
        System.out.print("Enter directory name or '..' to go up: ");
        fs.changeDirectory(scanner.nextLine());
    }

    /**
     * Handles creating a new file or directory based on user input.
     * @param fs The file system instance.
     * @param scanner The scanner for reading user input.
     */
    private static void handleCreate(FileSystem fs, Scanner scanner) {
        System.out.println("Current directory: " + fs.getCurrentPath());  // Show the current directory
        System.out.print("Create file or directory (f/d): ");
        String type = scanner.nextLine();
        if (!type.equalsIgnoreCase("f") && !type.equalsIgnoreCase("d")) {
            System.out.println("Invalid type. Please enter 'f' for file or 'd' for directory.");
            return;
        }
        System.out.print("Enter name for new " + (type.equalsIgnoreCase("f") ? "file" : "directory") + ": ");
        String name = scanner.nextLine();
        fs.createFileOrDirectory(type, name);
        System.out.println((type.equalsIgnoreCase("f") ? "File" : "Directory") + " created: " + name + (type.equalsIgnoreCase("d") ? "/" : ""));
    }
    
    /**
     * Handles deleting a specified file or directory based on user input.
     * @param fs The file system instance.
     * @param scanner The scanner for reading user input.
     */
    private static void handleDelete(FileSystem fs, Scanner scanner) {
        System.out.print("Enter name of file/directory to delete: ");
        String name = scanner.nextLine();
        fs.deleteFileOrDirectory(name);
    }

    /**
     * Handles moving a specified file or directory to a new location based on user input.
     * @param fs The file system instance.
     * @param scanner The scanner for reading user input.
     */
    private static void handleMove(FileSystem fs, Scanner scanner) {
        System.out.print("Enter the name of file/directory to move: ");
        String moveName = scanner.nextLine();
        System.out.print("Enter new directory path: ");
        String newPath = scanner.nextLine();
        fs.moveFileOrDirectory(moveName, newPath);
    }

    /**
     * Handles searching for a file or directory based on user input.
     * @param fs The file system instance.
     * @param scanner The scanner for reading user input.
     */
    private static void handleSearch(FileSystem fs, Scanner scanner) {
        System.out.print("Enter name to search for: ");
        String searchName = scanner.nextLine();
        fs.search(searchName);
    }

    /**
     * Prints the help menu detailing all available commands.
     */
    private static void printHelp() {
        System.out.println("Help Menu:");
        System.out.println("1: Change directory - Change the current directory.");
        System.out.println("2: List contents - List all files and directories in the current directory.");
        System.out.println("3: Create - Create a new file or directory.");
        System.out.println("4: Delete - Delete a specified file or directory.");
        System.out.println("5: Move - Move a specified file or directory to a new location.");
        System.out.println("6: Search - Search for a file or directory.");
        System.out.println("7: Print tree - Print the directory tree from the current directory.");
        System.out.println("8: Sort by date - Sort the contents of the current directory by date.");
        System.out.println("9: Exit - Exit the program.");
    }
}
