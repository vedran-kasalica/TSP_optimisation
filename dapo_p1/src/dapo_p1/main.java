package dapo_p1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class main {

	public static double[][] readFile(String file) throws FileNotFoundException {
		File reader = new File(file);
		Scanner input = new Scanner(reader);
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

		return adjMatrix;

	}

	public static boolean oddSum(double[] MST) {
		Boolean s = false;
		for (int i = 0; i < MST.length; i++)
			if (MST[i] > 0)
				s = !s;
		return s;
	}

	public static void main(String[] args) throws IOException {

		double[][] adjMatrix = readFile("./input/1.txt");

		double[][] MST = Prim.prim(adjMatrix);

		int size = adjMatrix[0].length;

//		list that has indexes of odd nodes and after that -1
		int[] odd = new int[size+1];
		int curr = 0;
		for (int i = 0; i < size; i++) {
			if (oddSum(MST[i]))
				odd[curr++] = i;
		}
		odd[curr]=-1;

	}

}
