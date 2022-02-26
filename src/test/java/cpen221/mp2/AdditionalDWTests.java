package cpen221.mp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.*;

public class AdditionalDWTests {
    private static DWInteractionGraph dwig1;
    private static DWInteractionGraph dwig2;
    private static DWInteractionGraph dwig3;
    private static DWInteractionGraph dwig4;

    @BeforeAll
    public static void setupTests() throws FileNotFoundException {
        dwig1 = new DWInteractionGraph("resources/Task1-2Transactions.txt", new int[]{3,9});
        dwig2 = new DWInteractionGraph("resources/Task1-2Transactions.txt", new int[]{1,4});
        dwig3 = new DWInteractionGraph("resources/Task3Transactions3.txt");
        dwig4 = new DWInteractionGraph("resources/Task3Transactions4.txt");
    }

    @Test
    public void task1and2TestsFileTimeInput(){
        Set<Integer> expected = new HashSet<>(Arrays.asList(0, 1, 4, 8));
        Assertions.assertEquals(expected, dwig1.getUserIDs());
        Assertions.assertEquals(1, dwig1.getEmailCount(1, 0));
        Assertions.assertEquals(1, dwig1.getEmailCount(8, 0));
        Assertions.assertArrayEquals(new int[]{0,0,0}, dwig2.ReportActivityInTimeWindow(new int[]{0,1}));
        Assertions.assertArrayEquals(new int[]{2,2,2}, dwig2.ReportActivityInTimeWindow(new int[]{0,4}));
    }

    @Test
    public void testBFSGraph3() {
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Assertions.assertEquals(expected, dwig3.BFS(1, 9));
    }

    @Test
    public void testDFSGraph3() {
        List<Integer> expected = Arrays.asList(1, 2, 4, 6, 3, 5, 7, 8, 9);
        Assertions.assertEquals(expected, dwig3.DFS(1, 9));
    }

    @Test
    public void testBFSGraph4() {
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 8, 7, 9, 10, 12, 13, 11, 14);
        Assertions.assertEquals(expected, dwig4.BFS(1, 14));
    }

    @Test
    public void testDFSGraph4() {
        List<Integer> expected = Arrays.asList(1, 2, 3, 5, 7, 6, 10, 12,13, 11,14);
        Assertions.assertEquals(expected, dwig4.DFS(1, 14));
    }


}

