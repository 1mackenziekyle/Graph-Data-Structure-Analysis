package cpen221.mp2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class UDWInteractionGraph {

    private  final Map<Set<Integer>, List<Integer>> undirectedGraph= new HashMap<>();
    private final Map<Integer, Integer> sortedUserEmails = new LinkedHashMap<>();

    //Rep Invariant:
    //
    // undirectedGraph is non-null
    // undirectedGraph keys are non-null sets containing only non-negative integers
    // undirectedGraph all values are non-null lists containing only non-negative integers
    //
    // sortedUserEmails has non-null and non-negative integer keys and values
    // sortedUserEmails is sorted in descending order by value

    //Abstraction Function:
    // represents interactions between users in an undirected weighted graph through connections in
    // directedGraph

    /* ------- Task 1 ------- */
    /* Building the Constructors */

    /**
     * Creates a new UDWInteractionGraph using an email interaction file.
     * The email interaction file will be in the resources directory.
     *
     * @param fileName the name of the file in the resources
     *                 directory containing email interactions
     */
    public UDWInteractionGraph(String fileName) {
        try {
            //scan the document and parse data into sender, receiver and timestamp for each interaction
            Scanner scanner = new Scanner(new File(fileName));

            while (scanner.hasNextInt()) {
                int sender = scanner.nextInt();
                int receiver = scanner.nextInt();
                int timeStamp = Integer.parseInt(scanner.next());

                //create a set containing the sender and receiver
                Set<Integer> users = new HashSet<>();

                users.add(sender);
                users.add(receiver);

                //check if the graph contains current sender receiver pair
                if (!undirectedGraph.containsKey(users)) {
                    //add the sender receiver set as key
                    undirectedGraph.put(Collections.unmodifiableSet(users), new ArrayList<>());
                }
                //add the time stamp to the list of values
                undirectedGraph.get(users).add(timeStamp);
            }

            //close the scanner
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new UDWInteractionGraph from a UDWInteractionGraph object
     * and considering a time window filter.
     *
     * @param inputUDWIG a UDWInteractionGraph object
     * @param timeFilter an integer array of length 2: [t0, t1]
     *                   where t0 <= t1. The created UDWInteractionGraph
     *                   should only include those emails in the input
     *                   UDWInteractionGraph with send time t in the
     *                   t0 <= t <= t1 range.
     */
    public UDWInteractionGraph(UDWInteractionGraph inputUDWIG, int[] timeFilter) {
        //get the map for the old graph
        Map<Set<Integer>, List<Integer>> originalMap = inputUDWIG.getGraphMap();

        //loop through the user set in the key set of the old graph and each time stamp interaction
        for(Set<Integer> userPair : originalMap.keySet()){
            for(int time : originalMap.get(userPair)){
                // if the time stamp falls within the timeFilter range add the interaction to
                // the new graph
                if(time<=timeFilter[1] && time>=timeFilter[0]){
                    if(!undirectedGraph.containsKey(userPair)){
                        undirectedGraph.put(userPair, new ArrayList<>());
                    }
                    undirectedGraph.get(userPair).add(time);
                }
            }
        }
    }

    /**
     * Creates a new UDWInteractionGraph from a UDWInteractionGraph object
     * and considering a list of User IDs.
     *
     * @param inputUDWIG a UDWInteractionGraph object
     * @param userFilter a List of User IDs. The created UDWInteractionGraph
     *                   should exclude those emails in the input
     *                   UDWInteractionGraph for which neither the sender
     *                   nor the receiver exist in userFilter.
     */
    public UDWInteractionGraph(UDWInteractionGraph inputUDWIG, List<Integer> userFilter) {
        //get the map for the old graph
        Map<Set<Integer>, List<Integer>> originalMap = inputUDWIG.getGraphMap();

        for (Set<Integer> userPair : originalMap.keySet()) {
            // for each pair of users in the original map
            // if either user is in the userFilter,
            // add their pair and their list to the new graph
            for (Integer user : userPair) {
                if (userFilter.contains(user) && !undirectedGraph.containsKey(userPair)) {
                    undirectedGraph.put(userPair, new ArrayList<>(originalMap.get(userPair)));
                }
            }
        }
    }


    /**
     * Creates a new UDWInteractionGraph from a DWInteractionGraph object.
     *
     * @param inputDWIG a DWInteractionGraph object
     */
    public UDWInteractionGraph(DWInteractionGraph inputDWIG) {
        //get the map for the directed graph
        Map<Integer, LinkedList<Edge>> originalMap = inputDWIG.getGraphMap();

        //loop through the edges in the directed graph
        for(LinkedList<Edge> edges : originalMap.values()){
            for(Edge edge : edges){
                //create a set containing the sender and receiver
                Set<Integer> userPair= new HashSet<>();
                userPair.add(edge.getReceiver());
                userPair.add(edge.getSender());

                //add each interaction to the undirectedGraph
                if(!undirectedGraph.containsKey(userPair)){
                    undirectedGraph.put(userPair, edge.getSendTimes());
                } else{
                    undirectedGraph.get(userPair).addAll(edge.getSendTimes());
                }
            }
        }

    }

    /**
     * @return a Set of Integers, where every element in the set is a User ID
     * in undirectedGraph DWInteractionGraph.
     */
    public Set<Integer> getUserIDs() {
        //create a set of user IDs
        Set<Integer> users = new HashSet<>();

        // loop through each key pair and add the users to user ID set
        for (Set<Integer> key :  undirectedGraph.keySet()){
            users.addAll(key);
        }
        return new HashSet<>(users);
    }

    /**
     * @param sender the User ID of the sender in the email transaction.
     * @param receiver the User ID of the receiver in the email transaction.
     * @return the number of emails sent from the specified sender to the specified
     * receiver in undirectedGraph DWInteractionGraph.
     */
    public int getEmailCount(int sender, int receiver) {
        Set<Integer> users = new HashSet<>();
        int emailCount = 0;

        //create a set of the input sender and receiver
        users.add(sender);
        users.add(receiver);

        //record the number of interactions corresponding to the sender receiver key
        if(undirectedGraph.containsKey(users)){
            emailCount = undirectedGraph.get(users).size();
        }

        return emailCount;
    }

    /**
     *
     * @return an unmodifiable hashmap representing the graph
     */
    public Map<Set<Integer>, List<Integer>> getGraphMap() {
        return Collections.unmodifiableMap(undirectedGraph);
    }

    /* ------- Task 2 ------- */

    /**
     * @param timeWindow is an int array of size 2 [t0, t1]
     *                   where t0<=t1
     *                   t0>=0 && t1>=0
     * @return an int array of length 2, with the following structure:
     *  [NumberOfUsers, NumberOfEmailTransactions]
     */
    public int[] ReportActivityInTimeWindow(int[] timeWindow) {
        Set<Integer> users = new HashSet<>();
        int numEmails = 0;

        //for each user pair in key set loop through all the time stamps
        //if the time stamp is in the required time range, record the users and increment
        //the number of emails
        for(Set<Integer> userPair : undirectedGraph.keySet()){
            for(int time : undirectedGraph.get(userPair)){
                if(time<=timeWindow[1] &&time>= timeWindow[0]){
                    users.addAll(userPair);
                    numEmails++;
                }
            }
        }

        return new int[]{users.size(), numEmails};
    }

    /**
     * @param userID the User ID of the user for which
     *               the report will be created
     * @return an int array of length 2 with the following structure:
     *  [NumberOfEmails, UniqueUsersInteractedWith]
     * If the specified User ID does not exist in undirectedGraph instance of a graph,
     * returns [0, 0].
     */
    public int[] ReportOnUser(int userID) {
        Set<Integer> interactedWith = new HashSet<>();
        int numEmails = 0;

        //loop through each user pair and if the user pair contains the required user
        //record all interactions
        for(Set<Integer> userPair : undirectedGraph.keySet()){
            if(userPair.contains(userID)){
                interactedWith.addAll(userPair);
                numEmails += undirectedGraph.get(userPair).size();
            }
        }

        //remove the original user from the set of users that are interacted with
        interactedWith.remove(userID);

        return new int[]{numEmails, interactedWith.size()};
    }

    /**
     * @param N a positive number representing rank. N=1 means the most active.
     * @return the User ID for the Nth most active user
     *          if N is larger than the number of users, return -1;
     */
    public int NthMostActiveUser(int N) {
        //populate the sorted emails map
        if(sortedUserEmails.isEmpty()) {
            Map<Integer, Integer> userEmails = new HashMap<>();

            //loop through the user pairs
            for (Set<Integer> userPair : undirectedGraph.keySet()) {
                List<Integer> userPairList = userPair.stream().toList();

                //if userEmails does not contain the first user in user pair, add the user pair and the number of emails
                //to userEmails map
                //if userEmails contains the user pair as a key, add the number of emails to the value
                if (!userEmails.containsKey(userPairList.get(0))) {
                    userEmails.put(userPairList.get(0), this.getEmailCount(userPairList.get(0), userPairList.get(1)));
                } else {
                    userEmails.put(userPairList.get(0), userEmails.get(userPairList.get(0)) + this.getEmailCount(userPairList.get(0), userPairList.get(1)));
                }

                //repeat the previous loop for the second user in user pair
                if (!userEmails.containsKey(userPairList.get(1))) {
                    userEmails.put(userPairList.get(1), this.getEmailCount(userPairList.get(0), userPairList.get(1)));
                } else {
                    userEmails.put(userPairList.get(1), userEmails.get(userPairList.get(1)) + this.getEmailCount(userPairList.get(0), userPairList.get(1)));
                }
            }

            //sort map in descending order by value
            userEmails.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(unsorted -> sortedUserEmails.put(unsorted.getKey(), unsorted.getValue()));
        }

        //if there are fewer or equal users to the desired N, return the user ID at desired spot
        if(N<=sortedUserEmails.keySet().size()){
            return sortedUserEmails.keySet().stream().toList().get(N-1);
        }

        //if there are more users than the desired N, return -1
        return -1;
    }

    /* ------- Task 3 ------- */

    /**
     * @return the number of completely disjoint graph
     *    components in the UDWInteractionGraph object.
     */
    public int NumberOfComponents() {

        Set<Integer> allUsers = getUserIDs();
        Set<Integer> visited = new HashSet<>();
        int count = 0;

        //for each user find all related users and mark them as visited
        //for each unvisited user increment count
        for(Integer user : allUsers){
            if(!visited.contains(user)){
                count++;
                allRelatedUsers(user, visited);
            }
        }
        return count;
    }

    private void allRelatedUsers(int user, Set<Integer> visited){
        //if the user is not visited find all related users
        if(!visited.contains(user)){
            visited.add(user);
            //find all related users for each related user of the original user
            //and mark them as visited
            for(Set<Integer> users : undirectedGraph.keySet()){
                if(users.contains(user)) {
                    List<Integer> userPair = users.stream().toList();
                    if(users.size()==1){
                        allRelatedUsers(userPair.get(0), visited);
                    } else {
                        allRelatedUsers(userPair.get(1 - userPair.indexOf(user)), visited);
                    }
                }
            }
        }
    }

    /**
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return whether a path exists between the two users
     */
    public boolean PathExists(int userID1, int userID2) {
        Set<Integer> visited = new HashSet<>();
        boolean[] pathExists = new boolean[]{false};
        //check the path between userID1 and userID2 and update the boolean to be true
        //if it does
        CheckPath(userID1, userID2, visited, pathExists);
        return pathExists[0];
    }

    private void CheckPath(int userID1, int userID2, Set<Integer> visited, boolean[] pathExists) {
        //if userID1 has not yet been visited and the path to userID2 has not been found,
        //find users directly related to userID1
        if (!visited.contains(userID1) && !pathExists[0]) {
            for (Set<Integer> userPair : undirectedGraph.keySet()) {
                if (userPair.contains(userID1)) {
                    List<Integer> userPairList = new ArrayList<>(userPair.stream().toList());
                    //if userID2 is directly related, mark the path as found
                    if (userPair.contains(userID2)) {
                        pathExists[0] = true;
                        break;
                    }
                    //otherwise, check path for each related user
                    else {
                        visited.add(userID1);
                        CheckPath(userPairList.get(1 - userPairList.indexOf(userID1)), userID2, visited, pathExists);
                    }
                }
            }
        }
    }
}
