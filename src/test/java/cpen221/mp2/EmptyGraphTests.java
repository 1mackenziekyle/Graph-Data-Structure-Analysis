package cpen221.mp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.*;

public class EmptyGraphTests {
    private static UDWInteractionGraph testGraphUDW;
    private static UDWInteractionGraph testGraphUDW1;
    private static UDWInteractionGraph testGraphUDW2;
    private static UDWInteractionGraph testGraphUDW3;
    private static DWInteractionGraph testGraphDW;
    private static DWInteractionGraph testGraphDW1;
    private static DWInteractionGraph testGraphDW2;
    private static DWInteractionGraph testGraphDW3;


    @BeforeAll
    public static void setupTests() {
        testGraphDW = new DWInteractionGraph("resources/empty-graph.txt");
        testGraphDW1 = new DWInteractionGraph("resources/empty-graph.txt", new int[]{0,100});
        testGraphDW2 = new DWInteractionGraph(testGraphDW, new int[]{0,100});
        testGraphDW3 = new DWInteractionGraph(testGraphDW, Arrays.asList(0,1,2,3,4));
        testGraphUDW = new UDWInteractionGraph("resources/empty-graph.txt");
        testGraphUDW1 = new UDWInteractionGraph(testGraphUDW, new int[]{0,10});
        testGraphUDW2 = new UDWInteractionGraph(testGraphUDW, Arrays.asList(0,1,2,3,4));
        testGraphUDW3 = new UDWInteractionGraph(testGraphDW);
    }

    @Test
    public void task1TestEmptyGraphsUDW(){
        Set<Integer> expectedUsers = new HashSet<>(Arrays.asList());
        //test getEmailCount for emptyUDWs
        Assertions.assertEquals(0, testGraphUDW.getEmailCount(0, 100));
        Assertions.assertEquals(0, testGraphUDW1.getEmailCount(0, 100));
        Assertions.assertEquals(0, testGraphUDW2.getEmailCount(0, 100));
        Assertions.assertEquals(0, testGraphUDW3.getEmailCount(0, 100));
        //test expectedUsers for empty UDWs
        Assertions.assertEquals(expectedUsers, testGraphUDW.getUserIDs());
        Assertions.assertEquals(expectedUsers, testGraphUDW1.getUserIDs());
        Assertions.assertEquals(expectedUsers, testGraphUDW2.getUserIDs());
        Assertions.assertEquals(expectedUsers, testGraphUDW3.getUserIDs());
    }

    @Test
    public void task1TestEmptyGraphsDW(){
        Set<Integer> expectedUsers = new HashSet<>(Arrays.asList());
        //test getEmailCount for emptyUDWs
        Assertions.assertEquals(0, testGraphDW.getEmailCount(0, 100));
        Assertions.assertEquals(0, testGraphDW1.getEmailCount(0, 100));
        Assertions.assertEquals(0, testGraphDW2.getEmailCount(0, 100));
        Assertions.assertEquals(0, testGraphDW3.getEmailCount(0, 100));
        //test expectedUsers for empty UDWs
        Assertions.assertEquals(expectedUsers, testGraphUDW.getUserIDs());
        Assertions.assertEquals(expectedUsers, testGraphUDW1.getUserIDs());
        Assertions.assertEquals(expectedUsers, testGraphUDW2.getUserIDs());
        Assertions.assertEquals(expectedUsers, testGraphUDW3.getUserIDs());
    }

    @Test
    public void task2TestEmptyGraphsDW(){
        int[] expected = {0,0,0};
        //test reportActivityInTimeWindow
        Assertions.assertArrayEquals(expected, testGraphDW.ReportActivityInTimeWindow(new int[]{0,100}));
        Assertions.assertArrayEquals(expected, testGraphDW1.ReportActivityInTimeWindow(new int[]{0,100}));
        //test reportOnUser
        Assertions.assertArrayEquals(expected, testGraphDW2.ReportOnUser(5));
        //test NthMostActiveUser
        Assertions.assertEquals(-1, testGraphDW3.NthMostActiveUser(5, SendOrReceive.SEND));
        Assertions.assertEquals(-1, testGraphDW3.NthMostActiveUser(1, SendOrReceive.RECEIVE));
    }

    @Test
    public void task2TestEmptyGraphsUDW(){
        int[] expected = {0,0};
        //test reportActivityInTimeWindow
        Assertions.assertArrayEquals(expected, testGraphUDW.ReportActivityInTimeWindow(new int[]{0,100}));
        Assertions.assertArrayEquals(expected, testGraphUDW1.ReportActivityInTimeWindow(new int[]{0,100}));
        //test reportOnUser
        Assertions.assertArrayEquals(expected, testGraphUDW2.ReportOnUser(9));
        //test NthMostActiveUser
        Assertions.assertEquals(-1, testGraphUDW3.NthMostActiveUser(3));

    }

    @Test
    public void task3TestEmptyGraphsDW(){
        List<Integer> expected = null;
        //test DFS
        Assertions.assertNull(testGraphDW.DFS(5, 8));
        Assertions.assertNull(testGraphDW1.DFS(8,19));
        //test BFS
        Assertions.assertNull( testGraphDW.DFS(8,8));
        Assertions.assertNull( testGraphDW1.DFS(9,15));
    }

    @Test
    public void task3TestEmptyGraphsUDW(){
        //test DFS
        Assertions.assertEquals(0,testGraphUDW.NumberOfComponents());
        Assertions.assertEquals(0,testGraphUDW1.NumberOfComponents());
        //test pathExists
        Assertions.assertFalse(testGraphUDW2.PathExists(0,1));
        Assertions.assertFalse(testGraphUDW3.PathExists(4,5));

    }

}
