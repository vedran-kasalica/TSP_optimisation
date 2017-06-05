package project2;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import dapo_p1.Project1;

public class SimulatedAnnealer {

	static boolean acceptProposal(double current, double proposal, double temperature) {
		double prob;
		if (proposal < current)
			return true;
		if (temperature == 0.0)
			return false;
		prob = Math.exp(-(proposal - current) / temperature);
		return Math.random() < prob;
	}

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

	public static void main(String[] args) {

		TSPEnvironment tspEnvironment = new TSPEnvironment();

		try {
			tspEnvironment.distances = Project1.readFile("./input/Argentina.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] currSolution = getRandomArray(tspEnvironment.distances.length);
		double currCost = tspEnvironment.getObjectiveFunctionValue(currSolution);
		int numberOfIterations = 1000;
		double temperature = Double.MAX_VALUE;
		for (int i = 0; i < numberOfIterations; i++) {
			temperature -= (i + 1) / numberOfIterations;
			int[] randomNeighbor = getRandomArray(tspEnvironment.distances.length);
			double neighborCost = tspEnvironment.getObjectiveFunctionValue(currSolution);
			if (acceptProposal(currCost, neighborCost, temperature)) {
				currSolution = randomNeighbor;
			}
            printSolution(currSolution);
            System.out.println("The cost is: " + tspEnvironment.getObjectiveFunctionValue(currSolution));
		}

	}
	
    public static void printSolution(int[] solution) {
        for (int i = 0; i < solution.length; i++) {
            System.out.print(solution[i] + " ");
        }
        System.out.println();
    }

}