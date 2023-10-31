package org.jackdking.algorithm.bytedance.last;

import org.jackdking.algorithm.basesort.Sort;

/*
 * 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
 * https://www.nowcoder.com/practice/6e196c44c7004d15b1610b9afca8bd88
 *
 */
public class IsSubTree extends Sort {

    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        if (root1 == null || root2 == null) return false;
        // 之所以在这里加三种情况，是因为有可能有相同的点
        // 比如根点为8，根点的子结点也为8，但是真正的子结构是从子结点开始的
        return Judge(root1, root2) ||
                Judge(root1.left, root2) ||
                Judge(root1.right, root2);
    }


    public boolean Judge(TreeNode root1, TreeNode root2) {
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
