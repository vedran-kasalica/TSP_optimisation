package dapo_p1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class main {

	static ArrayList<int[]> bestPairs;
	static int pairSum = Integer.MAX_VALUE;
	static double[][] adjMatrix;
	static double[][] MST;

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

	public static ArrayList<Integer> DFS(double adjMatrix[][]) {
		ArrayList<Integer> traversal = new ArrayList<Integer>();
		traversal.add(0);
		boolean[] V = new boolean[adjMatrix[0].length]; // a visited array to
														// mark which vertices
														// have been visited
														// while doing the DFS
		int numComponents = 0; // the number of components in the graph

		// do the DFS from each node not already visited
		for (int i = 0; i < adjMatrix[0].length; ++i)
			if (!V[i]) {
				++numComponents;
				// System.out.printf("Starting a DFS for component %d starting
				// at node %d%n",numComponets,i);
				DFS(i, V, adjMatrix, traversal);
			}

		traversal.add(0);
		return traversal;
	}

	public static void DFS(int at, boolean[] V, double[][] adjMatrix, ArrayList<Integer> traversal) {
		// System.out.printf("At node %d in the DFS%n",at);
		// mark that we are visiting this node
		V[at] = true;

		// recursively visit every node connected to this that we have not
		// already visited
		for (int i = 0; i < adjMatrix[0].length; ++i)
			if (adjMatrix[at][i] != 0 && !V[i]) {
				// System.out.printf("Going to node %d...\n",i);
				traversal.add(i);
				DFS(i, V, adjMatrix, traversal);
			}

	}

	public static int sum(ArrayList<int[]> newPairs) {
		int sum = 0;
		for (int i = 0; i < newPairs.size(); i++) {
			sum += adjMatrix[newPairs.get(i)[0]][newPairs.get(i)[1]];
		}
		return sum;
	}

	public static void generatePairs(int[] array, int[][] pairsCombination, ArrayList<int[]> newPairs,
			Set<Integer> taken) {
		int num = 0;
		while (true) {
			if (num >= array.length) {
				// for (int j = 0; j < newPairs.size(); j++)
				// System.out.print("(" + newPairs.get(j)[0] + "," +
				// newPairs.get(j)[1] + "), ");
				// System.out.println();
				if (sum(newPairs) < pairSum) {
					// System.out.println("Nova suma: "+pairSum);
					pairSum = sum(newPairs);
					bestPairs = new ArrayList<int[]>();
					for (int i = 0; i < newPairs.size(); i++)
						bestPairs.add(newPairs.get(i));
				}
				break;
			}
			if (!taken.contains(array[num])) {
				break;
			}
			num++;
		}
		if (num == array.length)
			return;
		for (int i = 0; i < pairsCombination.length; i++) {
			if ((pairsCombination[i][0] == array[num] && !taken.contains(pairsCombination[i][1]))
					|| (!taken.contains(pairsCombination[i][0]) && pairsCombination[i][1] == array[num])) {
				taken.add(pairsCombination[i][0]);
				taken.add(pairsCombination[i][1]);
				int[] tmp = { pairsCombination[i][0], pairsCombination[i][1] };
				newPairs.add(tmp);
				generatePairs(array, pairsCombination, newPairs, taken);
				taken.remove(pairsCombination[i][0]);
				taken.remove(pairsCombination[i][1]);
				newPairs.remove(tmp);
			}
		}

	}

	public static void main(String[] args) throws IOException {

//		 adjMatrix = readFile("./input/WesternSahara.txt");
		adjMatrix = readFile("./input/Qatar.txt");
		// adjMatrix = readFile("./input/Argentina.txt");
		// adjMatrix = readFile("./input/Greece.txt");
//		 adjMatrix = readFile("./input/Djibouti.txt");
		// adjMatrix = readFile("./input/Kazakhstan.txt");
		// adjMatrix = readFile("./input/Japan.txt");
		MST = Prim.prim(adjMatrix);

		int size = adjMatrix[0].length;

		System.out.println("Select the algorithm\n1. 2-approximation algorithm\n2. 3/2-approximation algorithm");
		Scanner scanner = new Scanner(System.in);
		int number = scanner.nextInt();
		if (number == 2) {
			// list that has indexes of odd nodes and after that -1
			int[] odd = new int[size + 1];
			int curr = 0;
			for (int i = 0; i < size; i++) {
				if (oddSum(MST[i]))
					odd[curr++] = i;
			}
			odd[curr] = -1;

			int end = 0;
			for (int i = 0; i < odd.length - 1; i++) {
				if (odd[i] == -1) {
					end = i;
					break;
				}
			}
			int[] array = new int[end];
			for (int i = 0; i < end; i++) {
				array[i] = odd[i];
			}

			int noPair = array.length * (array.length - 1) / 2;
			int[][] pairs = new int[noPair][2];

			int tmp = 0;
			for (int i = 0; i < array.length - 1; i++) {
				for (int j = i + 1; j < array.length; j++) {
					pairs[tmp][0] = array[i];
					pairs[tmp++][1] = array[j];
				}
			}

			generatePairs(array, pairs, new ArrayList<>(), new HashSet<Integer>());

			// System.out.println("Rezultat za sumu od:" + pairSum);
			for (int j = 0; j < bestPairs.size(); j++) {
				// System.out.print("(" + bestPairs.get(j)[0] + "," +
				// bestPairs.get(j)[1] + "), ");
				MST[bestPairs.get(j)[0]][bestPairs.get(j)[1]] = adjMatrix[bestPairs.get(j)[0]][bestPairs.get(j)[1]];
				MST[bestPairs.get(j)[1]][bestPairs.get(j)[0]] = adjMatrix[bestPairs.get(j)[1]][bestPairs.get(j)[0]];
			}
			System.out.println();
		}
		ArrayList<Integer> traversal = DFS(MST);
		System.out.println();
		double sum = 0;
		for (int i = 0, j = 1; i < adjMatrix[0].length + 1 && j < adjMatrix[0].length + 1;) {
			if (i < j) {
				sum = sum + adjMatrix[traversal.get(i)][traversal.get(j)];
				i = i + 2;
			} else if (j < i) {
				sum = sum + adjMatrix[traversal.get(j)][traversal.get(i)];
				j = j + 2;
			}
		}

		System.out.println("The total cost is: " + sum);

	}


}
