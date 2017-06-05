package project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

import dapo_p1.Project1;

public class AntColonyOpt {
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
					adjMatrix[i][j] = Integer.MIN_VALUE;
				else
					adjMatrix[i][j] = Math.sqrt(Math.pow(coordinates[i][0] - coordinates[j][0], 2)
							+ Math.pow(coordinates[i][1] - coordinates[j][1], 2));
			}
		}
		scanner.close();
		return adjMatrix;
	}

	public static void main(String[] args) throws IOException {

		AntTsp ant = new AntTsp(Project1.readFile("./input/WesternSahara.txt"));
//		AntTsp ant = new AntTsp(Project1.readFile("./input/Djibouti.txt"));
//		AntTsp ant = new AntTsp(Project1.readFile("./input/Qatar.txt"));
//		AntTsp ant = new AntTsp(readFile("./input/Argentina.txt"));
//		AntTsp ant = new AntTsp(Project1.readFile("./input/Kazakhstan.txt"));
		for (int i = 1; i < 500; i++) {
			ant.solve();
		}

	}

}
