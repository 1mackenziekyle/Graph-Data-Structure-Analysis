package cpen221.mp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Task3DWTests {

    private static DWInteractionGraph dwig1;
    private static DWInteractionGraph dwig2;

    @BeforeAll
    public static void setupTests() throws FileNotFoundException {
        dwig1 = new DWInteractionGraph("resources/Task3Transactions1.txt");
        dwig2 = new DWInteractionGraph("resources/Task3Transactions2.txt");
    }

    @Test
    public void testBFSGraph1() {
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 6);
        Assertions.assertEquals(expected, dwig1.BFS(1, 6));
    }

    @Test
    public void testDFSGraph1() {
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 6);
        Assertions.assertEquals(expected, dwig1.DFS(1, 6));
    }

    @Test
    public void testBFSGraph2() {
        List<Integer> expected = Arrays.asList(1, 3, 5, 6, 4, 8, 7, 2, 9, 10);
        Assertions.assertEquals(expected, dwig2.BFS(1, 10));
    }

    @Test
    public void testDFSGraph2() {
        List<Integer> expected = Arrays.asList(1, 3, 4, 8, 5, 7, 2, 9, 10);
        Assertions.assertEquals(expected, dwig2.DFS(1, 10));
    }

    @Test
    public void testDFSGraph3() {
        Assertions.assertNull(dwig2.DFS(11, 10));
    }

    @Test
    public void testBFSGraph3() {
        Assertions.assertEquals(null, dwig2.BFS(11, 10));
    }
}
