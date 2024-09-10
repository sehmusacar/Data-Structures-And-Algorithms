import java.sql.Timestamp;

/**
 * Represents a file in a file system.
 * This class extends {@link FileSystemElement} to specifically handle file-related attributes and behaviors.
 */

public class File extends FileSystemElement {

    /**
     * Constructs a new File with the specified name, creation timestamp, and parent directory.
     * 
     * @param name The name of the file.
     * @param dateCreated The timestamp indicating when the file was created.
     * @param parent The parent directory of this file. Can be null if the file has no parent.
     */
    public File(String name, Timestamp dateCreated, FileSystemElement parent) {
        super(name, dateCreated, parent);
    }
}
