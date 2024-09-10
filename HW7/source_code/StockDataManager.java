import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockDataManager {
    private List<Integer> addTimes;
    private List<Integer> removeTimes;
    private List<Integer> searchTimes;
    private List<Integer> updateTimes;
    private AVLTree avlTree;

    public StockDataManager() {
        addTimes = new ArrayList<>();
        removeTimes = new ArrayList<>();
        searchTimes = new ArrayList<>();
        updateTimes = new ArrayList<>();
        avlTree = new AVLTree();
    }

    public void loadDataFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLine(String line) {
        String[] parts = line.split(" ");
        String operation = parts[0];

        switch (operation) {
            case "ADD":
                int addTime = performAdd(parts);
                addTimes.add(addTime);
                break;
            case "REMOVE":
                int removeTime = performRemove(parts[1]);
                removeTimes.add(removeTime);
                break;
            case "SEARCH":
                int searchTime = performSearch(parts[1]);
                searchTimes.add(searchTime);
                break;
            case "UPDATE":
                int updateTime = performUpdate(parts);
                updateTimes.add(updateTime);
                break;
        }
    }

    public void addOrUpdateStock(String symbol, double price, long volume, long marketCap) {
        // Implementation for adding or updating stock in AVL tree
        // Start timing
        long startTime = System.nanoTime();
        // Perform ADD or UPDATE operation using AVL tree
        avlTree.insertOrUpdate(new Stock(symbol, price, volume, marketCap));
        // End timing
        long endTime = System.nanoTime();
        addTimes.add((int) (endTime - startTime));
    }

    public void removeStock(String symbol) {
        // Implementation for removing stock from AVL tree
        // Start timing
        long startTime = System.nanoTime();
        // Perform REMOVE operation using AVL tree
        avlTree.delete(symbol);
        // End timing
        long endTime = System.nanoTime();
        removeTimes.add((int) (endTime - startTime));
    }

    public Stock searchStock(String symbol) {
        // Implementation for searching stock in AVL tree
        // Start timing
        long startTime = System.nanoTime();
        // Perform SEARCH operation using AVL tree
        Stock stock = avlTree.search(symbol);
        // End timing
        long endTime = System.nanoTime();
        searchTimes.add((int) (endTime - startTime));
        return stock;
    }

    public void updateStock(String symbol, double newPrice, long newVolume, long newMarketCap) {
        // Implementation for updating stock in AVL tree
        // Start timing
        long startTime = System.nanoTime();
        // Perform UPDATE operation using AVL tree
        avlTree.insertOrUpdate(new Stock(symbol, newPrice, newVolume, newMarketCap));
        // End timing
        long endTime = System.nanoTime();
        updateTimes.add((int) (endTime - startTime));
    }

    private int performAdd(String[] parts) {
        long startTime = System.nanoTime();
        avlTree.insertOrUpdate(new Stock(parts[1], Double.parseDouble(parts[2]), Long.parseLong(parts[3]), Long.parseLong(parts[4])));
        long endTime = System.nanoTime();
        return (int) (endTime - startTime);
    }

    private int performRemove(String symbol) {
        long startTime = System.nanoTime();
        avlTree.delete(symbol);
        long endTime = System.nanoTime();
        return (int) (endTime - startTime);
    }

    private int performSearch(String symbol) {
        long startTime = System.nanoTime();
        avlTree.search(symbol);
        long endTime = System.nanoTime();
        return (int) (endTime - startTime);
    }

    private int performUpdate(String[] parts) {
        long startTime = System.nanoTime();
        avlTree.insertOrUpdate(new Stock(parts[1], Double.parseDouble(parts[3]), Long.parseLong(parts[4]), Long.parseLong(parts[5])));
        long endTime = System.nanoTime();
        return (int) (endTime - startTime);
    }

    public List<Integer> getAddTimes() {
        return addTimes;
    }

    public List<Integer> getRemoveTimes() {
        return removeTimes;
    }

    public List<Integer> getSearchTimes() {
        return searchTimes;
    }

    public List<Integer> getUpdateTimes() {
        return updateTimes;
    }
}
