import java.util.Date;
import java.util.List;

/**
 * Represents a person in the social network.
 */
public class Person {
    String name;
    int age;
    List<String> hobbies;
    Date timestamp;

    /**
     * Constructs a new Person with the given attributes.
     *
     * @param name     the name of the person
     * @param age      the age of the person
     * @param hobbies  the hobbies of the person
     */
    public Person(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.hobbies = hobbies;
        this.timestamp = new Date();
    }

    @Override
    public String toString() {
        return name + " (Age: " + age + ", Hobbies: " + hobbies + ", Timestamp: " + timestamp + ")";
    }
}
