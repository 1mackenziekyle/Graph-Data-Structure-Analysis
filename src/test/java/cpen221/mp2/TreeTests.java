package cpen221.mp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class TreeTests {
    private static TreeNode one;
    private static TreeNode two;
    private static TreeNode three;
    private static TreeNode four;
    private static TreeNode five;
    private static TreeNode six;
    private static TreeNode seven;

    @BeforeAll
    public static void setupTests() {
        //      1
        //     / \
        //    3   5
        //   / \ / \
        //  2  4 6  7

        one = new TreeNode(1);
        two = new TreeNode(2);
        four = new TreeNode(4);
        six = new TreeNode(6);
        seven = new TreeNode(7);
        three = new TreeNode(3);
        five = new TreeNode(5);

        three.addChild(two);
        three.addChild(four);

        five.addChild(six);
        five.addChild(seven);

        one.addChild(three);
        one.addChild(five);
    }

    @Test
    public void testGetSize() {
        Assertions.assertEquals(three.getSize(), 3);
        Assertions.assertEquals(two.getSize(), 1);
        Assertions.assertEquals(one.getSize(), 7);
    }

    @Test
    public void testContains() {
        Assertions.assertEquals(one.contains(1), true);
        Assertions.assertEquals(one.contains(8), false);
        Assertions.assertEquals(one.contains(3), true);
        Assertions.assertEquals(one.contains(4), true);
        Assertions.assertEquals(one.contains(10), false);
    }

    @Test
    public void testGetALlNodes1() {
        Set<Integer> all = new HashSet<>();
        all.add(1);
        all.add(2);
        all.add(3);
        all.add(4);
        all.add(5);
        all.add(6);
        all.add(7);
        Assertions.assertEquals(one.getAllNodes(), all);
    }

    @Test
    public void testGetAllNodes2() {
        Set<Integer> threesNodes = new HashSet<>();
        threesNodes.add(three.getVal());
        threesNodes.add(two.getVal());
        threesNodes.add(four.getVal());
        Assertions.assertEquals(threesNodes, three.getAllNodes());
    }

    @Test
    public void testAddChildToNode() {
        TreeNode eight = new TreeNode(8);
        TreeNode nine= new TreeNode(9);
        TreeNode ten = new TreeNode(10);
        TreeNode eleven = new TreeNode(11);
        TreeNode twelve = new TreeNode(12);

        //    8
        //   / \
        //  10  11

        eight.addChild(ten);
        eight.addChild(eleven);
        //    8
        //   / \
        //      11
        //        \\
        //         12

        eight.addChildToNode(11, twelve);
        Set<TreeNode> expectedChild = new HashSet<>();
        expectedChild.add(twelve);
        Assertions.assertEquals(eight.getSize(), 4);
        Assertions.assertEquals(eleven.getDirectChildren(), expectedChild);
    }

}
