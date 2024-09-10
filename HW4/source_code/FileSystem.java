import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a file system managing directories and files.
 * Allows for operations such as changing directories, listing contents,
 * creating, deleting, moving files and directories, and searching within the file system.
 */
public class FileSystem {
    private Directory root;
    private Directory currentDirectory;

    /**
     * Constructor for the FileSystem. Initializes the file system with a root directory.
     */
    public FileSystem() {
        this.root = new Directory("root", new Timestamp(System.currentTimeMillis()), null);
        this.currentDirectory = root;  // Start at the root
    }

    /**
     * Returns the current directory within the file system.
     * @return The current directory.
     */
    public Directory getCurrentDirectory() {
        return currentDirectory;
    }

    /**
     * Changes the current directory to a new directory specified by the path.
     * Supports absolute and relative paths.
     * @param path The path to the new directory.
     * @return true if the directory change is successful, false otherwise.
     */
    public boolean changeDirectory(String path) {
        if (path.startsWith("/")) {
            Directory newDir = findDirectory(root, path.substring(1).split("/"), 0);
            if (newDir != null) {
                currentDirectory = newDir;
                System.out.println("Directory changed to: " + newDir.getPath());
                return true;
            }
        } else if (path.equals("..")) {
            if (currentDirectory.getParent() != null) {
                currentDirectory = (Directory) currentDirectory.getParent();
                System.out.println("Directory changed to: " + currentDirectory.getPath());
                return true;
            }
        } else {
            Directory newDir = findDirectory(currentDirectory, path.split("/"), 0);
            if (newDir != null) {
                currentDirectory = newDir;
                System.out.println("Directory changed to: " + newDir.getPath());
                return true;
            }
        }
        System.out.println("Directory not found.");
        return false;
    }
    
    /**
     * Finds a directory based on the path segments provided starting from a given directory.
     * @param current The starting directory.
     * @param pathSegments An array of strings representing each segment of the path.
     * @param index The current index in the path segments.
     * @return The directory if found, null otherwise.
     */
    private Directory findDirectory(Directory current, String[] pathSegments, int index) {
        if (index >= pathSegments.length) return current;  
        if (current == null) return null;  
        for (FileSystemElement element : current.getChildren()) {
            if (element instanceof Directory && element.getName().equals(pathSegments[index])) {
                return findDirectory((Directory) element, pathSegments, index + 1);
            }
        }
        return null;  
    }

    /**
     * Lists all files and directories in the current directory.
     */
    public void listContents() {
        List<FileSystemElement> children = currentDirectory.getChildren();
        System.out.println("Listing contents of " + getCurrentPath() + ":");
        if (children.isEmpty()) {
            System.out.println("No files or directories found in the current directory.");
        } else {
            for (FileSystemElement element : children) {
                if (element instanceof Directory) {
                    System.out.println(" * " + element.getName() + "/");
                } else {
                    System.out.println(" " + element.getName());
                }
            }
        }
    }

