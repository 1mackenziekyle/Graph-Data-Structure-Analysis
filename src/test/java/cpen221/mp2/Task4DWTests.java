package cpen221.mp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Task4DWTests {

    private static DWInteractionGraph dwig1;
    private static DWInteractionGraph dwig2;
    private static DWInteractionGraph dwig3;
    private static DWInteractionGraph dwig4Users;
    private static DWInteractionGraph dept1Dwig;
    private static DWInteractionGraph dwigEdgeCase;

    @BeforeAll
    public static void setupTests() throws FileNotFoundException {
        dwig1 = new DWInteractionGraph("resources/Task4Transactions1.txt");
        dwig2 = new DWInteractionGraph("resources/Task4Transactions2.txt");
        dwig3 = new DWInteractionGraph("resources/Task4Transactions3.txt");
        dwig4Users = new DWInteractionGraph("resources/Task4Transactions4Users.txt");

    }

    @Test
    public void testMaxedBreachedUserCount1() {
        Assertions.assertEquals(8, dwig1.MaxBreachedUserCount(2));
    }

    @Test
    public void testMaxedBreachedUserCount2() {
        Assertions.assertEquals(10, dwig2.MaxBreachedUserCount(4));
    }

    @Test
    public void testListIterator() {
        // Used to compare the listIterator's behaviour to the input transactions text files.

        for (int[] transaction : dwig3.getInteractionLog()) {
            System.out.println(transaction[0] + " " + transaction[1] + " " + transaction[2]);
        }
        System.out.println(dwig3.getEndingIndex(1, 0));
    }

    @Test
    public void testCorrectInfectionTrees() {
        LinkedList<int[]> interactionLog4Users = new LinkedList<int[]>();
        interactionLog4Users.add(new int[] {1,2,1}); //     Expected output:
        interactionLog4Users.add(new int[] {4,2,2}); //     1         4         3
        interactionLog4Users.add(new int[] {3,1,3}); //    / \         \         \
        interactionLog4Users.add(new int[] {2,3,4}); //   4   2         2         1
        interactionLog4Users.add(new int[] {4,3,5}); //      /         /         /
        interactionLog4Users.add(new int[] {1,4,6}); //     3         3         4
        interactionLog4Users.add(new int[] {2,4,7}); //
        // first expected tree
        TreeNode firstTreeRightChild = new TreeNode(2, new TreeNode(3));
        TreeNode firstTree = new TreeNode(1, new TreeNode(4));
        firstTree.addChild(new TreeNode(2));
        firstTree.addChild(firstTreeRightChild);
        // second expected tree
        TreeNode secondTreeChild = new TreeNode(2, new TreeNode(3));
        TreeNode secondTree = new TreeNode(4, secondTreeChild);
        // third expected tree
        TreeNode thirdTreeChild = new TreeNode(1, new TreeNode(4));
        TreeNode thirdTree = new TreeNode(3, thirdTreeChild);

        ArrayList<TreeNode> expectedInfectionTrees = new ArrayList<>();
        expectedInfectionTrees.add(firstTree);
        expectedInfectionTrees.add(secondTree);
        expectedInfectionTrees.add(thirdTree);

        Assertions.assertEquals(4, dwig4Users.MaxBreachedUserCount(1));

    }
}

