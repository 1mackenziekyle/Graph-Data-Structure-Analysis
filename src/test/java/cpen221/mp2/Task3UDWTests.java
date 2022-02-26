package cpen221.mp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


public class Task3UDWTests {

    @Test
    public void testNumComponent() {
        UDWInteractionGraph t = new UDWInteractionGraph("resources/Task3-components-test.txt");
        Assertions.assertEquals(1, t.NumberOfComponents());
    }

    @Test
    public void testNumComponent1() {
        UDWInteractionGraph t = new UDWInteractionGraph("resources/Task3-components-test1.txt");
        Assertions.assertEquals(2, t.NumberOfComponents());
    }

    @Test
    public void testPathExists() {
        UDWInteractionGraph t = new UDWInteractionGraph("resources/Task3-components-test.txt");
        Assertions.assertTrue(t.PathExists(1, 2));
        Assertions.assertTrue(t.PathExists(1, 3));
        Assertions.assertTrue(t.PathExists(1, 4));
        Assertions.assertTrue(t.PathExists(2, 3));
        Assertions.assertTrue(t.PathExists(2, 4));
        Assertions.assertTrue(t.PathExists(3, 4));
    }

    @Test
    public void testPathExists1() {
        UDWInteractionGraph t = new UDWInteractionGraph("resources/Task3-components-test1.txt");
        Assertions.assertTrue(t.PathExists(1, 2));
        Assertions.assertTrue(t.PathExists(3, 4));
        Assertions.assertFalse(t.PathExists(1, 4));
        Assertions.assertFalse(t.PathExists(2, 3));
    }

    @Test
    public void testSingleUser() {
        UDWInteractionGraph t = new UDWInteractionGraph("resources/Task3-components-test2.txt");
        Assertions.assertEquals(2, t.getEmailCount(1, 1));
        Assertions.assertEquals(1, t.NumberOfComponents());
        Assertions.assertTrue(t.PathExists(1, 1));
    }

    @Test
    public void testNoUser() {
        UDWInteractionGraph t = new UDWInteractionGraph("resources/Task3-components-test2.txt");
        UDWInteractionGraph t1 = new UDWInteractionGraph(t, List.of(2));
        Assertions.assertEquals(0, t1.NumberOfComponents());
    }


}