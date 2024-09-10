public class AVLTree {
    private Node root;

    private class Node {
        Stock stock;
        Node left, right;
        int height;

        Node(Stock stock) {
            this.stock = stock;
            this.height = 1;
        }
    }

    public void insertOrUpdate(Stock stock) {
        root = insertOrUpdate(root, stock);
    }

    private Node insertOrUpdate(Node node, Stock stock) {
        if (node == null) {
            return new Node(stock);
        }
        int cmp = stock.getSymbol().compareTo(node.stock.getSymbol());
        if (cmp < 0) {
            node.left = insertOrUpdate(node.left, stock);
        } else if (cmp > 0) {
            node.right = insertOrUpdate(node.right, stock);
        } else {
            node.stock = stock; // Update the existing stock
            return node;
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);
        // Left Left Case
        if (balance > 1 && stock.getSymbol().compareTo(node.left.stock.getSymbol()) < 0) {
            return rightRotate(node);
        }
        // Right Right Case
        if (balance < -1 && stock.getSymbol().compareTo(node.right.stock.getSymbol()) > 0) {
            return leftRotate(node);
        }
        // Left Right Case
        if (balance > 1 && stock.getSymbol().compareTo(node.left.stock.getSymbol()) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // Right Left Case
        if (balance < -1 && stock.getSymbol().compareTo(node.right.stock.getSymbol()) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    public void delete(String symbol) {
        root = delete(root, symbol);
    }

    private Node delete(Node root, String symbol) {
        if (root == null) {
            return root;
        }

        int cmp = symbol.compareTo(root.stock.getSymbol());
        if (cmp < 0) {
            root.left = delete(root.left, symbol);
        } else if (cmp > 0) {
            root.right = delete(root.right, symbol);
        } else {
            if ((root.left == null) || (root.right == null)) {
                Node temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
                }

                if (temp == null) {
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node temp = getMinValueNode(root.right);
                root.stock = temp.stock;
                root.right = delete(root.right, temp.stock.getSymbol());
            }
        }

        if (root == null) {
            return root;
        }

        root.height = Math.max(height(root.left), height(root.right)) + 1;

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    private Node getMinValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public Stock search(String symbol) {
        return search(root, symbol);
    }

    private Stock search(Node node, String symbol) {
        if (node == null) {
            return null;
        }

        int cmp = symbol.compareTo(node.stock.getSymbol());
        if (cmp < 0) {
            return search(node.left, symbol);
        } else if (cmp > 0) {
            return search(node.right, symbol);
        } else {
            return node.stock;
        }
    }

    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private int getBalance(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }
}
