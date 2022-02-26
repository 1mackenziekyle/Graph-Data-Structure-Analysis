package cpen221.mp2;

import com.sun.source.tree.Tree;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;


public class DWInteractionGraph {

    private final Map<Integer, LinkedList<Edge>> directedGraph;
    private final List<int[]> interactionLog;

    //Rep invariant:
    //
    // all keys in directedGraph are non-negative
    // all keys and values in directedGraph are non-null
    // directedGraph is non-null
    //
    // all entries in interaction log are non-null
    // all integers in each array of interactionLog are non-null and greater than 0

    //Abstraction Function:
    // represents interactions between users in a directed weighted graph through connections in
    // directedGraph

    /* ------- Task 1 ------- */
    /* Building the Constructors */

    /**
     * Creates a new directedGraph using an email interaction file.
     * The email interaction file will be in the resources directory.
     *
     * @param fileName the name of the file in the resources
     *                 directory containing email interactions
     */
    public DWInteractionGraph(String fileName) {
        directedGraph = new HashMap<>();
        interactionLog = new LinkedList<>();

        try {
            //scan the document and parse data into sender, receiver and timestamp for each interaction
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextInt()) {
                int sender = scanner.nextInt();
                int receiver = scanner.nextInt();
                int timeStamp = scanner.nextInt();

                //store the interaction as a single piece of data
                int[] data = new int[]{sender, receiver, timeStamp};

                //update interaction log (used for task 4 implementation)
                updateInteractionLog(data);

                //check if the graph contains the sender
                if(directedGraph.containsKey(sender)){
                    boolean containsEdge = false;
                    //check if the graph contains the appropriate edge
                    for (int i = 0; i<directedGraph.get(sender).size(); i++){
                        //cycle through the existing edges
                        //if an edge with the current sender and receiver exists, add the timestamp to the edge
                        if(directedGraph.get(sender).get(i).getSender() == sender && directedGraph.get(sender).get(i).getReceiver()==receiver){
                            directedGraph.get(sender).get(i).addSendTime(timeStamp);
                            containsEdge = true;
                            break;
                        }
                    }
                    //if the edge does not exist, create a new edge
                    if(!containsEdge){
                        Edge edge = new Edge(sender, receiver);
                        edge.addSendTime(timeStamp);

                        //place the edge into the linked list corresponding to the sender, such that the edges are in ascending order by receiverID
                        for(int index=0; index<directedGraph.get(sender).size(); index++){
                            if(receiver > directedGraph.get(sender).get(index).getReceiver()){
                                if(index+1<directedGraph.get(sender).size()){
                                    if(receiver < directedGraph.get(sender).get(index+1).getReceiver()){
                                        directedGraph.get(sender).add(index+1,  edge);
                                        break;
                                    }
                                }
                            }
                        }
                        if(!directedGraph.get(sender).contains(edge)){
                            directedGraph.get(sender).add(edge);
                        }
                    }
                }
                //if the graph does not contain the sender
                //create a new list of edges and a new edge
                //add sender and value to the graph
                else {
                    LinkedList<Edge> edgeList = new LinkedList<>();
                    Edge edge = new Edge(sender, receiver);
                    edge.addSendTime(timeStamp);
                    edgeList.add(edge);
                    directedGraph.put(sender, edgeList);
                }
            }
            //close the scanner
            scanner.close();
        } catch (FileNotFoundException e) {
            //throw exception if specified file is not found and cannot be scanned
            e.printStackTrace();
        }
    }

    /**
     * Creates a new directedGraph using an email interaction file.
     * The email interaction file will be in the resources directory.
     *
     * @param fileName the name of the file in the resources
     *                 directory containing email interactions
     */
    public DWInteractionGraph(String fileName, int[] timeFilter) {
        directedGraph = new HashMap<>();
        interactionLog = new LinkedList<>();

        try {
            //scan the document and parse data into sender, receiver and timestamp for each interaction
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextInt()) {
                int sender = scanner.nextInt();
                int receiver = scanner.nextInt();
                int timeStamp = scanner.nextInt();

                //check if the timestamp falls into the specified time range
                if (timeStamp >= timeFilter[0] && timeStamp <= timeFilter[1]) {
                    //store the interaction as a single piece of data
                    int[] data = new int[]{sender, receiver, timeStamp};

                    //update interaction log (used for task 4 implementation)
                    updateInteractionLog(data);

                    //check if the graph contains the sender
                    if (directedGraph.containsKey(sender)) {
                        boolean containsEdge = false;
                        //check if the graph contains the appropriate edge
                        for (int i = 0; i < directedGraph.get(sender).size(); i++) {
                            //cycle through the existing edges
                            //if an edge with the current sender and receiver exists, add the timestamp to the edge
                            if (directedGraph.get(sender).get(i).getSender() == sender && directedGraph.get(sender).get(i).getReceiver() == receiver) {
                                directedGraph.get(sender).get(i).addSendTime(timeStamp);
                                containsEdge = true;
                                break;
                            }
                        }
                        //if the edge does not exist, create a new edge
                        if (!containsEdge) {
                            Edge edge = new Edge(sender, receiver);
                            edge.addSendTime(timeStamp);

                            //place the edge into the linked list corresponding to the sender, such that the edges are in ascending order by receiverID
                            for (int index = 0; index < directedGraph.get(sender).size(); index++) {
                                if (receiver > directedGraph.get(sender).get(index).getReceiver()) {
                                    if (index + 1 < directedGraph.get(sender).size()) {
                                        if (receiver < directedGraph.get(sender).get(index + 1).getReceiver()) {
                                            directedGraph.get(sender).add(index + 1, edge);
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!directedGraph.get(sender).contains(edge)) {
                                directedGraph.get(sender).add(edge);
                            }
                        }
                    }
                    //if the graph does not contain the sender
                    //create a new list of edges and a new edge
                    //add sender and value to the graph
                    else {
                        LinkedList<Edge> edgeList = new LinkedList<>();
                        Edge edge = new Edge(sender, receiver);
                        edge.addSendTime(timeStamp);
                        edgeList.add(edge);
                        directedGraph.put(sender, edgeList);
                    }
                }
            }
            //close the scanner
            scanner.close();
        } catch (FileNotFoundException e) {
            //throw exception if specified file is not found and cannot be scanned
            e.printStackTrace();
        }
    }

    /**
     * Creates a new directedGraph from a directedGraph object
     * and considering a time window filter.
     *
     * @param inputDWIG a directedGraph object
     * @param timeFilter an integer array of length 2: [t0, t1]
     *                   where t0 <= t1. The created directedGraph
     *                   should only include those emails in the input
     *                   directedGraph with send time t in the
     *                   t0 <= t <= t1 range.
     */
    public DWInteractionGraph( DWInteractionGraph inputDWIG, int[] timeFilter) {
        directedGraph = new HashMap<>();
        interactionLog = new LinkedList<>();

        Map<Integer, LinkedList<Edge>> oldGraph = inputDWIG.getGraphMap();
        //loop through the senders and their edges
        for (Integer emailUser : oldGraph.keySet()) {
            for (Edge inputEdge : oldGraph.get(emailUser)) {
                try {
                    //if the graph does not contain sender, add the sender and its updated edges to the new graph
                    if (!directedGraph.containsKey(emailUser)) {
                        LinkedList<Edge> userList = new LinkedList<>();
                        Edge updatedEdge = new Edge(inputEdge, timeFilter);
                        userList.add(updatedEdge);

                        //update the interaction log
                        int data[] = {updatedEdge.getSender(), updatedEdge.getReceiver(), 0};
                        for(int time: updatedEdge.getSendTimes()){
                            data[2] = time;
                            updateInteractionLog(data.clone());

                        }
                        directedGraph.put(emailUser, userList);
                    }
                }
                catch (RuntimeException rte) {
                    System.out.println("No edges on time interval for directedGraph user.");
                }
            }
        }
    }

    /**
     * Creates a new directedGraph from a directedGraph object
     * and considering a list of User IDs.
     *
     * @param inputDWIG a directedGraph object
     * @param userFilter a List of User IDs. The created directedGraph
     *                   should exclude those emails in the input
     *                   directedGraph for which neither the sender
     *                   nor the receiver exist in userFilter.
     */
    public DWInteractionGraph(DWInteractionGraph inputDWIG, List<Integer> userFilter) {
        directedGraph = new HashMap<>();
        interactionLog = new LinkedList<>();

        //get a hashmap of the input graph
        Map <Integer, LinkedList<Edge>> originalMap = inputDWIG.getGraphMap();

        //loop through the original map
        for(int user : originalMap.keySet()){
            //if the user is part of the required users add the user and all of its edges
            if(userFilter.contains(user)){
                directedGraph.put(user, originalMap.get(user));

                //update interaction log
                for(Edge edge : directedGraph.get(user)){
                    int receiver = edge.getReceiver();
                    for(int time : edge.getSendTimes()){
                        updateInteractionLog(new int[]{user, receiver, time});
                    }
                }

            } else {
                for(Edge edge : originalMap.get(user)){
                    // if the receiver is part of the required users, add the sender and receiver of the
                    // corresponding edge to the new graph
                    if(userFilter.contains(edge.getReceiver())){
                        if (!directedGraph.containsKey(edge.getSender())) {
                            directedGraph.put(edge.getSender(), new LinkedList<>());
                        }
                        directedGraph.get(edge.getSender()).add(edge);

                        //update interaction log
                        for(int time: edge.getSendTimes()){
                            updateInteractionLog(new int[]{edge.getSender(), edge.getReceiver(), time});
                        }
                    }
                }
            }
        }
    }


    //takes in an input array of length three that describes an interaction
    //data[0]= sender
    //data[1]= receiver
    //data[2] = timeStamp
    //adds data to the interaction log
    private void updateInteractionLog(int[] data){
        //add the interaction to the log
        if(!interactionLog.contains(data)){
            //check if the log is the not empty
            if(!interactionLog.isEmpty()){
                //if the log only contains one interaction, data1
                //insert data before data1 if the time stamp of data is less than that of data1
                //otherwise insert data after data1
                if(interactionLog.size() == 1){
                    if(interactionLog.get(0)[2]<data[2]){
                        interactionLog.add(data.clone());
                    }
                    else{
                        interactionLog.add(0, data.clone());
                    }
                }
                //if the log contains multiple interactions, cycle through the interactions and insert data
                //such that the interactions are organized in order of ascending time stamp
                else {
                    boolean containsData = false;
                    for (int interaction = 0; interaction < interactionLog.size()-1; interaction++) {
                        if (interactionLog.get(interaction)[2] <= data[2] && interactionLog.get(interaction+1)[2] > data[2]) {
                            interactionLog.add(interaction+1, data.clone());
                            containsData=true;
                            break;
                        }
                    }
                    //add the data at the end if it has not yet been added
                    if(!containsData){
                        interactionLog.add(data.clone());
                    }
                }
            }
            //if the log is empty add the data
            else{
                interactionLog.add(data.clone());
            }
        }
    }


    /**
     * @return a Set of Integers, where every element in the set is a User ID
     * in directedGraph directedGraph.
     */
    public Set<Integer> getUserIDs() {
        //create a set of user IDs
        Set<Integer> userIDs = new HashSet<>(directedGraph.keySet());

        List<int[]> hello = this.interactionLog;
        //loop through each edge and add each sender and receiver to the set of user IDs
        for(LinkedList<Edge> edges: directedGraph.values()){
            for(Edge edge : edges){
                Integer receiver = edge.getReceiver();
                userIDs.add(receiver);
            }
        }

        return userIDs;
    }

    /**
     * @param sender the User ID of the sender in the email transaction.
     * @param receiver the User ID of the receiver in the email transaction.
     * @return the number of emails sent from the specified sender to the specified
     * receiver in directedGraph directedGraph.
     */
    public int getEmailCount(int sender, int receiver) {
        int emailCount=0;

        //if the sender is not in the graph, return 0 emails
        if(!directedGraph.containsKey(sender)){
            return emailCount;
        }

        //loop through the edges and add emails sent and set email count to the
        //number of emails sent
        for (Edge edge : directedGraph.get(sender)){
            if(edge.getReceiver() == receiver){
                emailCount =  edge.getSendTimes().size();
                break;
            }
        }
        return emailCount;
    }

    /**
     *
     * @return an unmodifiable hashmap representing the graph
     */
    protected Map<Integer, LinkedList<Edge>> getGraphMap(){
        return Collections.unmodifiableMap(directedGraph);
    }

    /* ------- Task 2 ------- */

    /**
     * Given an int array, [t0, t1], reports email transaction details.
     * Suppose an email in directedGraph graph is sent at time t, then all emails
     * sent where t0 <= t <= t1 are included in directedGraph report.
     * @param timeWindow is an int array of size 2 [t0, t1] where t0<=t1.
     * @return an int array of length 3, with the following structure:
     * [NumberOfSenders, NumberOfReceivers, NumberOfEmailTransactions]
     */
    public int[] ReportActivityInTimeWindow(int[] timeWindow) {
        Set<Integer> senders = new HashSet<>();
        Set<Integer> receivers = new HashSet<>();
        int emailCount =0;

        //loop through all the edges and their send times
        for(LinkedList<Edge> edges : directedGraph.values()){
            for (Edge edge : edges){
                for (int emailTime : edge.getSendTimes()){
                    //if the send time is within the required time window
                    //add the sender to the senders set
                    //add the receiver to the receivers set
                    //and increment the number of emails
                    if(emailTime>=timeWindow[0] && emailTime<=timeWindow[1]){
                        senders.add(edge.getSender());
                        receivers.add(edge.getReceiver());
                        emailCount++;
                    }
                }
            }
        }

        return new int[]{senders.size(), receivers.size(), emailCount};
    }

    /**
     * Given a User ID, reports the specified User's email transaction history.
     * @param userID the User ID of the user for which the report will be
     *               created.
     * @return an int array of length 3 with the following structure:
     * [NumberOfEmailsSent, NumberOfEmailsReceived, UniqueUsersInteractedWith]
     * If the specified User ID does not exist in directedGraph instance of a graph,
     * returns [0, 0, 0].
     */
    public int[] ReportOnUser(int userID) {
        int emailsSent = 0;
        int emailsReceived = 0;
        Set<Integer> uniqueUsersInteractedWith = new HashSet<>();

        //check whether any of the users send or receive from the specified user
        //if they do document the number of emails sent and received as well as the user that is
        //interacted with
        for (Integer otherUser : this.getUserIDs()) {
            int numSentToOther = getEmailCount(userID, otherUser);
            emailsSent += numSentToOther;
            int numReceivedFromOther = getEmailCount(otherUser, userID);
            emailsReceived += numReceivedFromOther;

            if (numSentToOther > 0 || numReceivedFromOther > 0) {
                uniqueUsersInteractedWith.add(otherUser);
            }
        }

        int numUniqueUsersInteractedWith = uniqueUsersInteractedWith.size();

        return new int[]{emailsSent, emailsReceived, numUniqueUsersInteractedWith};
    }

    /**
     * @param N a positive number representing rank. N=1 means the most active.
     * @param interactionType Represent the type of interaction to calculate the rank for
     *                        Can be SendOrReceive.Send or SendOrReceive.RECEIVE
     * @return the User ID for the Nth most active user in specified interaction type. If there
     * are less than N users, returns -1.
     * Sorts User IDs by their number of sent or received emails first. In the case of a
     * tie, secondarily sorts the tied User IDs in ascending order.
     */
    public int NthMostActiveUser(int N, SendOrReceive interactionType) {
        int reportIdx = (interactionType == SendOrReceive.SEND ? 0 : 1);

        //order the users in order of activity
        List<Integer> testUserStream = new ArrayList<>(this.getUserIDs()).stream()
                .sorted((x,y) -> (this.ReportOnUser(x)[reportIdx] != this.ReportOnUser(y)[reportIdx])
                        ? Integer.compare(this.ReportOnUser(y)[reportIdx], this.ReportOnUser(x)[reportIdx])
                        :Integer.compare(x,y))
                .toList();

        //if there are less users than the desired N, return -1
        if (N > directedGraph.keySet().size()) {
            return -1;
        }

        return testUserStream.get(N-1);
    }

    /* ------- Task 3 ------- */

    /**
     * performs breadth first search on the directedGraph object
     * to check path between user with userID1 and user with userID2.
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return if a path exists, returns aa list of user IDs
     * in the order encountered in the search.
     * if no path exists, return null.
     */
    public List<Integer> BFS(int userID1, int userID2) {
        List<Integer> visited = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        // new
        queue.add(userID1);
        // new
        if (userID1 == userID2) {
            visited.add(userID1);
            return visited;
        }
        if (!this.getUserIDs().contains(userID1) || !this.getUserIDs().contains(userID2)) {
            return null;
        }
        return BFS(userID1, userID2, visited, queue);
    }

    public List<Integer> BFS(int userID1, int userID2, List<Integer> visited, Queue<Integer> queue) {
        if (!visited.contains(userID1)) {
            visited.add(userID1);
        }

        if (directedGraph.containsKey(userID1)) {
            for (Edge userEdge : directedGraph.get(userID1)) {
                if (!visited.contains(userEdge.getReceiver()) && this.getUserIDs().contains(userEdge.getReceiver())) {
                    queue.add(userEdge.getReceiver());
                }
            }

            for (Edge userEdge : directedGraph.get(userID1)) {
                Integer adjNode = userEdge.getReceiver();
                if (this.getUserIDs().contains(adjNode)) {
                    if (!visited.contains(adjNode)) {
                        visited.add(adjNode);
                    }
                    if (adjNode == userID2) {
                        return visited;
                    }
                }
            }
        }

        queue.poll();
        return BFS(queue.peek(), userID2, visited, queue);
    }

    /**
     * performs depth first search on the directedGraph object
     * to check path between user with userID1 and user with userID2.
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return if a path exists, returns aa list of user IDs
     * in the order encountered in the search.
     * if no path exists, should return null.
     */
    public List<Integer> DFS(int userID1, int userID2) {
        List<Integer> path = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();

        //add the first user to path and mark them as visited
        path.add(userID1);
        visited.add(userID1);

        //preform DFS path search
        DFSPath(userID1, userID2, path, visited);

        if(path.size()==1){
            return null;
        }
        return path;
    }

    /**
     * @param userID1 non-negative, non-null integer value to user to start at
     * @param userID2 non-negative, non-null integer value to user to end at
     * @param path the nodes visited, in order
     * @param visited the unordered set of nodes visited in BFS
     */
    private void DFSPath(int userID1, int userID2, List<Integer> path, Set<Integer> visited){
        //check whether the user is part of the graph
        if(directedGraph.containsKey(userID1)){
            //check for all connected users
            for(Edge edge : directedGraph.get(userID1)){
                //if a connected user has not been visited, add connected user to path,
                //mark connected user as visited and preform DFSPath on connected user
                if(!visited.contains(edge.getReceiver()) && !path.contains(userID2)){
                    path.add(edge.getReceiver());
                    visited.add(edge.getReceiver());
                    if(edge.getReceiver()!= userID2 && !path.contains(userID2)) {
                        DFSPath(edge.getReceiver(), userID2, path, visited);
                    }
                }
            }
        }
    }

    /* ------- Task 4 ------- */

    /**
     *
     * @return copy of Interaction Log
     */
    public LinkedList<int[]> getInteractionLog() {
        return new LinkedList<int[]>(interactionLog);
    }

    /**
     * Read the MP README file carefully to understand
     * what is required from this method.
     * @param hours
     * @return the maximum number of users that can be polluted in N hours
     */
    public int MaxBreachedUserCount(int hours) {
        if (hours == 0) {
            return 1;
        }
        if (interactionLog.size() == 0) {
            return 1;
        }
        if(interactionLog.size() == 1) {
            return 2;
        }


        int startingIndex = 0;
        int currentMax = 0;
        ArrayList<TreeNode> infectionTrees = new ArrayList<>();
        int maxTreeSizeInTimeWindow = 0;
        int maxTreeSize = 0;
        int finalIndex = 0;
        int finalIndexOnTimeWindow = 0;
        ListIterator timeWindowIterator = interactionLog.listIterator(0);
        ListIterator<int[]> finalIndexFinder = interactionLog.listIterator(0);
        TreeNode interactionParent;
        TreeNode interactionChild;
        boolean infectionAdded = false; // boolean to see if the current interaction being read lengthens a chain

        while (finalIndexFinder.hasNext()) {
            finalIndex = finalIndexFinder.nextIndex();
            finalIndexFinder.next();
        }

        do {// keep moving starting index forward.
            finalIndexOnTimeWindow = getEndingIndex(hours, startingIndex); // new final index
            // we have startingIndex, which keeps moving forward, and finalIndexOnTimeWindow, which is adjusted at each loop.
            timeWindowIterator = interactionLog.listIterator(startingIndex);
            int[] interaction = interactionLog.get(startingIndex);
            interactionParent = new TreeNode(interaction[0]); // add the first interaction parent to the list
            interactionParent.addChild(new TreeNode(interaction[1])); // add the recipient to the parent's 'children' set.
            while (timeWindowIterator.nextIndex() <= finalIndexOnTimeWindow) { // until reached the end of time window
                interaction = (int[]) timeWindowIterator.next(); // update interaction
                // add the child to all parents
                for (TreeNode infectionTree : infectionTrees) {
                    if (infectionTree.contains(interaction[0])) { // if sender is already in this chain
                        infectionAdded = true;
                        if(!infectionTree.contains(interaction[1])) {
                            infectionTree.addChildToNode(interaction[0], new TreeNode(interaction[1]));
                            // only add if the tree does not already contain the receiver (no duplicates!)
                        }
                    }
                }
                if (!infectionAdded) {
                    // new nodes for child and parent
                    infectionTrees.add(new TreeNode(interaction[0], new TreeNode(interaction[1]))); // add new pair to list
                }

                infectionAdded = false;

            }


            if (!infectionTrees.isEmpty()) {
                for (TreeNode infectionTree : infectionTrees) {
                    if (infectionTree.getSize() > maxTreeSizeInTimeWindow) {
                        maxTreeSizeInTimeWindow = infectionTree.getSize();
                    }
                    if (maxTreeSizeInTimeWindow > maxTreeSize) {
                        maxTreeSize = maxTreeSizeInTimeWindow; // update overall max
                    }
                }
            }

        } while (getEndingIndex(hours, startingIndex) != finalIndex);
        return maxTreeSize;
    }

    /**
     *
     * @param hours the time in hours that the malware is able to spread before the firewall detects it
     * @param startingIndex the time at which the malware is given to the first user
     * @return
     */
    public Integer getEndingIndex(int hours, int startingIndex) {
        int startingTime = interactionLog.get(startingIndex)[2];
        int finalTime = startingTime + 3600*hours;
        ListIterator<int[]> listIter = interactionLog.listIterator(startingIndex);
        while (listIter.hasNext() && listIter.next()[2] <= finalTime) {
            // .next already called.
        }
        return listIter.previousIndex();
    }

    public Integer getStartingIndex(int hours, int endingIndex) {
        int finalTime = interactionLog.get(endingIndex)[2] - hours * 3600;
        ListIterator<int[]> listIter = interactionLog.listIterator(endingIndex);
        while (listIter.hasPrevious() && listIter.previous()[2] >= finalTime) {
            // Automatic Iteration
        }
        return listIter.previousIndex();
    }
}
