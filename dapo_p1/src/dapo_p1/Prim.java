package dapo_p1;

import java.io.IOException;

public class Prim {

	public static double[][] prim(double[][] adjMatrix) throws IOException {

		int size = adjMatrix[0].length;

		int visited[] = new int[size];
		double d[] = new double[size];
		int p[] = new int[size];
		int nodeA, nodeB, weight;
		int current, total;
		double mincost;

		for (int i = 0; i < size; i++) {
			p[i] = visited[i] = -1;
			d[i] = Integer.MAX_VALUE;
		}

		current = 0;
		d[current] = 0;
		total = 1;
		visited[current] = 1;
		while (total != size) {
			for (int i = 0; i < size; i++) {
				if (adjMatrix[current][i] != 0) {
					if (visited[i] == -1) {
						if (d[i] > adjMatrix[current][i]) {
							d[i] = adjMatrix[current][i];
							p[i] = current;
						}
					}
				}
			}
			mincost = Double.POSITIVE_INFINITY;
			for (int i = 0; i < size; i++) {
				if (visited[i] == -1) {
					if (d[i] < mincost) {
						mincost = d[i];
						current = i;
					}
				}
			}
			visited[current] = 1;
			total++;
		}

		mincost = 0;
		for (int i = 0; i < size; i++)
			mincost = mincost + d[i];

		System.out.println("Minimum cost = " + mincost);
//		 System.out.print("\n Minimum Spanning tree is ");
		
//		 for (int i = 0; i < size; i++)
//		 System.out.print("\n vertex " + i + " is connected to " + p[i]);
//		 System.out.println();
		
		double[][] MST = new double[size][size];
		for (int i = 0; i < size; i++) {
			if (p[i] != -1) {
				MST[i][p[i]] = d[i];
				MST[p[i]][i] = d[i];
			}
		}

//		 print MST
//		 for (int i = 0; i < MST[0].length; i++) {
//		 for (int j = 0; j < MST[0].length; j++) {
//		 System.out.printf("%3.1f \t", MST[i][j]);
//		 }
//		 System.out.println();
//		 }
		return MST;
	}
}