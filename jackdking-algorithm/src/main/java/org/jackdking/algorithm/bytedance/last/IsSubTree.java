package org.jackdking.algorithm.bytedance.last;

import org.jackdking.algorithm.basesort.Sort;

/*
 * 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
 * https://www.nowcoder.com/practice/6e196c44c7004d15b1610b9afca8bd88
 *
 */
public class IsSubTree extends Sort {


    public static void main(String[] args) {
        Integer[] arr = createIntegerArray(10);
        TreeNode tree1 = createBinaryTree(new Integer[]{8,8,7,8,2,4,7,8,9});
        TreeNode tree2 = createBinaryTree(new Integer[]{8,9});

        boolean  isSubTree = HasSubtree(tree1, tree2);
        System.out.println("是否为子树："+ isSubTree);

        isSubTree = judgeSubTree(tree1, tree2);
        System.out.println("是否为子树："+ isSubTree);

      isSubTree = judgeSubTree20231107(tree1, tree2);
      System.out.println("是否为子树："+ isSubTree);


      isSubTree = judgeSubTree20231109(tree1, tree2);
      System.out.println("是否为子树："+ isSubTree);


      isSubTree = judgeSubTree20231119(tree1, tree2);
      System.out.println("是否为子树："+ isSubTree);

    }

  private static boolean judgeSubTree20231119(TreeNode tree1, TreeNode tree2) {
      if (tree2==null || tree1 == null) {
        return false;
      }

      return isEq20231119(tree1, tree2) || judgeSubTree20231119(tree1.left, tree2) || judgeSubTree20231119(tree1.right, tree2);

  }

  private static boolean isEq20231119(TreeNode tree1, TreeNode tree2) {
      if (tree2 == null) {
        return true;
      }
      if (tree1==null) {
        return false;
      }

      if (tree1.val == tree2.val) {
        return isEq20231119(tree1.left, tree2.left) || isEq20231119(tree1.right, tree2.right);
      }
      return false;
  }

  private static boolean judgeSubTree20231109(TreeNode tree1, TreeNode tree2) {
      if (tree2 == null || tree1 == null) {
        return false;
      }
      return eqTree20231109(tree1, tree2) || eqTree20231109(tree1.left, tree2) || eqTree20231109(tree1.right, tree2);
  }

  private static boolean eqTree20231109(TreeNode tree1, TreeNode tree2) {
      if (tree2==null) {
        return true;
      }
      if (tree1==null) {
        return false;
      }

      if (tree1.val != tree2.val) {
        return eqTree20231109(tree1.left, tree2) || eqTree20231109(tree1.right, tree2);
      } else {
        return eqTree20231109(tree1.left, tree2.left) && eqTree20231109(tree1.right, tree2.right);
      }
  }

  private static boolean judgeSubTree20231107(TreeNode tree1, TreeNode tree2) {
      if (tree2==null || tree1 == null) {
        return false;
      }

      return eqTree20231107(tree1, tree2) || eqTree20231107(tree1.left, tree2) || eqTree20231107(tree1.right, tree2);
  }

  private static boolean eqTree20231107(TreeNode tree1, TreeNode tree2) {
      if (tree2 == null) {
        return true;
      }
      if (tree1==null) {
        return false;
      }
      if (tree1.val != tree2.val) {
        return eqTree20231107(tree1.left, tree2) || eqTree(tree1.right, tree2);
      }else {
        return eqTree20231107(tree1.left, tree2.left) && eqTree20231107(tree1.right, tree2.right);
      }

  }

  private static boolean judgeSubTree(TreeNode tree1, TreeNode tree2) {
        if (tree2 == null || tree1 == null) {
            return false;
        }

        return eqTree(tree1, tree2) || eqTree(tree1.left, tree2) || eqTree(tree1.right, tree2);

    }

    private static boolean eqTree(TreeNode tree1, TreeNode tree2) {
        if (tree2 == null) {
            return true;
        }

        if (tree1 == null){
            return false;
        }

        if (tree1.val != tree2.val) {
            return eqTree(tree1.left, tree2) || eqTree(tree1.right, tree2);
        }
        return eqTree(tree1.left, tree2.left) && eqTree(tree1.right, tree2.right);
    }


    public static boolean HasSubtree(TreeNode root1,TreeNode root2) {
        if (root1 == null || root2 == null) return false;
        // 之所以在这里加三种情况，是因为有可能有相同的点
        // 比如根点为8，根点的子结点也为8，但是真正的子结构是从子结点开始的
        return Judge(root1, root2) ||
                Judge(root1.left, root2) ||
                Judge(root1.right, root2);
    }


    public static boolean Judge(TreeNode root1, TreeNode root2) {
        // 这两个条件由递归式决定，有时候条件决定递归，有时候递归决定条件
        // 我这部分比较完了,返回true
        if (root2 == null) return true;
        // 如果root1，没有根和root2比较，所以返回false
        if (root1 == null) return false;

        // 如果根不相等，root2有可能是左子树或者右子树的一部分
        if (root1.val != root2.val) {
            return Judge(root1.left, root2) || Judge(root1.right, root2);
        }
        return Judge(root1.left, root2.left) && Judge(root1.right, root2.right);
    }
}
