package dapo_p1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


public class main {
	

	public static double[][] readFile(String file) throws FileNotFoundException {
		File reader = new File(file);
		Scanner scanner = new Scanner(reader);
		Scanner input = scanner.useLocale(Locale.US);

		// skipping the first 3 lines
		input.nextLine();
		input.nextLine();
		input.nextLine();
		int size = input.nextInt();
		double[][] adjMatrix = new double[size][size];

		double[][] coordinates = new double[size][2];
		for (int i = 0; i < size; i++) {
			input.nextInt();
			coordinates[i][0] = input.nextDouble();
			coordinates[i][1] = input.nextDouble();
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == j)
					adjMatrix[i][j] = 0;
				else
					adjMatrix[i][j] = Math.sqrt(Math.pow(coordinates[i][0] - coordinates[j][0], 2)
							+ Math.pow(coordinates[i][1] - coordinates[j][1], 2));
			}
		}
		// print matrix
		// for (int i = 0; i < adjMatrix[0].length; i++) {
		// for (int j = 0; j < adjMatrix[0].length; j++) {
		// System.out.printf("%3.1f \t",adjMatrix[i][j]);
		// }
		// System.out.println();
		// }
		scanner.close();
		return adjMatrix;
	}

	public static boolean oddSum(double[] MST) {
		Boolean s = false;
		for (int i = 0; i < MST.length; i++)
			if (MST[i] > 0)
				s = !s;
		return s;
	}
	
	public static ArrayList<Integer> DFS(double adjMatrix[][])
	{
		ArrayList<Integer> traversal = new ArrayList<Integer>();
		traversal.add(0);
		boolean[] V=new boolean[adjMatrix[0].length]; // a visited array to mark which vertices have been visited while doing the DFS
		int numComponents=0; // the number of components in the graph
		
		// do the DFS from each node not already visited
		for (int i=0; i<adjMatrix[0].length; ++i)
			if (!V[i])
			{
				++numComponents;
//				System.out.printf("Starting a DFS for component %d starting at node %d%n",numComponets,i);
				DFS(i,V,adjMatrix,traversal);
			}
		
		traversal.add(0);
		return traversal;
	}
	
	public static void DFS(int at, boolean[] V, double[][] adjMatrix, ArrayList<Integer> traversal)
	{
//		System.out.printf("At node %d in the DFS%n",at);
		// mark that we are visiting this node
		V[at]=true;
		
		// recursively visit every node connected to this that we have not already visited
		for (int i=0; i<adjMatrix[0].length; ++i)
			if (adjMatrix[at][i]!=0 && !V[i])
			{
//				System.out.printf("Going to node %d...\n",i);
					traversal.add(i);
				DFS(i,V,adjMatrix,traversal);
			}
	}

	/*public static void main(String[] args) throws IOException {

		double[][] adjMatrix = readFile("./input/1.txt");

		double[][] MST = Prim.prim(adjMatrix);

		int size = adjMatrix[0].length;

		int[] odd = new int[size];
		int curr = 0;
		for (int i = 0; i < size; i++) {
			if (oddSum(MST[i]))
				odd[curr++] = i;
		}

	}*/
	
	public static void main(String[] args) throws IOException {

//		double[][] adjMatrix = readFile("./input/Argentina.txt");
//		double[][] adjMatrix = readFile("./input/Greece.txt");
//		double[][] adjMatrix = readFile("./input/Djibouti.txt");
//		double[][] adjMatrix = readFile("./input/Kazakhstan.txt");
		double[][] adjMatrix = readFile("./input/Japan.txt");

		double[][] MST = Prim.prim(adjMatrix);

		int size = adjMatrix[0].length;
		
		System.out.println("The BFS traversal of the graph is ");
		ArrayList<Integer> traversal = DFS(MST);
		for (int i = 0; i < adjMatrix[0].length +1; i++) {
			System.out.println(traversal.get(i) + "  ");
		}
		System.out.println();
		double sum=0;
		for (int i = 0,j=1; i < adjMatrix[0].length+1 && j < adjMatrix[0].length+1;) {
			if (i<j) {
				sum = sum + adjMatrix[traversal.get(i)][traversal.get(j)];
//				System.out.println("We added the cost of the edge(" + traversal1.get(i) + " " + traversal1.get(j) + "), that actually cost: "+adjMatrix[traversal1.get(i)][traversal1.get(j)]);
				i=i+2;
			}
			else if (j<i){
				sum = sum + adjMatrix[traversal.get(j)][traversal.get(i)];
//				System.out.println("We added the cost of " + traversal1.get(j) + " " + traversal1.get(i) + " that actually cost: "+adjMatrix[traversal1.get(j)][traversal1.get(i)]);
				j=j+2;
			}
			
//			System.out.println("The sum is: " + sum);
//			System.out.println("Traversal: "+traversal1.get(i));
		}
		
		System.out.println("The total cost is: " + sum);
	}

}
