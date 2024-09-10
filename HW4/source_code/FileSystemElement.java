import java.sql.Timestamp;

/**
 * Represents an abstract element within a file system, providing common properties and methods.
 * This class serves as a base for more specific file system elements like files and directories.
 */
public abstract class FileSystemElement {
    protected String name;
    protected Timestamp dateCreated;
    protected FileSystemElement parent;

    /**
     * Constructs a new file system element with a name, creation date, and parent element.
     * 
     * @param name The name of the file system element.
     * @param dateCreated The timestamp when the element was created.
     * @param parent The parent of this element, or null if it is the root element.
     */
    public FileSystemElement(String name, Timestamp dateCreated, FileSystemElement parent) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.parent = parent;
    }

    /**
     * Returns the name of the file system element.
     *
     * @return The name of the element.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the file system element.
     *
     * @param name The new name of the element.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the timestamp when the file system element was created.
     *
     * @return The creation timestamp of the element.
     */
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the creation date of the file system element.
     *
     * @param dateCreated The new creation timestamp of the element.
     */
    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Returns the parent of this file system element.
     *
     * @return The parent element, or null if this is the root element.
     */
    public FileSystemElement getParent() {
        return parent;
    }

    /**
     * Sets the parent of this file system element.
     *
     * @param parent The new parent element, or null if this element is to become a root element.
     */
    public void setParent(FileSystemElement parent) {
        this.parent = parent;
    }

    /**
     * Retrieves the full path of this element from the root of the file system.
     * 
     * @return The full path from the root to this element, using "/" as a directory separator.
     */
    public String getPath() {
        if (this.parent == null) {
            return "/" + this.name;  // Root directory
        } else {
            return this.parent.getPath() + "/" + this.name;
        }
    }
}
