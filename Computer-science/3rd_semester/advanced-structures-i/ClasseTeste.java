/**
 * ClasseTeste
 */
public class ClasseTeste {

    public static void main(String[] args) {
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<Integer, Integer>();
        // tree.insert(0, 1);
        // tree.insert(2, 1);
        tree.insert(6, 1);
        tree.insert(0, 1);
        tree.insert(9, 1);
        tree.insert(10, 1);
        tree.insert(4, 1);
        tree.insert(2, 1);
        tree.insert(3, 1);
        tree.insert(5, 1);
        tree.insert(7, 1);
        tree.insert(1, 1);
        // tree.insert(8, 1);
        System.out.println(tree.toString());
        System.out.println("countNodes:" + tree.countNodes());
        System.out.println("countInternalNodes:" + tree.countInternalNodes());
        System.out.println("countLeaves:" + tree.countLeaves());
    }
}