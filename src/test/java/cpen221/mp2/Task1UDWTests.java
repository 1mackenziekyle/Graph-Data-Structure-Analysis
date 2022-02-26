package cpen221.mp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task1UDWTests {
    private static UDWInteractionGraph testGraphBase;
    private static UDWInteractionGraph testGraph1;
    private static UDWInteractionGraph testGraph2;
    private static UDWInteractionGraph testGraph3;
    private static UDWInteractionGraph testGraph31;
    private static UDWInteractionGraph testGraph32;

    @BeforeAll
    public static void setupTests() {
        testGraphBase = new UDWInteractionGraph("resources/Task1-2UDWTransactions.txt");
        testGraph1 = new UDWInteractionGraph(testGraphBase, new int[]{0, 9});
        testGraph2 = new UDWInteractionGraph(testGraphBase, new int[]{10, 11});
        testGraph3 = new UDWInteractionGraph("resources/empty graph");
        testGraph31 = new UDWInteractionGraph(testGraph3, new int[]{3,5});
        testGraph32 = new UDWInteractionGraph(testGraph3, Arrays.asList(0,1,2,3));
    }

    @Test
    public void testGetUserIds() {
        Assertions.assertEquals(new HashSet<>(Arrays.asList(0, 1, 2, 3)), testGraphBase.getUserIDs());
    }

    @Test
    public void testGetUserIds1() {
        Assertions.assertEquals(new HashSet<>(Arrays.asList(1, 2, 3)), testGraph2.getUserIDs());
    }

    @Test
    public void testGetEmailCount() {
        Assertions.assertEquals(2, testGraphBase.getEmailCount(1, 0));
        Assertions.assertEquals(2, testGraphBase.getEmailCount(0, 1));
    }

    @Test
    public void testGetEmailCount1() {
        Assertions.assertEquals(2, testGraph1.getEmailCount(1, 0));
        Assertions.assertEquals(2, testGraph1.getEmailCount(0, 3));
    }

    @Test
    public void testGetEmailCount2() {
        Assertions.assertEquals(0, testGraph2.getEmailCount(1, 0));
        Assertions.assertEquals(1, testGraph2.getEmailCount(1, 3));
    }

    @Test
    public void testUserConstructor() {
        List<Integer> userFilter = Arrays.asList(0, 1);
        UDWInteractionGraph t = new UDWInteractionGraph(testGraphBase, userFilter);
        Assertions.assertEquals(new HashSet<>(Arrays.asList(0, 1, 2, 3)), t.getUserIDs());
        Assertions.assertEquals(2, t.getEmailCount(0, 1));
        Assertions.assertEquals(2, t.getEmailCount(0, 3));
    }

    @Test
    public void testConstructionFromDW() throws FileNotFoundException {
        DWInteractionGraph dwig = new DWInteractionGraph("resources/Task1-2UDWTransactions.txt");
        UDWInteractionGraph udwig = new UDWInteractionGraph(dwig);
        Assertions.assertEquals(new HashSet<>(Arrays.asList(0, 1, 2, 3)), udwig.getUserIDs());
        Assertions.assertEquals(2, udwig.getEmailCount(2, 3));
    }

    @Test
    public void testConstructionFromDW1() throws FileNotFoundException {
        DWInteractionGraph dwig = new DWInteractionGraph("resources/Task1-2Transactions.txt");
        UDWInteractionGraph udwig = new UDWInteractionGraph(dwig);
        Assertions.assertEquals(new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 8)), udwig.getUserIDs());
        Assertions.assertEquals(2, udwig.getEmailCount(2, 3));
    }

    @Test
    public void testEmptyGraph(){
        Assertions.assertEquals(0, testGraph3.getEmailCount(1, 8));
        Assertions.assertEquals(0, testGraph3.getEmailCount(2, 3));
        Set<Integer> expectedUsers = new HashSet<>(Arrays.asList());
        Assertions.assertEquals(expectedUsers, testGraph3.getUserIDs());
        Assertions.assertEquals(0, testGraph31.getEmailCount(1, 8));
        Assertions.assertEquals(0, testGraph31.getEmailCount(2, 3));
        Assertions.assertEquals(expectedUsers, testGraph31.getUserIDs());
        Assertions.assertEquals(0, testGraph32.getEmailCount(1, 8));
        Assertions.assertEquals(0, testGraph32.getEmailCount(2, 3));
        Assertions.assertEquals(expectedUsers, testGraph32.getUserIDs());
    }

}
