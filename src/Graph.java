import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    private List<Pair> adjListArray[];

    public Graph() {
        fillAdjacencyList();
    }

    /**
     * Fills adjacency list with pairs that contain a vertex and a weight
     */
    private void fillAdjacencyList() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("maze.txt")));

            int n = Integer.parseInt(br.readLine());
            adjListArray = new LinkedList[(int) Math.pow(n, 2)];

            makeAdjacencyList();

            String st;
            String[] tokens;
            while ((st = br.readLine()) != null) {
                tokens = st.split("\\s+");
                int src = Integer.parseInt(tokens[0]);
                int dest = Integer.parseInt(tokens[1]);
                int weight = Integer.parseInt(tokens[2]);

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

    public List<Pair>[] getAdjListArray() {
        return adjListArray;
    }
}
