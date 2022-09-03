package VeryHard;

import java.util.*;

// O(n^2) - space complexity
class FlattenBinaryTree_01 {

  static BinaryTree leftMost(BinaryTree root){
    BinaryTree prev = root;
    while( root != null ){
      prev = root;
      root = root.left;
    }
    return prev;
  }

  static BinaryTree rightMost(BinaryTree root){
    BinaryTree prev = root;
    while( root != null ){
      prev = root;
      root = root.right;
    }
    return prev;
  }
  
  public static BinaryTree flattenBinaryTree(BinaryTree root) {
    if( root == null )
      return null;
    BinaryTree prev = flattenBinaryTree(root.left);
    BinaryTree next = flattenBinaryTree(root.right);

    BinaryTree rightMostOfPrev = rightMost(prev);
    if( prev != null ){
      rightMostOfPrev.right = root;
    }
    BinaryTree leftMostOfNext = leftMost(next);
    if( root != null ){
      root.left = rightMostOfPrev;
      root.right = leftMostOfNext;
    }
    if( next != null ){
      leftMostOfNext.left = root;
    }

    if( prev == null ){
      if( root == null )
        return next;
      return root;
    }
    return prev;
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
}
