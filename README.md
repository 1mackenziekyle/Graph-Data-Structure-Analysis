# MP2: Analyzing Email Interactions

## Motivation:

In this mini-project, we aim to develop a system to analyze email interactions of users within organizations. This is to gain insights about usage patterns, discover social interactions within the institution, and understand the impacts of potential vulnerabilities.

## Primary dataset:

Email interactions between 986 users at a research institution for over two years, including sender user ID, receiver user ID, and send time of 332,334 emails. The data is provided in a single file in “txt” format.  
Link to the open-source data: https://snap.stanford.edu/data/email-Eu-core-temporal.html  
Effectively, you are given the data for a directed temporal weighted graph in the `resources` folder.

## Task 1: Constructing the Interaction Graph and Collecting Basic Metrics

A graph is a suitable representation of the interaction between different users. However, depending on which question we want to answer, different graph representations might be more suitable.

### Task1.1:

Implement a directed weighted graph as a rich form oc capturing email interactions. The number of emails between any users A and B must be captured, as well as email times. This must be able to be constructed in either of the following ways:

1.           Using an **input file** (similar in format to the one provided to you) and a **time window** (int array of length two: ```[StartTimeInSeconds, EndTimeInSeconds]```). Only email interactions that occurred in this window should be stored in the object, where the time window is inclusive. If no time window is provided, all email interactions in the file should be included.
2.           From another ```InteractionGraph``` object and a **time range filter** (int array of length two: ```[StartTimeInSeconds, EndTimeInSeconds]```) Note that the time window for the resulting object would be the intersection of input time range filter and the input object’s.
3.           From another ```InteractionGraph``` object and an int array containing **user IDs**. The returning ```InteractionGraph``` should only contain interactions of these users. Note that not all requested users are guaranteed to be in the input ```InteractionGraph``` object. Interactions should be added to the new graph as long as either the sender or the reciver is in the specified array, or both.

### Task 1.2:

Create an undirected weighted graph of user interactions within a specific time. Assuming two users A and B, undirected means emails from A to B are not differentiated from those sent from B to A, and weighted means that the total number of emails between A to B should be captured. This class should also capture email times, as certain analyses would rely on it. An object of this class should be constructed in one of the following ways:

1.           Using an **input file** (similar in format to the one provided to you) and a **time window** (int array of length two: ```[StartTimeInSeconds, EndTimeInSeconds]```).
2.           From another ```UDWInteractionGraph``` object and a **time range filter** (int array of length two: ```[StartTimeInSeconds, EndTimeInSeconds]```) Again, this time window should be checked to fit in the time window associated with the input object, where the time range is inclusive.
3.           From another ```UDWInteractionGraph``` object and an int array containing **user IDs**. The returning ```UDWInteractionGraph``` should only contain interactions of these users. Interactions should be added to the new graph as long as either the sender or the reciver is in the specified array, or both.
4.           From an ```InteractionGraph```. Here, the ```InteractionGraph``` should be translated into a ```UDWInteractionGraph```.

**Note 1**: Part of solving this task is deciding a suitable data structure for holding data of a `DWInteractionGraph` or a `UDWInteractionGraph`. There are two common ways to represent a graph: using an [adjacency matrix](https://en.wikipedia.org/wiki/Adjacency_matrix), or an [adjacency list](https://en.wikipedia.org/wiki/Adjacency_list). Depending on the graph, one could be better than the other in terms of speed or memory usage. Your ability to finish Task 4 and pass tests in a reasonable time may depend on choosing the representation better suited to this problem.

## Task 2: Reporting Basic Metrics

The `DWInteractionGraph` class should implement the following public methods:

| Method Name                | Parameters                                                                                            | Returns                                                                                                                                                                   |
| -------------------------- | ----------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| ReportActivityInTimeWindow | Time window in seconds: `<int, int>`<br> Time window is inclusive on both boundaries.                 | [NumberOfSenders,<br />NumberOfReceivers,<br />NumberOfEmailTransactions]:<br />`<int, int, int>`                                                                         |
| ReportOnUser               | User ID: `<int>`                                                                                      | [NumberOfEmailsSent,<br />NumberOfEmailsReceived,<br />UniqueUsersInteractedWith]:<br />`<int, int, int>`<br>If the User ID cannot be found in the graph, returns [0,0,0] |
| NthMostActiveUser          | N: `<int>`,<br />interactionType: `<SendOrReceive>` (‘SendOrReceive.SEND’ or ‘SendOrReceive.RECEIVE’) | User ID: `<int>`<br>If two or more User IDs send or recieve the same number of emails, returns the smallest User ID in the most active set.                               |

The `UDWInteractionGraph` class should implement the following public methods:

| Method Name                | Parameters                                | Returns                                                             |
| -------------------------- | ----------------------------------------- | ------------------------------------------------------------------- |
| ReportActivityInTimeWindow | Time window in seconds:<br />`<int, int>` | [NumberOfUsers,<br />NumberOfEmailTransactions]:<br />`<int, int>`  |
| ReportOnUser               | User ID: `<int>`                          | [NumberOfEmails,<br />UniqueUsersInteractedWith]:<br />`<int, int>` |
| NthMostActiveUser          | N: `<int>`                                | User ID: `<int>`                                                    |

## Task 3: Reporting More Advanced Metrics

The `DWInteractionGraph` class should implement the following public methods:

| Method Name | Parameters                                | Returns                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| ----------- | ----------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| BFS         | User 1 ID `<int>`,<br />User 2 ID `<int>` | Returns: `List <Integer>`<br />Using the breadth first search (BFS) algorithm, find whether there exists a path between User 1 and User 2. If such a path exists, this method should return the list of User IDs in the order visited. If no such path exists, the method should return null. When choosing the next adjacent node to vist, choose the node with the smallest User ID first that has not yet been visited.<br />More on BFS: https://en.wikipedia.org/wiki/Breadth-first_search |
| DFS         | User 1 ID `<int>`,<br />User 2 ID `<int>` | Returns: `List <Integer>`<br />Using the depth first search (DFS) algorithm, find whether there exists a path between User 1 and User 2. If such a path exists, this method should return the list of User IDs in the order visited. If no such path exists, the method should return null. When choosing the next adjacent node to vist, choose the node with the smallest User ID first that has not yet been visited.<br />More on BFS: https://en.wikipedia.org/wiki/Breadth-first_search   |

The `UDWInteractionGraph` should implement the following public methods:

| Method Name        | Parameters                                | Returns                                                                                                                                         |
| ------------------ | ----------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------- |
| NumberOfComponents | No input parameter                        | Number of components of the graph: `<int>`<br />Description: Number of completely disjoint graph components.<br />Examples: <br />              |
|                    |                                           | 1. Email interaction history of [[A,B,0],[A,C,3],[D,B,4]] ⇒ the interaction graph has only one component. ![E.g.1](images/eg1.png)              |
|                    |                                           | 2. Email interaction history of [[A,B,0],[C,D,1]] ⇒ the interaction graph has two components. ![E.g.2](images/eg2.png)                          |
|                    |                                           | 3. Email interaction history of [[A,A,0],[A,A,1]] ⇒ the interaction graph has one component. ![E.g.3](images/eg3.png)                           |
| PathExists         | User 1 ID `<int>`,<br />User 2 ID `<int>` | Whether a path exists between the two users: `<bool>`<br />(Hint: you can use breadth first search (BFS) or depth first search (DFS) for this.) |
