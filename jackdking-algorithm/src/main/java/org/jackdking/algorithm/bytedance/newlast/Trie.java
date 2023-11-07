package org.jackdking.algorithm.bytedance.newlast;

import java.util.HashMap;

/*
前缀树（Trie）：又叫字典树或单词查找树，主要应用场景为给定一个字符串集合构建一棵前缀树，然后给一个字符串，判断前缀树中是否存在该字符串或者该字符串的前缀。
 */
public class Trie {



    /**
     * 主要三个方法
     * <p>
     * 插入
     * 查找
     * 匹配
     */
//字典树节点
    class TrieTreeNode {
        //节点值
        private Character value;
        //该节点下属系列节点
        private HashMap<Character, TrieTreeNode> nexts;
        //该节点是否为某一个单词的结尾标志
        private boolean endNodeFlag;


        //初始化
        public TrieTreeNode(Character value) {
            this.value = value;
            nexts = new HashMap<>();
            this.endNodeFlag = false;
        }

        //getter和setter
        public HashMap<Character, TrieTreeNode> getNexts() {
            return nexts;
        }

        public boolean isEndNodeFlag() {
            return endNodeFlag;
        }

        public void setEndNodeFlag(boolean endNodeFlag) {
            this.endNodeFlag = endNodeFlag;
        }
    }

    public class TrieTree {
        //根节点值
        Character rootValue = '$';
        //根节点
        TrieTreeNode root;

        //初始化
        public TrieTree() {
            root = new TrieTreeNode(rootValue);
        }

        //插入
        public void insert(String word) {
            //当前节点
            TrieTreeNode nowNode = this.root;
            char[] chs = word.toCharArray();
            for (char c : chs) {
                //当前节点的下属节点中不包含c，就创建新的节点
                if (!nowNode.getNexts().containsKey(c)) {
                    //创建下一个节点
                    TrieTreeNode newNode = new TrieTreeNode(c);
                    //将新的节点放入nownode下属节点中
                    nowNode.getNexts().put(c, newNode);
                }
                //更新nownode到下一层
                nowNode = nowNode.getNexts().get(c);
            }
            //设置单词结束标志
            nowNode.setEndNodeFlag(true);
        }

        //查找
        public boolean search(String word) {
            //当前节点
            TrieTreeNode nowNode = this.root;
            char[] chs = word.toCharArray();
            for (char c : chs) {
                //当前节点的下属节点中包含c，就继续更新nownode，否则没有，返回false
                if (nowNode.getNexts().containsKey(c)) {
                    nowNode = nowNode.getNexts().get(c);
                } else {
                    return false;
                }
            }
            //遍历结束后，判断当前节点是否为单词结束节点
            return nowNode.isEndNodeFlag();
        }

        //匹配  与查找只有一点点不同，不用判断单词结束节点
        public boolean startsWith(String prefix) {
            TrieTreeNode nowNode = this.root;
            char[] chs = prefix.toCharArray();
            for (char c : chs) {
                //当前节点的下属节点中包含c，就继续更新nownode，否则没有，返回false
                if (nowNode.getNexts().containsKey(c)) {
                    nowNode = nowNode.getNexts().get(c);
                } else {
                    return false;
                }
            }
            return true;
        }
    }
//
//————————————————
//        版权声明：本文为CSDN博主「光年在眼前」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/qq_46634315/article/details/128576613
}