    /**
     * Creates a file or directory in the current directory based on the type specified.
     * @param type The type of the file system element to create ('f' for file, 'd' for directory).
     * @param name The name of the file or directory to create.
     */
    public void createFileOrDirectory(String type, String name) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (type.equalsIgnoreCase("d")) {
            Directory newDir = new Directory(name, now, currentDirectory);
            currentDirectory.addChild(newDir);
        } else if (type.equalsIgnoreCase("f")) {
            File newFile = new File(name, now, currentDirectory);
            currentDirectory.addChild(newFile);
        } else {
            System.out.println("Invalid type specified. Use 'f' for file and 'd' for directory.");
        }
    }

    /**
     * Recursively deletes a file system element.
     * @param element The element to delete.
     */
    public void deleteRecursively(FileSystemElement element) {
        if (element instanceof Directory) {
            Directory directory = (Directory) element;
            for (FileSystemElement child : new ArrayList<>(directory.getChildren())) {
                deleteRecursively(child);
            }
        }
        if (element.getParent() != null) {
            ((Directory) element.getParent()).removeChild(element);
        }
    }

    /**
     * Deletes a file or directory by name in the current directory.
     * @param name The name of the file or directory to delete.
     */
    public void deleteFileOrDirectory(String name) {
        if (currentDirectory == root && name.equals("root")) {
            System.out.println("Cannot delete root directory.");
            return;
        }
        FileSystemElement toDelete = null;
        for (FileSystemElement element : currentDirectory.getChildren()) {
            if (element.getName().equals(name)) {
                toDelete = element;
                break;
            }
        }
        if (toDelete != null) {
            deleteRecursively(toDelete);
            System.out.println(name + " has been deleted.");
        } else {
            System.out.println("File or directory not found.");
        }
    }
    
    /**
     * Moves a file or directory to a new path.
     * @param name The name of the file or directory to move.
     * @param newPath The new path for the file or directory.
     */
    public void moveFileOrDirectory(String name, String newPath) {
        // Cannot move the root directory, it must remain fixed.
        if (name.equals("root")) {
            System.out.println("Cannot move root directory.");
            return;
        }
        // Find the new directory by splitting the path and searching from the root.
        Directory newDirectory = findDirectory(root, newPath.split("/"), 0);
        if (newDirectory == null) {
            System.out.println("Destination directory not found.");
            return;
        }
        // Locate the file or directory to move within the current directory's children.
        FileSystemElement toMove = null;
        for (FileSystemElement element : currentDirectory.getChildren()) {
            if (element.getName().equals(name)) {
                toMove = element;
                break;
            }
        }
        
        if (toMove != null) {
            // If the element to move is a directory, check it is not being moved into its own subtree.
            if (toMove instanceof Directory && newDirectory.getPath().startsWith(toMove.getPath() + "/")) {
                System.out.println("Invalid move operation: Cannot move a directory into one of its subdirectories.");
                return;
            }
    
            // Perform the move by removing the element from the current directory and adding it to the new directory.
            currentDirectory.removeChild(toMove);
            newDirectory.addChild(toMove);
            System.out.println(name + " moved to " + newPath);
        } else {
            System.out.println("File or directory to move not found.");
        }
    }  

    /**
     * Searches for a file or directory by name starting from the specified directory.
     * @param name The name to search for.
     * @param startDir The starting directory for the search.
     */
    public void search(String name) {
        System.out.println("Searching from root...");
        if (!recursiveSearch(name, root)) {
            System.out.println("No file or directory found by the name '" + name + "'.");
        }
    }
    
    private boolean recursiveSearch(String name, Directory dir) {
        if (dir.getName().equals(name)) {
            System.out.println("Found: " + getPath(dir));
            return true;
        }
        for (FileSystemElement element : dir.getChildren()) {
            if (element.getName().equals(name)) {
                System.out.println("Found: " + getPath(element));
                return true;
            }
            if (element instanceof Directory) {
                if (recursiveSearch(name, (Directory) element)) {
                    return true;
                }
            }
        }
        return false;
    }       
    
     /**
     * Retrieves the path of a file system element.
     * @param element The element to get the path for.
     * @return The path of the element.
     */
    private String getPath(FileSystemElement element) {
        // Helper method to get full path from the root to the current element
        if (element.getParent() == null) return "/" + element.getName();
        return getPath(element.getParent()) + "/" + element.getName();
    }

    /**
     * Prints the directory tree starting from a given directory with a specified indentation.
     * @param dir The starting directory.
     * @param indent The indentation to use for each level of the tree.
     */
    public void printDirectoryTree(Directory dir, String indent) {
        if (dir == currentDirectory) {
            System.out.println(indent + "* " + dir.getName() + "/ (Current Directory)");
        } else {
            System.out.println(indent + "* " + dir.getName() + "/");
        }
    
        for (FileSystemElement element : dir.getChildren()) {
            if (element instanceof Directory) {
                printDirectoryTree((Directory) element, indent + "    ");
            } else {
                System.out.println(indent + "   " + element.getName());
            }
        }
    }
    
    /**
     * Prints the directory tree from the root to the current directory.
     */
    public void printCurrentDirectoryTree() {
        System.out.println("Path to current directory from root:");
        printDirectoryTree(root, "");
    }

    /**
     * Sorts the contents of a directory by the date they were created.
     * @param dir The directory whose contents will be sorted.
     */
    public void sortDirectoryByDate(Directory dir) {
        if (dir.getChildren().isEmpty()) {
            System.out.println("No contents to sort in " + dir.getPath());
            return;
        }
    
        Collections.sort(dir.getChildren(), new Comparator<FileSystemElement>() {
            @Override
            public int compare(FileSystemElement o1, FileSystemElement o2) {
                return o1.getDateCreated().compareTo(o2.getDateCreated());
            }
        });
    
        System.out.println("Sorted contents of " + dir.getPath() + " by date created:");
        for (FileSystemElement element : dir.getChildren()) {
            String type = element instanceof Directory ? "Directory" : "File";
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(element.getDateCreated());
            if (type.equals("Directory")) {
                System.out.println(" * " + element.getName() + "/ (" + timestamp + ")");
            } else {
                System.out.println(" " + element.getName() + " (" + timestamp + ")");
            }
        }
    }    
    
    /**
     * Returns the path of the current directory.
     * @return The path of the current directory.
     */
    public String getCurrentPath() {
        return currentDirectory.getPath();
    }
}
