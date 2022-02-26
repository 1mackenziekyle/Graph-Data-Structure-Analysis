package cpen221.mp2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TreeNode {
    private Integer val;
    private HashSet<TreeNode> children;

    /**
     * Creates a TreeNode with an inputted integer value and no children.
     * @param inputVal a non-null integer to assign as the value
     */
    public TreeNode(int inputVal) {
        this.val = inputVal;
        this.children = new HashSet<TreeNode>();
    }

    /**
     *
     * @param inputVal a non-null, non-negative integer to assign as the node's value
     * @param child a child to intialize as the first child
     */
    public TreeNode(int inputVal, TreeNode child) {
        this.val = inputVal;
        children = new HashSet<TreeNode>();
        children.add(child);
    }

    /**
     *
     * @param inputVal a non-null, non-negative integer value to assign as the node's value
     * @param children a set of children to initialize the treeNode's set of children
     */
    public TreeNode(int inputVal, HashSet<TreeNode> children) {
        this.val = inputVal;
        this.children = new HashSet<TreeNode>(children);
    }

    public Integer getVal() {
        return this.val;
    }

    public Integer getSize() {
        if (children.isEmpty()) {
            return 1;
        }
        int size = 1;
        for (TreeNode child : children) {
            size += child.getSize();
        }
        return size;
    }

    /**
     * Add a child TreeNode to the TreeNode's children.
     * @param newChild to add to children.
     */
    protected void addChild(TreeNode newChild) {
        children.add(newChild);
        return;
    }

    /**
     * Gets the children of a given node.
     * @return the children of the TreeNode
     */
    public Set<TreeNode> getDirectChildren() {
        return new HashSet<TreeNode>(children);
    }

    /**
     * Gets a set of all nodes inside a tree.
     * @return all nodes in the tree, including grandchildren, and so on.
     */
    public Set<Integer> getAllNodes() {
        HashSet<Integer> visited = new HashSet<>();
        visited.add(this.getVal());

        HashSet<Integer> allNodes = new HashSet<>();
        allNodes.add(this.getVal());

        for (TreeNode child : children) {
            allNodes.addAll(child.getAllNodes(allNodes, visited));
        }

        return allNodes;
    }

    /**
     * The recursive helper method to getALlNodes
     * @param allNodes the nodes that have been collected upon calling this method
     * @param visited the nodes that have been visited upon calling this method
     * @return the nodes below and including the node that this method is called on
     */
    public Set<Integer> getAllNodes(Set<Integer> allNodes, Set<Integer> visited) {
        if (this.children.isEmpty()) {
            Set<Integer> nodes = new HashSet<>();
            nodes.add(this.getVal());
            return nodes;
        }
        for (TreeNode child : children) {
            if (!visited.contains(child.getVal())) {
                allNodes.addAll(child.getAllNodes());
                visited.add(child.getVal());
            }
        }
        allNodes.add(this.getVal());
        return allNodes;
    }

    /**
     *
     * @param val, a non-null, non-negative integer value to search for in the tree
     * @return true or false depending on if that value exists in the tree
     */
    public boolean contains(int val) {
        if (this.val == val) {
            return true;
        }
        for (TreeNode child : children) {
            if (child.contains(val)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Recursive helper method to contains()
     * @param val non-null, non-negative integer value to find
     * @return boolean of whether or not the subtree contains the integer value
     */
    private TreeNode get(int val) {
        if (this.val == val) {
            return this;
        }
        for (TreeNode child : children) {
            if (child.contains(val)) {
                return child.get(val);
            }
        }
        return null;
    }

    /**
     * Adds a child to a node in a tree given the integer value of the node.
     *
     * @param val the non-null, non-negative integer value of the node to add teh child to
     * @param child the node to add to the parent node's set of children.
     */
    public void addChildToNode(Integer val, TreeNode child) {
        if (!this.contains(val)) {
            throw new RuntimeException();
        }
        this.get(val).addChild(child);
    }
}

