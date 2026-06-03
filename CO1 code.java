class Node {
    int key, height;
    Node left, right;

    Node(int key) {
        this.key = key;
        height = 1;
    }
}

class AVLTree {

    int height(Node N) {
        return (N == null) ? 0 : N.height;
    }

    int getBalance(Node N) {
        return (N == null) ? 0 : height(N.left) - height(N.right);
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    Node insert(Node node, int key) {

        if (node == null)
            return new Node(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // LL Case
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // RR Case
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // LR Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    Node minValueNode(Node node) {
        Node current = node;

        while (current.left != null)
            current = current.left;

        return current;
    }

    Node delete(Node root, int key) {

        if (root == null)
            return root;

        if (key < root.key)
            root.left = delete(root.left, key);

        else if (key > root.key)
            root.right = delete(root.right, key);

        else {

            if ((root.left == null) || (root.right == null)) {
                Node temp = (root.left != null) ? root.left : root.right;

                if (temp == null) {
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = delete(root.right, temp.key);
            }
        }

        if (root == null)
            return root;

        root.height = Math.max(height(root.left), height(root.right)) + 1;

        int balance = getBalance(root);

        // LL
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // LR
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RR
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // RL
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    boolean search(Node root, int key) {
        if (root == null)
            return false;

        if (root.key == key)
            return true;

        if (key < root.key)
            return search(root.left, key);

        return search(root.right, key);
    }

    void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.key + " ");
            inorder(root.right);
        }
    }
}

public class FileIndexPro {
    public static void main(String[] args) {

        AVLTree tree = new AVLTree();
        Node root = null;

        int[] fileIDs = {10, 20, 30, 40, 50};

        System.out.println("File IDs Inserted:");
        for (int id : fileIDs) {
            root = tree.insert(root, id);
            System.out.print(id + " ");
        }

        System.out.println("\n\nAVL Tree (Inorder Traversal):");
        tree.inorder(root);

        int searchID = 30;
        System.out.println("\n\nSearching File ID: " + searchID);

        if (tree.search(root, searchID))
            System.out.println("Result: File Found");
        else
            System.out.println("Result: File Not Found");

        int deleteID = 50;
        System.out.println("\nDeleting File ID: " + deleteID);

        root = tree.delete(root, deleteID);

        System.out.println("AVL Tree After Deletion:");
        tree.inorder(root);

        System.out.println("\n\nTime Complexity:");
        System.out.println("Search : O(log n)");
        System.out.println("Insert : O(log n)");
        System.out.println("Delete : O(log n)");
    }
}