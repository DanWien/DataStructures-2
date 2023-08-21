

import java.util.NoSuchElementException;

public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    private Node root = null;

    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
    }

    public Node getRoot() {
        if (root == null) {
            throw new NoSuchElementException("empty tree has no root");
        }
        return root;
    }
    
    public Node search(int k) {
        if (root == null)
            return null;
        Node curr = root;
        while (curr != null && k != curr.key) {
            if (k < curr.key)
                curr = curr.left;
            else
                curr = curr.right;
        }
        return curr;
    }
    // Inserting the node as a leaf while dividing into cases for backtrack , and setting its parent pointer accordingly.
    public void insert(Node node) {
        if (node == null)
            throw new IllegalArgumentException("Null is not allowed");
        Node p = null;
        Node curr = root;
        while (curr != null) {
            p = curr;
            if (node.key < curr.key)
                curr = curr.left;
            else curr = curr.right;
        }
        node.parent = p;
        if (p == null) {
            root = node;
            stack.push('e');
        } else if (node.key < p.key) {
            p.left = node;
            stack.push('l');
        } else {
            p.right = node;
            stack.push('r');
        }
        stack.push(p);
        stack.push("insert");
        if (!redoStack.isEmpty()) {
            Object check = redoStack.pop();
            if (!(check instanceof String))
                redoStack.clear();
        }
    }
    // Dividing delete to 4 cases.
    public void delete(Node node) {
        if (root == null)
            throw new NoSuchElementException("Tree is Empty");
        stack.push(node);
        // Deleting a leaf.
        if (node.left == null & node.right == null) { 
            Node p = node.parent;
            if (p == null) // Implies node = root
                root = null;
            if (node.key < p.key)
                p.left = null;
            else
                p.right = null;
            stack.push("c1");
        }
        // Deleting a node that has a left child and no right child.
        if (node.left != null & node.right == null) {
            Node p = node.parent;
            if (p == null) { // Implies node = root
                root = node.left;
                root.parent = null;
            } else if (p.key < node.key) {
                p.right = node.left;
                p.right.parent = p;
            } else {
                p.left = node.left;
                p.left.parent = p;
            }
            stack.push("c2l");
        }
        // Deleting a node that has a right child and no left child.
        if (node.left == null & node.right != null) {
            Node p = node.parent;
            if (p == null) { // Implies node = root
                root = node.right;
                root.parent = null;
            } else if (p.key < node.key) {
                p.right = node.right;
                p.right.parent = p;
            } else {
                p.left = node.right;
                p.left.parent = p;
            }
            stack.push("c2r");
        }
        // Deleting a node with both left and right children.
        if (node.left != null & node.right != null) {
            Node suc = successor(node); //node will have a successor because it has two children so suc can't be min\max
            stack.push(suc.parent);
            //suc.parent = node.parent;
            if (node.parent == null) // Implies node = root
                root = suc;
            else {
                if (suc.key < node.parent.key)
                    node.parent.left = suc;
                else
                    node.parent.right = suc;
            }
            suc.left = node.left;
            node.left.parent = suc;
            if (node.right != suc) {
                if (suc.right != null) { // suc can only have a right child (there's no option that suc will have a left child because its minimum in right subtree of node)
                    suc.right.parent = suc.parent;
                    suc.parent.left = suc.right;
                }
                suc.right = node.right;
                suc.right.parent = suc;
                suc.right.left = null;
            }
            stack.push("c3");
        }
        redoStack.clear();
    }

    public Node minimum() {
        if (root == null)
            throw new NoSuchElementException("Tree is Empty");
        Node min = root;
        while (min.left != null)
            min = min.left;
        return min;
    }

    public Node maximum() {
        if (root == null)
            throw new NoSuchElementException("Tree is Empty");
        Node max = root;
        while (max.right != null)
            max = max.right;
        return max;
    }

    public Node successor(Node node) {
        if (root == null)
            throw new NoSuchElementException("Tree is Empty");
        if (node.right != null) {
            Node curr = node.right;
            while (curr.left != null)
                curr = curr.left;
            return curr;
        }
        Node curr = node;
        Node p = node.parent;
        while (p != null && curr == p.right) {
            curr = p;
            p = p.parent;
        }
        if (p == null)
            throw new NoSuchElementException("Max has no successor");
        return p;
    }

    public Node predecessor(Node node) {
        if (root == null)
            throw new NoSuchElementException("Tree is Empty");
        if (node.left != null) {
            Node curr = node.left;
            while (curr.right != null)
                curr = curr.right;
            return curr;
        }
        Node curr = node;
        Node p = node.parent;
        while (p != null && curr == p.left) {
            curr = p;
            p = p.parent;
        }
        if (p == null)
            throw new NoSuchElementException("Min has no predecessor");
        return p;
    }

    @Override
    // Dividing backtrack to Insert and Delete.
    public void backtrack() {
        if (!stack.isEmpty()) {
            String s = (String) stack.pop();
            if (s.equals("insert")) {
                Node p = (Node) stack.pop();
                redoStack.push(p);
                char c = (char) stack.pop();
                if (c == 'e')
                    root = null;
                else if (c == 'l')
                    p.left = null;
                else p.right = null;
                redoStack.push("i");
            } else {
                // Dividing the delete into the same 4 cases
                if (s.equals("c1")) {
                    Node deleted = (Node) stack.pop();
                    if (root == null)
                        root = deleted;
                    else if (deleted.key < deleted.parent.key)
                        deleted.parent.left = deleted;
                    else
                        deleted.parent.right = deleted;
                } else if (s.equals("c2l")) {
                    Node deleted = (Node) stack.pop();
                    if (deleted.parent == null) {
                        root.parent = deleted;
                        root = deleted;
                    } else if (deleted.left.key < deleted.left.parent.key) {
                        deleted.left.parent.left = deleted;
                    } else {
                        deleted.left.parent.right = deleted;
                    }
                    deleted.left.parent = deleted;
                } else if (s.equals("c2r")) {
                    Node deleted = (Node) stack.pop();
                    if (deleted.parent == null) {
                        root.parent = deleted;
                        root = deleted;
                    }
                    if (deleted.right.key < deleted.right.parent.key) {
                        deleted.right.parent.left = deleted;
                    } else {
                        deleted.right.parent.right = deleted;
                    }
                    deleted.right.parent = deleted;
                } else { //case 3
                    Node sucP = (Node) stack.pop();
                    Node deleted = (Node) stack.pop();
                    if (deleted.parent == null) {
                        root.left = null;
                        if (deleted.right != root) {
                            root.right = sucP.left;
                            sucP.left.parent = root;
                            root.parent = sucP;
                            sucP.left = root;
                        }
                        deleted.right.parent = deleted;
                        deleted.left.parent = deleted;
                        root = deleted;
                    } else if (deleted.right == deleted.parent.left) {
                        deleted.parent.left = deleted;
                        deleted.left.parent = deleted;
                        deleted.right.left = null;
                        deleted.right.parent = deleted;
                    } else if (deleted.right == deleted.parent.right) {
                        deleted.parent.right = deleted;
                        deleted.left.parent = deleted;
                        deleted.right.left = null;
                        deleted.right.parent = deleted;
                    } else {
                        if (deleted.key < deleted.parent.key) {
                            deleted.right.parent = deleted;
                            Node toChange = deleted.parent.left;
                            toChange.left = null;
                            toChange.parent = sucP;
                            toChange.right = sucP.left;
                            if (sucP.left != null)
                                sucP.left.parent = toChange;
                            sucP.left = toChange;
                            deleted.parent.left = deleted;
                            deleted.left.parent = deleted;
                        } else {
                            deleted.right.parent = deleted;
                            Node toChange = deleted.parent.right;
                            toChange.left = null;
                            toChange.parent = sucP;
                            toChange.right = sucP.left;
                            if (sucP.left != null)
                                sucP.left.parent = toChange;
                            sucP.left = toChange;
                            deleted.parent.right = deleted;
                            deleted.left.parent = deleted;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void retrack() {
        if (!redoStack.isEmpty()) {
            String s = (String) redoStack.pop();
            if (s.equals("i")) {
                Node toInsert = (Node) redoStack.pop();
                redoStack.push("rInsert");
                insert(toInsert);
            }
        }
    }

    public void printPreOrder() {
        if (root != null)
            root.printPreOrder();
    }

    @Override
    public void print() {
        printPreOrder();
    }


    public static class Node {
        // These fields are public for grading purposes. By coding conventions and best practice they should be private.
        public Node left;
        public Node right;

        private Node parent;
        private int key;
        private Object value;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public void printPreOrder() {
            System.out.print(key + " ");
            if (left != null)
                left.printPreOrder();
            if (right != null)
                right.printPreOrder();
        }
    }
}
