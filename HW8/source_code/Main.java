import java.util.*;

/**
 * Main class to demonstrate the Social Network Analysis System.
 */
public class Main {
    public static void main(String[] args) {
        SocialNetworkGraph network = new SocialNetworkGraph();
        Scanner scanner = new Scanner(System.in);

        // Adding some people for demonstration
        network.addPerson("John Doe", 25, Arrays.asList("reading", "hiking", "cooking"));
        network.addPerson("Jane Smith", 22, Arrays.asList("swimming", "cooking"));
        network.addPerson("Alice Johnson", 27, Arrays.asList("hiking", "painting"));
        network.addPerson("Bob Brown", 30, Arrays.asList("reading", "swimming"));
        network.addPerson("Emily Davis", 28, Arrays.asList("running", "swimming"));
        network.addPerson("Frank Wilson", 26, Arrays.asList("reading", "hiking"));

        // Adding friendships for demonstration
        network.addFriendship("John Doe", "Jane Smith");
        network.addFriendship("John Doe", "Alice Johnson");
        network.addFriendship("Jane Smith", "Bob Brown");
        network.addFriendship("Emily Davis", "Frank Wilson");

        while (true) {
            System.out.println("===== Social Network Analysis Menu =====");
            System.out.println("1. Add person");
            System.out.println("2. Remove person");
            System.out.println("3. Add friendship");
            System.out.println("4. Remove friendship");
            System.out.println("5. Find shortest path");
            System.out.println("6. Suggest friends");
            System.out.println("7. Count clusters");
            System.out.println("8. Exit");
            System.out.print("Please select an option: ");

            int choice = -1;

            // Input validation for choice
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 8.");
                scanner.nextLine(); // Clear the invalid input
                continue;
            }

            if (choice == 8) {
                break;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter age: ");
                    int age = 0;
                    try {
                        age = scanner.nextInt();
                        scanner.nextLine(); // Clear the newline
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid age input. Please enter a valid number.");
                        scanner.nextLine(); // Clear the invalid input
                        continue;
                    }
                    System.out.print("Enter hobbies (comma-separated): ");
                    String hobbiesInput = scanner.nextLine();
                    List<String> hobbies = Arrays.asList(hobbiesInput.split(","));
                    network.addPerson(name, age, hobbies);
                    break;
                case 2:
                    System.out.print("Enter name: ");
                    String removeName = scanner.nextLine().toLowerCase(); // Ismi küçük harfe çevir
                    network.removePerson(removeName);
                    break;
                case 3:
                    System.out.print("Enter first person's name: ");
                    String name1 = scanner.nextLine();
                    System.out.print("Enter second person's name: ");
                    String name2 = scanner.nextLine();
                    network.addFriendship(name1, name2);
                    break;
                case 4:
                    System.out.print("Enter first person's name: ");
                    String removeName1 = scanner.nextLine();
                    System.out.print("Enter second person's name: ");
                    String removeName2 = scanner.nextLine();
                    network.removeFriendship(removeName1, removeName2);
                    break;
                case 5:
                    System.out.print("Enter start person's name: ");
                    String startName = scanner.nextLine();
                    System.out.print("Enter end person's name: ");
                    String endName = scanner.nextLine();
                    network.findShortestPath(startName, endName);
                    break;
                case 6:
                    System.out.print("Enter person's name: ");
                    String suggestName = scanner.nextLine();
                    System.out.print("Enter maximum number of friends to suggest: ");
                    int maxSuggestions = 0;
                    try {
                        maxSuggestions = scanner.nextInt();
                        scanner.nextLine(); // Clear the newline
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid number input. Please enter a valid number.");
                        scanner.nextLine(); // Clear the invalid input
                        continue;
                    }
                    network.suggestFriends(suggestName, maxSuggestions);
                    break;
                case 7:
                    network.countClusters();
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}
