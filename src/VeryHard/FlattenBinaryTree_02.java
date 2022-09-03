package VeryHard;

/**
 * O(n) - RunTime complexity
 */
public class FlattenBinaryTree_02 {

    private static class Trio{
        BinaryTree key, leftMost, rightMost;
        Trio(BinaryTree key, BinaryTree leftMost, BinaryTree rightMost){
            this.key = key;
            this.leftMost = leftMost;
            this.rightMost = rightMost;
        }
    }

    private static Trio process(BinaryTree root){
        if( root == null )
            return new Trio(null, null, null);
        Trio prevTrio = process(root.left);
        BinaryTree prev = prevTrio.key;

        Trio nextTrio = process(root.right);
        BinaryTree next = nextTrio.key;
        if( prev != null ){
            prevTrio.rightMost.right = root;
        }
        if( root != null ){
            root.left = prevTrio.rightMost;
            root.right = nextTrio.leftMost;
        }
        if( next != null ){
            nextTrio.leftMost.left = root;
        }

        BinaryTree leftMost, rightMost;
        leftMost = prevTrio.leftMost == null ? root : prevTrio.leftMost;
        rightMost = nextTrio.rightMost == null ? root : nextTrio.rightMost;

        if( prev == null ){
            if( root == null )
                return new Trio(next, leftMost, rightMost);
            return new Trio(root, leftMost, rightMost);
        }
        return new Trio(prev, leftMost, rightMost);
    }

    public static BinaryTree flattenBinaryTree(BinaryTree root) {
        Trio p = process(root);
        return p.key;
    }

    // This is the class of the input root. Do not edit it.
    static class BinaryTree {
        int value;
        BinaryTree left = null;
        BinaryTree right = null;

        public BinaryTree(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {

    }
}
