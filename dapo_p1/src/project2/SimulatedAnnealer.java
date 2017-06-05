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
		if (temperature < 0.2)
			return false;
		return (Math.exp((proposal - current) / temperature) < Math.random());
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
	
	public static int[] randomNeighbor(int[] solution) {
		int k = solution.length;
		int[] newSolution = new int[k];
		System.arraycopy(solution, 0, newSolution, 0, k);
		
		int[] ints = new Random().ints(0, k).distinct().limit(2).toArray();
		int rnd1 = ints[0];
		int rnd2 = ints[1];
		int temp = newSolution[rnd1];
		newSolution[rnd1] = newSolution[rnd2];
		newSolution[rnd2] = temp;
		return newSolution;
	}

	public static void main(String[] args) {

		TSPEnvironment tspEnvironment = new TSPEnvironment();

		try {
			tspEnvironment.distances = TSPEnvironment.readFile("./input/WesternSahara.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] currSolution = getRandomArray(tspEnvironment.distances.length);
		double currCost = tspEnvironment.getObjectiveFunctionValue(currSolution);

		int numberOfIterations = 10000;
		double temperature = 500000;
		
		int[] sbest = currSolution;
		double ebest = currCost;
		int k=0;
//		double emax = 10000;
		while (k<numberOfIterations || temperature<10){
			double newtemperature = temperature/(k+1);
			int[] randomNeighbor = randomNeighbor(currSolution);
			double neighborCost = tspEnvironment.getObjectiveFunctionValue(randomNeighbor);
			if (acceptProposal(currCost, neighborCost, newtemperature)){
				currSolution =  randomNeighbor;
				currCost = tspEnvironment.getObjectiveFunctionValue(currSolution);
			}
			if (neighborCost < ebest){
				sbest = randomNeighbor;
				ebest = neighborCost;
			}
//			printSolution(sbest);
            System.out.println("The cost is: " + tspEnvironment.getObjectiveFunctionValue(sbest));
			k++;
			
		}
        System.out.println("The cost of the best solution is:\n" + tspEnvironment.getObjectiveFunctionValue(sbest));
        System.out.println("\nThe solution is:");
		printSolution(sbest);

	}
	
    public static void printSolution(int[] solution) {
        for (int i = 0; i < solution.length; i++) {
            System.out.print(solution[i] + " ");
        }
        System.out.println();
    }

}