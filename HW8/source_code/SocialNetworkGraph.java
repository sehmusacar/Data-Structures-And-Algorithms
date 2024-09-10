import java.util.*;

/**
 * Represents a social network graph with people and their friendships.
 */
public class SocialNetworkGraph {
    Map<String, Person> people = new HashMap<>();
    Map<Person, List<Person>> friendships = new HashMap<>();

    /**
     * Adds a person to the social network.
     *
     * @param name    the name of the person
     * @param age     the age of the person
     * @param hobbies the hobbies of the person
     */
    public void addPerson(String name, int age, List<String> hobbies) {
        String lowerCaseName = name.toLowerCase(); // Convert name to lowercase to ensure case-insensitive search
        Person person = new Person(lowerCaseName, age, hobbies); // Create a new person
        people.put(lowerCaseName, person); // Add person to the people map
        friendships.put(person, new ArrayList<>()); // Initialize the person's friendships list
        System.out.println("Person added: " + person); 
    }

    /**
     * Removes a person from the social network.
     *
     * @param name the name of the person to be removed
     */
    public void removePerson(String name) {
        String lowerCaseName = name.toLowerCase(); // Convert name to lowercase to ensure case-insensitive search
        Person person = people.get(lowerCaseName); // Retrieve person from the people map
        if (person != null) {
            friendships.remove(person); // Remove the person's friendships
            for (List<Person> friends : friendships.values()) {
                friends.remove(person); // Remove the person from other people's friendship lists
            }
            people.remove(lowerCaseName); // Remove the person from the people map
            System.out.println("Person removed: " + person); 
        } else {
            System.out.println("Person not found: " + name); 
        }
    }

    /**
     * Adds a friendship between two people in the social network.
     *
     * @param name1 the name of the first person
     * @param name2 the name of the second person
     */
    public void addFriendship(String name1, String name2) {
        Person person1 = people.get(name1.toLowerCase()); // Retrieve first person (case-insensitive)
        Person person2 = people.get(name2.toLowerCase()); // Retrieve second person (case-insensitive)
        if (person1 != null && person2 != null) {
            friendships.get(person1).add(person2); // Add second person to first person's friend list
            friendships.get(person2).add(person1); // Add first person to second person's friend list
            System.out.println("Friendship added between " + person1.name + " and " + person2.name);
        } else {
            System.out.println("One or both persons not found in the network."); 
        }
    }

    /**
     * Removes a friendship between two people in the social network.
     *
     * @param name1 the name of the first person
     * @param name2 the name of the second person
     */
    public void removeFriendship(String name1, String name2) {
        Person person1 = people.get(name1.toLowerCase()); // Retrieve first person (case-insensitive)
        Person person2 = people.get(name2.toLowerCase()); // Retrieve second person (case-insensitive)
        if (person1 != null && person2 != null) {
            friendships.get(person1).remove(person2); // Remove second person from first person's friend list
            friendships.get(person2).remove(person1); // Remove first person from second person's friend list
            System.out.println("Friendship removed between " + person1.name + " and " + person2.name); 
        } else {
            System.out.println("One or both persons not found in the network."); 
        }
    }

    /**
     * Finds the shortest path between two people in the social network using BFS.
     *
     * @param startName the name of the starting person
     * @param endName   the name of the ending person
     * @return a list of people representing the shortest path
     */
    public List<Person> findShortestPath(String startName, String endName) {
        Person start = people.get(startName.toLowerCase()); // Retrieve start person (case-insensitive)
        Person end = people.get(endName.toLowerCase()); // Retrieve end person (case-insensitive)
        if (start == null || end == null) {
            System.out.println("One or both persons not found."); 
            return Collections.emptyList();
        }

        Queue<Person> queue = new LinkedList<>();
        Map<Person, Person> prev = new HashMap<>();
        Set<Person> visited = new HashSet<>();

        queue.add(start); // Initialize BFS with the start person
        visited.add(start); // Mark start person as visited

        while (!queue.isEmpty()) {
            Person current = queue.poll();
            if (current.equals(end)) {
                break; // If end person is found, exit loop
            }
            for (Person neighbor : friendships.get(current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor); // Add neighbor to the queue
                    visited.add(neighbor); // Mark neighbor as visited
                    prev.put(neighbor, current); // Track the path
                }
            }
        }

