package project2;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

		int[] bestSol = new int[initSolution.length];
		System.arraycopy(initSolution, 0, bestSol, 0, bestSol.length);
		int initCost = tspEnviromnet.getObjectiveFunctionValue(initSolution);
		int bestCost = initCost;
		int city1 = 0;
		int city2 = 0;
		boolean firstNeighbor = true;
		// =i+Math.max(1,(int)(initSolution.length*(Math.random()*0.1+0.1)))
		for (int i = 1; i < initSolution.length - 2; i+=Math.max(1,(int)(initSolution.length*(Math.random()*0.1+0.1)))) {
			for (int j = i + 1; j < initSolution.length - 1; j+=Math.max(1,(int)(initSolution.length*(Math.random()*0.1+0.1)))) {

				int[] newBestSol = new int[initSolution.length];

				System.arraycopy(initSolution, 0, newBestSol, 0, newBestSol.length);

				swapOperator(i, j, newBestSol);
				// , maybe we get a bettersolution
				int newBestCost = tspEnviromnet.getObjectiveFunctionValueForChange(initSolution, i, j, initCost);

				if ((newBestCost < bestCost || firstNeighbor) && tabuList.tabuList[i][j] == 0) {
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
//			tspEnvironment.distances = TSPEnvironment.readFile("./input/WesternSahara.txt");
//			tspEnvironment.distances = TSPEnvironment.readFile("./input/Djibouti.txt");
//			tspEnvironment.distances = TSPEnvironment.readFile("./input/Qatar.txt");
//			tspEnvironment.distances = TSPEnvironment.readFile("./input/Argentina.txt");
//			tspEnvironment.distances = TSPEnvironment.readFile("./input/Kazakhstan.txt");
			tspEnvironment.distances = TSPEnvironment.readFile("./input/Greece.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// initial solution
		int[] currSolution = getRandomArray(tspEnvironment.distances.length);
//		int[] currSolution = {21,22,28,20,18,14,17,16,27,25,19,24,26,23,15,13,12,11,10,9,7,8,6,2,3,4,0,1,5};
		int numberOfIterations = 1000;
		int tabuLength = tspEnvironment.distances.length;
		TabuList tabuList = new TabuList(tabuLength);
		int[] bestSol = new int[currSolution.length]; // this is the best
														// Solution So Far
		System.arraycopy(currSolution, 0, bestSol, 0, bestSol.length);

		System.out.println(" Initial cost: " + tspEnvironment.getObjectiveFunctionValue(currSolution));

		int bestCost = tspEnvironment.getObjectiveFunctionValue(bestSol);
		// perform iterations here
		for (int i = 0; i < numberOfIterations; i++) {
			int currCost = TabuSearch.getBestNeighbour(tabuList, tspEnvironment, currSolution);
			// printSolution(currSolution);
			System.out.println("Current best cost = " + currCost);

			if (currCost < bestCost) {
				System.arraycopy(currSolution, 0, bestSol, 0, bestSol.length);
				bestCost = currCost;
			}
		}

		System.out.println("Search done! \nBest Solution cost found = " + bestCost + "\nBest Solution :");

		printSolution(bestSol);
	}

	public static void printSolution(int[] solution) {
		for (int i = 0; i < solution.length; i++) {
			System.out.print(solution[i] + " ");
		}
		System.out.println();
	}
}
