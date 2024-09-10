import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a directory in a file system.
 * This class extends {@link FileSystemElement} to handle directory-specific behaviors,
 * particularly managing child elements which can be either files or subdirectories.
 */
public class Directory extends FileSystemElement {
    private List<FileSystemElement> children;

    /**
     * Constructs a new Directory with the specified name, creation timestamp, and parent directory.
     *
     * @param name The name of the directory.
     * @param dateCreated The timestamp indicating when the directory was created.
     * @param parent The parent directory of this directory. Can be null if this is the root directory.
     */
    public Directory(String name, Timestamp dateCreated, FileSystemElement parent) {
        super(name, dateCreated, parent);
        this.children = new LinkedList<>();
    }

    /**
     * Adds a child element to this directory.
     * The child's parent reference is set to this directory.
     *
     * @param child The FileSystemElement to be added as a child. Can be either a File or another Directory.
     */
    public void addChild(FileSystemElement child) {
        children.add(child);
        child.setParent(this);
    }

    /**
     * Removes a child element from this directory.
     * The child's parent reference is set to null.
     *
     * @param child The FileSystemElement to be removed. This element must be a current child of this directory.
     */
    public void removeChild(FileSystemElement child) {
        children.remove(child);
        child.setParent(null);
    }

    /**
     * Returns a list of all children contained in this directory.
     * The list includes both files and directories.
     *
     * @return A list of FileSystemElement representing all the children of this directory.
     */
    public List<FileSystemElement> getChildren() {
        return children;
    }
}
