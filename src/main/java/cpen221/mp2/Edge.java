package cpen221.mp2;

import java.util.ArrayList;
import java.util.List;

public class Edge {
    private int senderId;
    private int receiverId;
    private List<Integer> sendTimes;

    //Rep Invariant:
    //
    //senderID: a non-null, non-negative integer
    //receiverID: a non-null non-negative integer
    //sendTimes: a non-null list of non-null and non-negative integers
    //
    //Abstract Function:
    //
    //represents an edge from a user, senderID, to user, receiverID, with a list of time stamps
    //at which emails were sent
    //

    /**
     * Creates a new edge documenting the emails sent from user with user ID senderID
     * to user with user ID receiver ID and an ArrayList of the timestamps in seconds at
     * which the emails were sent
     *
     * @param senderId the user ID of the email sender
     *                 is a non-null int
     *                 senderId>=0
     * @param receiverId the user ID of the email receiver
     *                 is a non-null int
     *                 senderId>=0
     */

    public Edge(int senderId, int receiverId){
        this.senderId=senderId;
        this.receiverId= receiverId;
        this.sendTimes = new ArrayList<>();
    }
    /**
     * Creates a new edge from a pre-existing edge and a timeInterval, that only includes
     * interactions within the specified time interval
     *
     * @param inputEdge an existing edge
     *
     * @param timeInterval a non-null integer array of size 2
     *                     timeInterval[0]>=0 && timeInterval[1]>=0
     *                     timeInterval[1]>timeInterval[0]
     */
    public Edge(Edge inputEdge, int[] timeInterval) throws RuntimeException { // RuntimeException if no Edge needed
        this.senderId=inputEdge.senderId;
        this.receiverId=inputEdge.receiverId;
        this.sendTimes= new ArrayList<>(inputEdge.getSendTimesInWindow(timeInterval));
        if (this.sendTimes.size() == 0) {
            throw new RuntimeException();
        }
    }

    /**
     * Creates a new edge documenting the emails sent from user with user ID senderID
     * to user with user ID receiver ID and an ArrayList of the times at which the
     * emails were sent
     *
     * @param senderId the user ID of the email sender
     *                 is a non-null int
     *                 senderId>=0
     * @param receiverId the user ID of the email receiver
     *                 is a non-null int
     *                 senderId>=0
     * @param sendTimes a list of time stamps at which emails were sent from sender to receiver
     *                 is a non-null list of Integers
     *                 each element>=0 in seconds
     *
     */

    public Edge(int senderId, int receiverId, List<Integer> sendTimes){
        this.senderId=senderId;
        this.receiverId= receiverId;
        this.sendTimes = sendTimes;
    }


    /**
     * @return a non-null int>=0, corresponding to the user ID of the email sender
     */
    public int getSender(){
        return senderId;
    }

    /**
     * @return a non-null int>=0, corresponding to the user ID of the email receiver
     */
    public int getReceiver(){
        return receiverId;
    }

    /**
     * @return a non-null ArrayList, containing time stamps (in second) of when an
     * email was sent from the sender to the receiver
     */
    public List<Integer> getSendTimes(){
        return new ArrayList<>(sendTimes);
    }

    /**
     * @param sendTime the timestamp (in seconds) an email was sent from sender to receiver
     *                 is a non-null int
     *                 sendTime>=0
     *@effects sendTimes updates the list with the new int sendTime
     */
    public void addSendTime(int sendTime){
        this.sendTimes.add(sendTime);
    }


    /**
     * @param timeWindow int array of length two: [StartTimeInSeconds, EndTimeInSeconds]]
     *                   startTimeInSeconds<EndTimeInSeconds
     * @return List of all timestamps (in seconds) between the start time and end time
     *         specified in @param timeWindow
     *
     */
    public List<Integer> getSendTimesInWindow(int[] timeWindow) {
        /*
         * Note regarding Index Inclusivity / Exclusivity at endpoints:
         *
         * We will be using the arrayList.sublist(int lower, int upper) method that takes
         * in the lower and upper indices to create the sublist from.
         * In this method, the lower index is inclusive, while the upper index is exclusive.
         * This is acknowledged in the implementation
         *
         * Moveover,
         * This Implementation uses Binary Search to achieve O(lg(n)) lookup time.
         * To understand this algorithm better:
         * https://www.youtube.com/watch?v=P3YID7liBug&ab_channel=HackerRank
         * (6-minute YouTube video).
         */
        int startTime = timeWindow[0];
        int stopTime = timeWindow[1];
        int originalNumEmails = sendTimes.size();
        int lowerIdx = originalNumEmails / 2;
        int upperIdx = originalNumEmails / 2;
        int jump = originalNumEmails / 4;
        boolean foundLower = false;
        boolean foundUpper = false;

        //get lower index
        while (!foundLower) {
            // we want: (since inclusive)
            // list[lowerIdx - 1] < startTime <= list[lowerIdx]
            if (originalNumEmails == 1) {
                if (sendTimes.get(0) >= startTime && sendTimes.get(0) <= stopTime) {
                    return new ArrayList<>(sendTimes);
                }
                else {
                    throw new RuntimeException();
                }
            }
            if (jump == 0) {
                break; // break out of infinite loop if can't find any
            }
            if (sendTimes.get(lowerIdx) < startTime) {
                lowerIdx += jump; // if looked too far left, look further right
            }
            if (sendTimes.get(lowerIdx) >= startTime) {
                if (sendTimes.get(lowerIdx - 1) < startTime) {
                    foundLower = true; // if (list[lowerIdx - 1] < startTime <= list[lowerIdx])
                }
                else {
                    lowerIdx -= jump; // if looked too far right, look further left
                }
            }
            jump /= 2;
        }
        jump = sendTimes.size() / 4; // reset jump

        //get upper index
        while (!foundUpper) {
            // we want:(since exclusive) ... list[upperIdx - 1] <= stopTime < list[upperIdx]

            if (jump == 0) {
                break;
            }

            if (sendTimes.get(upperIdx - 1) > stopTime) {
                upperIdx -= jump; // if looked too far right, look more left
            }
            if (sendTimes.get(upperIdx - 1) <= stopTime) {
                if (sendTimes.get(upperIdx) > stopTime) {
                    foundUpper = true; // if (list[upperIdx - 1] <= stopTime < list[upperIdx])
                }
                else {
                    upperIdx += jump; // if looked too far left, look more right
                }
            }
            jump /= 2;
        }

        return new ArrayList<>(sendTimes.subList(lowerIdx, upperIdx));
    }
}
