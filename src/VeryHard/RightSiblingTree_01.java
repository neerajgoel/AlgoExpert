package VeryHard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Using BFS Algorithm
 * O(n) - RunTime complexity where n is number of nodes
 * O(n) - SpaceTime complexity
 */
public class RightSiblingTree_01 {
    static Map<Integer, Integer> levelMap = new HashMap<>();

    private static void initLevelSet(BinaryTree root, int lvl){
        if( root == null )
            return;
        levelMap.put(root.value, lvl);
        initLevelSet(root.left, lvl + 1);
        initLevelSet(root.right, lvl + 1);
    }

    private static boolean isSameLevel(BinaryTree r1, BinaryTree r2){
        return levelMap.get(r1.value) == levelMap.get(r2.value);
    }

    public static BinaryTree rightSiblingTree(BinaryTree root) {
        initLevelSet(root, 0);
        Queue<BinaryTree> que = new LinkedList<>();
        // first node is always root
        que.add(root);
        BinaryTree temp;
        while( !que.isEmpty() ){
            temp = que.poll();
            if( temp == null )
                continue;
            System.out.println("temp : " + temp.value);

            // add left child in queue even if it is null
            que.add(temp.left);
            // add right child in queue even if it is null
            que.add(temp.right);

            // make connection to right sibling
            if( !que.isEmpty() && que.peek() != null && isSameLevel(temp, que.peek()) ){
                temp.right = que.peek();
            }
            else{
                temp.right = null;
            }
        }
        return root;
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
