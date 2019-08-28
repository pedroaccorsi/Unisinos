/**
 * BinarySearchTree
 * 
 * Trabalho de Estruturas Avançadas de Dados - Bacharelado em Ciência da
 * Computação
 * 
 * Fábio Machado Krein, Gabriel Pereira da Conceição, Pedro Henrique Accorsi e
 * Rafaela Cristina Kreusch
 */

public class BinarySearchTree<K extends Comparable<K>, V> implements BinarySearchTreeADT<K, V> {

    protected Node root;

    protected class Node {
        private K key;
        private V value;
        private Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node next(K other) {
            return other.compareTo(key) < 0 ? left : right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public String toString() {
            return "" + key;
        }
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public V search(K key) {
        return search(root, key);

    }

    private V search(Node node, K key) {
        if (node == null) {
            return null;
        } else if (key.compareTo(node.key) == 0) {
            return node.value;
        }
        return search(node.next(key), key);
    }

    @Override
    public void insert(K key, V value) {
        root = insert(root, key, value);
    }

    private Node insert(Node node, K key, V value) {
        if (node == null) {
            return new Node(key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insert(node.right, key, value);
        } else if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key, value);
        }
        return node;
    }

    @Override
    public String toString() {
        return root == null ? "[empty]" : printTree(new StringBuffer());
    }

    private String printTree(StringBuffer sb) {
        if (root.right != null) {
            printTree(root.right, true, sb, "");
        }
        sb.append(root + "\n");
        if (root.left != null) {
            printTree(root.left, false, sb, "");
        }

        return sb.toString();
    }

    private void printTree(Node node, boolean isRight, StringBuffer sb, String indent) {
        if (node.right != null) {
            printTree(node.right, true, sb, indent + (isRight ? "        " : " |      "));
        }
        sb.append(indent + (isRight ? " /" : " \\") + "----- " + node + "\n");
        if (node.left != null) {
            printTree(node.left, false, sb, indent + (isRight ? " |      " : "        "));
        }
    }

    @Override
    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node node) {
        if (node != null) {
            System.out.print(node + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    @Override
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node + " ");
            inOrder(node.right);
        }
    }

    @Override
    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node + " ");
        }
    }

    @Override
    public void levelOrder() {
        levelOrder(root);
    }

    private void levelOrder(Node node) {
        return;
    }

    @Override
    public int countNodes() {
        // Conta nós abaixo da raiz, considerando raiz
        return countNodes(root, 1);
    }

    @Override
    public int countInternalNodes() {
        // Conta nós abaixo da raiz
        return countNodes(root, 0) - countLeaves(root);
    }

    private int countNodes(Node node, int qtdNos) {
        if (node == null) {
            return 0;
        } else {
            if (node.right != null)
                // Soma a quantidade de nós na direita do nó atual
                qtdNos = countNodes(node.right, ++qtdNos);
            if (node.left != null)
                // Soma a quantidade de nós na esquerda do nó atual
                qtdNos = countNodes(node.left, ++qtdNos);
        }
        return qtdNos;
    }

    @Override
    public int countLeaves() {
        return countLeaves(root);
    }

    private int countLeaves(Node node) {
        int qtdFolhas = 0;
        if (node == null) {
            return 0;
        } else {
            // Se encontrou uma folha
            if (node.isLeaf())
                return ++qtdFolhas;
            // Se possui nó na esquerda
            if (node.left != null)
                // Soma quatidade de folhas na esquerda do nó atual
                qtdFolhas += countLeaves(node.left);
            // Se possui nó na direita
            if (node.right != null)
                // Soma quatidade de folhas na direita do nó atual
                qtdFolhas += countLeaves(node.right);
            return qtdFolhas;
        }
    }

    @Override
    public int degree(K key) {
        return degree(root, key);
    }

    private int degree(Node node, K key) {
        if (node == null){
            return 0;
        } else {
            
            return 0;
        }
    }

    @Override
    public int degreeTree() {
        return degreeTree(root);
    }

    private int degreeTree(Node node) {
        return 0;
    }

    @Override
    public int height(K key) {
        return height(root, key);
    }

    private int height(Node node, K key) {
        if (node == null) {
            return 0;
        } else {


        }

        return 0;
    }

    @Override
    public int heightTree() {
        return heightTree(root);
    }

    private int heightTree(Node node) {
        return 0;
    }

    @Override
    public int depth(K key) {
        return depth(root, key);
    }

    private int depth(Node node, K key) {
        return 0;
    }

    @Override
    public String ancestors(K key) {
        return ancestors(root, key);
    }

    private String ancestors(Node node, K key) {
        String a = "";
        String b = "";
        if (node == null)
            return null;
        if (node.key == key)
            return node.key.toString() + " ";
        a = ancestors(node.left, key);
        if (a == null){
            b = ancestors(node.right, key);
            if (b==null)
                return null;
            return b.concat(node.key.toString() + " ");
        }
        return a.concat(node.key.toString() + " ");
    }

    @Override
    public String descendents(K key) {
        return descendents(root, key);
    }

    public String descendents(Node node, K key) {
        if (node != null) {
            if (node.key == key)
            // Retorna node atual
            // Se tem subarvore na esquerda retorna seus filhos
            // Se tem subarvore na direita retorna seus filhos
                return (node + " ") + (node.left!=null?descendents(node.left, node.left.key):"") + (node.right!=null?descendents(node.right, node.right.key).toString():"");
        }
        if (node.left != null){
            return descendents(node.left, key);
        }
        if (node.right != null){
            return descendents(node.right, key);
        }
        return null;
    }

    @Override
    public boolean delete(K key) {
        return false;
    }
}