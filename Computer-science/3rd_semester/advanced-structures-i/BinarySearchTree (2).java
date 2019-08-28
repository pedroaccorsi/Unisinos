/**
 * BinarySearchTree
 * 
 * Trabalho de Estruturas Avan√ßadas de Dados - Bacharelado em Ci√™ncia da
 * Computa√ß√£o
 * 
 * F√°bio Machado Krein, Gabriel Pereira da Concei√ß√£o, Pedro Henrique Accorsi
 * e Rafaela Cristina Kreusch
 */

public class BinarySearchTree < K extends Comparable < K > , V > implements BinarySearchTreeADT < K, V > {

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
        // Conta n√≥s abaixo da raiz, considerando raiz
        return countNodes(root, 1);
    }

    private int countNodes(Node node, int qtdNos) {
        if (node == null) {
            return 0;
        } else {
            if (node.right != null)
                // Soma a quantidade de n√≥s na direita do n√≥ atual
                qtdNos = countNodes(node.right, qtdNos + 1);
            if (node.left != null)
                // Soma a quantidade de n√≥s na esquerda do n√≥ atual
                qtdNos = countNodes(node.left, qtdNos + 1);
        }
        return qtdNos;
    }

/*	ABAIXO, SEGUE OS M…TODOS DO TRABALHO.
	ABAIXO, SEGUE OS M…TODOS DO TRABALHO.
	ABAIXO, SEGUE OS M…TODOS DO TRABALHO.
	ABAIXO, SEGUE OS M…TODOS DO TRABALHO.	*/

    @Override
    public int countInternalNodes() {
        // Conta n√≥s abaixo da raiz
        return countNodes(root, 0) - countLeaves(root);
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
                return qtdFolhas + 1;
            // Se possui n√≥ na esquerda
            if (node.left != null)
                // Soma quatidade de folhas na esquerda do n√≥ atual
                qtdFolhas += countLeaves(node.left);
            // Se possui n√≥ na direita
            if (node.right != null)
                // Soma quatidade de folhas na direita do n√≥ atual
                qtdFolhas += countLeaves(node.right);
            return qtdFolhas;
        }
    }

    /*
     * Objetivo: retornar o grau de um nÛ. Par‚metros: key: chave que se deseja
     * verificar o grau. Retorno: grau do nÛ que È representado pela chave. Caso n„o
     * encontrado a chave, retornar -1.
     */

    @Override
    public int degree(K key) {
        return degree(root, key);
    }

    private int degree(Node node, K key) {
        if (node == null) {
            return -1;
        } else {

            return 0;
        }
    }

    /*
     * degreeTree Objetivo: retornar o grau da ·rvore. Par‚metros: nenhum. Retorno:
     * grau da ·rvore. Caso a ·rvore esteja vazia, retornar -1.
     */

    @Override
    public int degreeTree() {
        return degreeTree(root);
    }

    private int degreeTree(Node node) {
        return 0;
    }

    @Override
    public int height(K key) {
        if (key == root.key)
            return heightTree(root) - 1;

        return heightTree(get_reference_of_key(root, key)) - 1;
    }

    @Override
    public int heightTree() {
        return heightTree(root);
    }

    private int heightTree(Node node) {

        int i = 0;

        if (node == null)
            return 0;
        // System.out.print(node.key.toString() + " "); breakpoint pra que 
        if (node.isLeaf())
            return 1;

        if (node.right != null) {
            i = heightTree(node.right) + 1;
        }

        if (node.left != null) {
            i = heightTree(node.left) + 1;
        }

        return i;
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
        if (a == null) {
            b = ancestors(node.right, key);
            if (b == null)
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

                return (node + " ") + (node.left != null ? descendents(node.left, node.left.key) : "") +
                    (node.right != null ? descendents(node.right, node.right.key).toString() : "");
        }

        if (node.left != null) {
            return descendents(node.left, key);
        }
        if (node.right != null) {
            return descendents(node.right, key);
        }
        return null;
    }

    @Override
    public boolean delete(K key) {
        return false;
    }

/*	ABAIXO, SEGUE M…TODO EXTRA CONSIDERADO RELEVANTE
	ABAIXO, SEGUE M…TODO EXTRA CONSIDERADO RELEVANTE
	ABAIXO, SEGUE M…TODO EXTRA CONSIDERADO RELEVANTE
	ABAIXO, SEGUE M…TODO EXTRA CONSIDERADO RELEVANTE	*/

    private Node get_reference_by_key(Node io_node, K iv_key) {

        if (io_node == null) {
            return null;
        } else if (iv_key.compareTo(io_node.key) == 0) {
            return io_node;
        }
        return get_reference_by_key(io_node.next(iv_key), iv_key);

    }

/*	ABAIXO, SEGUE M…TODO N√O IMPLEMENTADO PELO GRUPO
	ABAIXO, SEGUE M…TODO N√O IMPLEMENTADO PELO GRUPO
	ABAIXO, SEGUE M…TODO N√O IMPLEMENTADO PELO GRUPO
	ABAIXO, SEGUE M…TODO N√O IMPLEMENTADO PELO GRUPO	*/

    @Override
    public int depth(K key) {
        return depth(root, key);
    }

    private int depth(Node node, K key) {
        return 0;
    }
}