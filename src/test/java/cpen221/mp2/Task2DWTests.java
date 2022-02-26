package cpen221.mp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Task2DWTests {

    private static DWInteractionGraph dwig;
    private static DWInteractionGraph dwig1;
    private static DWInteractionGraph dwig2;

    @BeforeAll
    public static void setupTests() throws FileNotFoundException {
        dwig = new DWInteractionGraph("resources/Task1-2Transactions.txt");
        dwig1 = new DWInteractionGraph(dwig, new int[]{3, 9});
        dwig2 = new DWInteractionGraph(dwig, Arrays.asList(2, 3, 4));
    }

    @Test
    public void testReportActivityInTimeWindowBase() {
        int[] expected1 = new int[]{5, 4, 7};
        Assertions.assertArrayEquals(expected1, dwig.ReportActivityInTimeWindow(new int[]{1, 15}));
        int[] expected2 = new int[]{1, 2, 2};
        Assertions.assertArrayEquals(expected2, dwig.ReportActivityInTimeWindow(new int[]{9, 12}));
    }

    @Test
    public void testReportActivityInTimeWindowGraph1() {
        int[] expected1 = new int[]{2, 2, 2};
        Assertions.assertArrayEquals(expected1, dwig1.ReportActivityInTimeWindow(new int[]{2, 7}));
        int[] expected2 = new int[]{4, 2, 4};
        Assertions.assertArrayEquals(expected2, dwig1.ReportActivityInTimeWindow(new int[]{3, 9}));
    }

    @Test
    public void testReportActivityInTimeWindowGraph2() {
        int[] expected1 = new int[]{0, 0, 0};
        Assertions.assertArrayEquals(expected1, dwig2.ReportActivityInTimeWindow(new int[]{3, 6}));
        int[] expected2 = new int[]{1, 1, 1};
        Assertions.assertArrayEquals(expected2, dwig2.ReportActivityInTimeWindow(new int[]{7, 7}));
    }

    @Test
    public void testReportOnUserBase() {
        Assertions.assertArrayEquals(new int[]{2, 3, 3}, dwig.ReportOnUser(0));
        Assertions.assertArrayEquals(new int[]{2, 1, 3}, dwig.ReportOnUser(8));
    }

    @Test
    public void testReportOnUserGraph1() {
        Assertions.assertArrayEquals(new int[]{1, 3, 3}, dwig1.ReportOnUser(0));
        Assertions.assertArrayEquals(new int[]{1, 0, 1}, dwig1.ReportOnUser(1));
    }

    @Test
    public void testReportOnUserGraph2() {
        Assertions.assertArrayEquals(new int[]{0, 2, 1}, dwig2.ReportOnUser(3));
        Assertions.assertArrayEquals(new int[]{0, 0, 0}, dwig2.ReportOnUser(6));
    }

    @Test
    public void testNthMostActiveUserBase() {
        Assertions.assertEquals(0, dwig.NthMostActiveUser(1, SendOrReceive.SEND));
        Assertions.assertEquals(0, dwig.NthMostActiveUser(1, SendOrReceive.RECEIVE));
    }

    @Test
    public void testNthMostActiveUserGraph1() {
        Assertions.assertEquals(1, dwig1.NthMostActiveUser(2, SendOrReceive.SEND));
        Assertions.assertEquals(8, dwig1.NthMostActiveUser(2, SendOrReceive.RECEIVE));
    }

    @Test
    public void testNthMostActiveUserGraph2() {
        Assertions.assertEquals(4, dwig2.NthMostActiveUser(2, SendOrReceive.SEND));
        Assertions.assertEquals(-1, dwig2.NthMostActiveUser(3, SendOrReceive.RECEIVE));
    }
}
