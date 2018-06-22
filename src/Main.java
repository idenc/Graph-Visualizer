import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph g = new Graph();

        JFrame f = new JFrame("MazeVisualizer");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // sample maze from Graph
        MazeVisualizer applet = new MazeVisualizer(4);

        int i = 1;
        for (List<Pair> anAdjListArray : g.getAdjListArray()) {
            for (Pair p : anAdjListArray) {
                applet.addEdge(i, (int) p.getX());
            }
            i++;
        }

        f.getContentPane().add("Center", applet);
        applet.init();
        f.pack();
        f.setBackground(Color.WHITE);
        f.setSize(new Dimension(512, 512));
        f.setVisible(true);
    }
}
