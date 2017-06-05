package project2;

import java.io.IOException;

public class AntColonyOpt {

	// Load graph file given on args[0].
    // (Full adjacency matrix. Columns separated by spaces, rows by newlines.)
    // Solve the TSP repeatedly for maxIterations
    // printing best tour so far each time. 
    public static void main(String[] args) {
       
        AntTsp ant = new AntTsp();
//        ant.graph=

        // Repeatedly solve - will keep the best tour found.
        while(true) {
            ant.solve();
        }

    }

}
