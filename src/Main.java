import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Driver function for testing mazes
 * Author: Iden Craven
 */
public class Main {
    public static void main(String[] args) {
        Graph g;
        if (args[0].equals("weighted"))
            g = new Graph(Boolean.TRUE, args[1]);
        else
            g = new Graph(Boolean.FALSE, args[1]);

        JFrame f = new JFrame("MazeVisualizer " +  "--" + args[0]);

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // sample maze from Graph
        MazeVisualizer applet = new MazeVisualizer(g.getN());

        int i = 1;
        for (List<Pair> anAdjListArray : g.getAdjListArray()) {
            for (Pair p : anAdjListArray) {
                applet.addEdge(i, (int) p.getX());
            }
            i++;
        }

        LinkedList<Integer> l;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(args[2])));

            String st;
            String[] tokens;
            while ((st = br.readLine()) != null) {
                tokens = st.split("\\s+");
                l = g.Dijkstras(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
                applet.addPath(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        f.getContentPane().add("Center", applet);
        applet.init();
        f.pack();
        f.setBackground(Color.WHITE);
        f.setSize(new Dimension(512, 512));
        f.setVisible(true);
    }
}