        List<Person> path = new ArrayList<>();
        for (Person at = end; at != null; at = prev.get(at)) {
            path.add(at); // Reconstruct the path from end to start
        }
        Collections.reverse(path); // Reverse to get the path from start to end

        if (path.size() == 1 && !path.get(0).equals(start)) {
            System.out.println("No connection found."); 
            return Collections.emptyList();
        }

        // Generate the required output format
        StringBuilder sb = new StringBuilder("Shortest path: ");
        for (Person person : path) {
            sb.append(person.name).append(" -> ");
        }
        // Remove the last " -> "
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 4);
        }
        System.out.println(sb.toString()); 

        return path;
    }

    /**
     * Suggests friends for a person based on mutual friends and common hobbies.
     *
     * @param name           the name of the person
     * @param maxSuggestions the maximum number of friends to suggest
     */
    public void suggestFriends(String name, int maxSuggestions) {
        Person person = people.get(name.toLowerCase()); // Retrieve person (case-insensitive)
        if (person == null) {
            System.out.println("Person not found."); // Print error if person not found
            return;
        }

        Map<Person, Double> scores = new HashMap<>();
        Map<Person, Integer> mutualFriendsMap = new HashMap<>();
        Map<Person, Integer> commonHobbiesMap = new HashMap<>();

        for (Person other : people.values()) {
            if (!other.equals(person) && !friendships.get(person).contains(other)) {
                int mutualFriends = 0;
                int commonHobbies = 0;
                double score = 0;
                for (Person mutualFriend : friendships.get(person)) {
                    if (friendships.get(other).contains(mutualFriend)) {
                        mutualFriends++;
                    }
                }
                for (String hobby : person.hobbies) {
                    if (other.hobbies.contains(hobby)) {
                        commonHobbies++;
                    }
                }
                score = mutualFriends * 1.0 + commonHobbies * 0.5;
                scores.put(other, score);
                mutualFriendsMap.put(other, mutualFriends);
                commonHobbiesMap.put(other, commonHobbies);
            }
        }

        List<Map.Entry<Person, Double>> suggestions = new ArrayList<>(scores.entrySet());
        suggestions.sort((a, b) -> Double.compare(b.getValue(), a.getValue())); // Sort suggestions by score

        System.out.println("Suggested friends for " + person.name + ":");
        for (int i = 0; i < Math.min(maxSuggestions, suggestions.size()); i++) {
            Map.Entry<Person, Double> entry = suggestions.get(i);
            Person suggestedPerson = entry.getKey();
            double score = entry.getValue();
            int mutualFriends = mutualFriendsMap.get(suggestedPerson);
            int commonHobbies = commonHobbiesMap.get(suggestedPerson);
            System.out.println(suggestedPerson + " (Score: " + score + ", " + mutualFriends + " mutual friends, " + commonHobbies + " common hobbies)");
        }
    }


    /**
     * Counts and prints the clusters of people in the social network using BFS.
     */
    public void countClusters() {
        Set<Person> visited = new HashSet<>();
        int clusterCount = 0;

        for (Person person : people.values()) {
            if (!visited.contains(person)) {
                clusterCount++; // Increment cluster count
                System.out.println("Cluster " + clusterCount + ":");
                bfs(person, visited); // Perform BFS to find all people in the cluster
            }
        }
        System.out.println("Number of clusters found: " + clusterCount); 
    }

    /**
     * Performs a BFS traversal from a starting person and prints the cluster.
     *
     * @param start   the starting person
     * @param visited the set of visited persons
     */
    private void bfs(Person start, Set<Person> visited) {
        Queue<Person> queue = new LinkedList<>();
        queue.add(start); // Initialize BFS with the start person
        visited.add(start); // Mark start person as visited

        while (!queue.isEmpty()) {
            Person current = queue.poll();
            System.out.println(current.name); 
            for (Person neighbor : friendships.get(current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor); // Add neighbor to the queue
                    visited.add(neighbor); // Mark neighbor as visited
                }
            }
        }
    }
}
