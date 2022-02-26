package cpen221.mp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

public class EdgeDatatypeTests {
    /**
     * 
     * To Test:
     * - Test that the getTime
     * 
     */

     private static Edge edge1;
     private static List<Integer> timestamps1;

     public static void SetupTests() {

        
     }

     @Test
     public void testGetSendTimesInWindow() {
        List<Integer> expected = new ArrayList<>();
        timestamps1 = new ArrayList<Integer>(); 
        for (int i = 1; i < 99; i++) {
            timestamps1.add(i);
        }
        edge1 = new Edge(1, 2, timestamps1);

        for (int j = 20; j < 31; j++) {
            expected.add(j); // ints 20 to 30
        }
        int[] timeWindow = {20, 30};
        System.out.println(edge1.getSendTimesInWindow(timeWindow));
        //Assertions.assertEquals(expected, edge1.getSendTimesInWindow(timeWindow));
     }

    @Test
    public void testNoTimesInWindow() {
        List<Integer> expected = new ArrayList<>();
        timestamps1 = new ArrayList<Integer>();
        for (int i = 1; i <= 5; i++) {
            timestamps1.add(i); // timestamps: 1, 2, 3, 4, 5
        }
        edge1 = new Edge(1, 2, timestamps1);

        int[] timeWindow = {6,7};
        Assertions.assertEquals(expected, edge1.getSendTimesInWindow(timeWindow));
    }

}
