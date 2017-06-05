package project2;

import java.awt.print.Printable;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import dapo_p1.Project1;

public class TabuSearch {

	static int[] getRandomArray(int n) {
		int[] ar = new int[n];
		for (int i = 0; i < n; i++)
			ar[i] = i;
		Random rnd = ThreadLocalRandom.current();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
		return ar;
	}

	public static int getBestNeighbour(TabuList tabuList, TSPEnvironment tspEnviromnet, int[] initSolution) {

		int[] bestSol = new int[initSolution.length]; // this is the best
														// Solution So Far
		System.arraycopy(initSolution, 0, bestSol, 0, bestSol.length);
		int bestCost = tspEnviromnet.getObjectiveFunctionValue(initSolution);
		int city1 = 0;
		int city2 = 0;
		boolean firstNeighbor = true;

		for (int i = 1; i < initSolution.length - 2; i=i+Math.max(1,(int)(initSolution.length*Math.random()*0.5+0.5))) {
			for (int j = i+1; j < initSolution.length - 1; j=j+Math.max(1,(int)(initSolution.length*Math.random()*0.5+0.5))) {

				int[] newBestSol = new int[initSolution.length]; 
															
				System.arraycopy(initSolution, 0, newBestSol, 0, newBestSol.length);

				swapOperator(i, j, newBestSol); // Try swapping
																// cities i and
																// j
				// , maybe we get a bettersolution
				int newBestCost = tspEnviromnet.getObjectiveFunctionValue(newBestSol);

				if ((newBestCost < bestCost || firstNeighbor) && tabuList.tabuList[i][j] == 0) { // if
																									// better
																									// move
																									// found,
																									// store
																									// it
					firstNeighbor = false;
					city1 = i;
					city2 = j;
					System.arraycopy(newBestSol, 0, bestSol, 0, newBestSol.length);
					bestCost = newBestCost;
				}

			}
		}

		if (city1 != 0) {
			tabuList.decrementTabu();
			tabuList.tabuMove(city1, city2);
		}
		System.arraycopy(bestSol, 0, initSolution, 0, bestSol.length);
		return bestCost;

	}

	// swaps two cities
	public static void swapOperator(int city1, int city2, int[] solution) {
		int temp = solution[city1];
		solution[city1] = solution[city2];
		solution[city2] = temp;
	}

	public static void main(String[] args) {

		TSPEnvironment tspEnvironment = new TSPEnvironment();

		try {
			tspEnvironment.distances = TSPEnvironment.readFile("./input/Greece.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		print(tspEnvironment.distances);
		// initial solution
		int[] currSolution = getRandomArray(tspEnvironment.distances.length);

		int numberOfIterations = 10000;
		int tabuLength = tspEnvironment.distances.length;
		TabuList tabuList = new TabuList(tabuLength);
		int[] bestSol = new int[currSolution.length]; // this is the best
														// Solution So Far
		System.arraycopy(currSolution, 0, bestSol, 0, bestSol.length);
		
		System.out.println(" ___ " + tspEnvironment.getObjectiveFunctionValue(currSolution));

		int bestCost = tspEnvironment.getObjectiveFunctionValue(bestSol);
		// perform iterations here
		for (int i = 0; i < numberOfIterations; i++) {
			int currCost = TabuSearch.getBestNeighbour(tabuList, tspEnvironment, currSolution);
			// printSolution(currSolution);

			System.out.println(
					"Current best cost = " + currCost);

			if (currCost < bestCost) {
				System.arraycopy(currSolution, 0, bestSol, 0, bestSol.length);
				bestCost = currCost;
			}
		}

		System.out.println("Search done! \nBest Solution cost found = " + bestCost + "\nBest Solution :");

		printSolution(bestSol);

	}

	private static void print(int[][] distances) {
		for (int i = 0; i < distances.length; i++) {
			for (int j = 0; j < distances[0].length; j++) {
				System.out.printf("%3d \t", distances[i][j]);
			}
			System.out.println();
		}

	}

	public static void printSolution(int[] solution) {
		for (int i = 0; i < solution.length; i++) {
			System.out.print(solution[i] + " ");
		}
		System.out.println();
	}
}
