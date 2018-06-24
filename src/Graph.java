import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Creates a graph using an adjacency list given graph in a text file
 * Author: Iden Craven
 */
public class Graph {
    private List<Pair> adjListArray[];
    private int n;

    public Graph(Boolean weighted, String fileName) {
        fillAdjacencyList(weighted, fileName);
    }

    /**
     * Fills adjacency list with pairs that contain a vertex and a weight
     */
    private void fillAdjacencyList(Boolean weighted, String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));

            n = Integer.parseInt(br.readLine());
            adjListArray = new LinkedList[(int) Math.pow(n, 2)];

            makeAdjacencyList();

            String st;
            String[] tokens;
            while ((st = br.readLine()) != null) {
                tokens = st.split("\\s+");
                int src = Integer.parseInt(tokens[0]);
                int dest = Integer.parseInt(tokens[1]);
                int weight;
                if (weighted)
                    weight = Integer.parseInt(tokens[2]);
                else
                    weight = 1;

                Pair<Integer, Integer> p = new Pair<>(dest, weight);
                adjListArray[src - 1].add(p);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates adjacency list with Linked lists of type pair
     */
    private void makeAdjacencyList() {
        for (int i = 0; i < adjListArray.length; i++) {
            adjListArray[i] = new LinkedList<Pair>();
        }
    }

    /**
     * Prints adjacency list
     */
    public void printAdjacencyList() {
        int i = 1;
        for (List<Pair> anAdjListArray : adjListArray) {
            StringBuilder result = new StringBuilder();
            result.append(i).append(" => ");
            for (Pair p : anAdjListArray) {
                result.append(p.getX()).append(" ");
            }
            System.out.println(result);
            i++;
        }
    }

    /**
     * Uses Dijkstra's algorithm to find shortest weighted path from a source vertex to a target vertex
     *
     * @param source Source vertex
     * @param target Target vertex
     * @return Linked list containing shortest path from source to target
     */
    public LinkedList<Integer> Dijkstras(int source, int target) {
        int[] previous = new int[adjListArray.length];  //Array containing previously visited vertex
        Map<Integer, Integer> nodes = new HashMap<>();  //Map with key = node number, and value = min distance
        Map<Integer, Integer> unsettledNodes = new HashMap<>(); //Map that contains nodes that haven't been visited

        //Set distances to "infinity"
        for (int i = 1; i <= adjListArray.length; i++) {
            nodes.put(i, Integer.MAX_VALUE);
        }

        //Distance from source to itself is 0
        nodes.put(source, 0);
        unsettledNodes.put(source, 0);

        while (unsettledNodes.size() != 0) {
            int curr = getLowestDistanceNode(unsettledNodes);   //curr holds key with min distance
            unsettledNodes.remove(curr);        //Mark curr as visited
            //Found target vertex
            if (curr == target)
                break;

            //Visit curr's neighbors
            for (Pair p : adjListArray[curr - 1]) {
                int newDist = nodes.get(curr) + (int) p.getY();

                //New shortest distance
                if (newDist < nodes.get((int) p.getX())) {
                    nodes.put((int) p.getX(), newDist);
                    previous[(int) p.getX() - 1] = curr;
                    unsettledNodes.put((int) p.getX(), newDist);
                }
            }
        }
        return findShortestPath(previous, source, target);
    }

    /**
     * Iterates through previous vertices starting at target vertex to find shortest path
     *
     * @param previous Array of previous vertices
     * @param source   Source vertex
     * @param target   Target vertex
     * @return Linked list containing shortest path from source to target
     */
    private LinkedList<Integer> findShortestPath(int[] previous, int source, int target) {
        LinkedList<Integer> shortestPath = new LinkedList<>();
        shortestPath.add(target);
        try {
            while (previous[target - 1] != source) {
                shortestPath.addFirst(previous[target - 1]);
                target = previous[target - 1];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No path");
            return new LinkedList<>();
        }
        shortestPath.addFirst(source);
        return shortestPath;
    }

    /**
     * Finds key of pair with smallest distance in map
     *
     * @param nodes Map containing vertices and their distances from the source vertex
     * @return Key with smallest distance value
     */
    private int getLowestDistanceNode(Map<Integer, Integer> nodes) {
        int minKey = -1;
        int minValue = Integer.MAX_VALUE;

        for (Map.Entry<Integer, Integer> entry : nodes.entrySet()) {
            if (entry.getValue() < minValue) {
                minValue = entry.getValue();
                minKey = entry.getKey();
            }
        }

        if (minKey != -1)   //No key was found
            return minKey;
        else
            throw new RuntimeException("Couldn't find lowest node");
    }

    public List<Pair>[] getAdjListArray() {
        return adjListArray;
    }

    public int getN() {
        return n;
    }
}
