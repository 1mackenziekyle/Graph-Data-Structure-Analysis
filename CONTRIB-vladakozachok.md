1) Created an Edge class, describing each edge in terms of the senderId, receiverID and an integer list of sendTimes in seconds.
   - constructors with sendTimes list as param and without
   - can get each element
   - can edit the SendTimes
2) Created all DW constructors except DWInteractionGraph(String fileName, int[] timeFilter)
3) Created all UDW constructors except UDWInteractionGraph(UDWInteractionGraph inputUDWIG, List<Integer> userFilter)
4) Created getEmailCount() for UDW and DW
5) Created getUserID() for UDW and DW
6) Created the following task 2 methods:
   - ReportOnTimeWindow DW
   - ReportOnTimeWindow UDW
   - NthMostActive UDW
   - ReportOnUser UDW
7) Created the following task 3 methods:
   - Write DFS and helper function
   - Write PathExists for UDW and helper function
   - Debug NumComponents
8) Created the interactions log used in the logic for task4
9) Created empty graph tests
10) Wrote RIs and AFs for Edge, UDWInteractionGraph, DWInteractionGraph
11) Changed implementation to be composition of hashmap instead of extending for both UDW and DW
12) Added functionality comments to UDW and DW methods 