package dapo_p1;

import java.io.IOException;

public class Prim { 

    public static void prim(double[][] adjMatrix) throws IOException {
        
    	int verticeCount = adjMatrix[0].length;
        
        int visited[] = new int [verticeCount];
        double d[] = new double[verticeCount];
        int p[] = new int[verticeCount];
        int nodeA, nodeB, weight;
        int current, total;
        double mincost;
        

       for (int i = 0; i < verticeCount; i++) {
           p[i] = visited[i] = -1;
           d[i] = 32767;
        } 

        current = 0;
        d[current] = 0;
        total = 1;
        visited[current] = 1;
        while( total != verticeCount) {
            for (int i = 0; i < verticeCount; i++) {
                if ( adjMatrix[current][i] != 0) {
                    if( visited[i] == -1) { 
                        if (d[i] > adjMatrix[current][i]) {
                            d[i] = adjMatrix[current][i];
                            p[i] = current;
                        }
                    }
                }
            }
            mincost=Double.POSITIVE_INFINITY;
            for (int i = 0 ; i < verticeCount; i++) {
                if (visited[i] == -1) {
                    if (d[i] < mincost) {
                        mincost = d[i];
                        current = i;
                    }
                }
            }
            visited[current]=1;
            total++;
        } 

        mincost=0;
        for(int i=0;i<verticeCount;i++)
        mincost=mincost+d[i]; 

        System.out.print("\n Minimum cost = "+mincost);
        System.out.print("\n Minimum Spanning tree is ");

        for(int i=0;i<verticeCount;i++)
        System.out.print("\n vertex " +i+" is connected to "+p[i]);
    }
}