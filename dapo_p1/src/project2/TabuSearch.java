package project2;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import dapo_p1.Project1;

public class TabuSearch {

	static int[] getRandomArray(int n)
	  {
		int[] ar=new  int[n];
		for(int i=0;i<n;i++)
			ar[i]=i;
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	    return ar;
	  }
	
    public static int[] getBestNeighbour(TabuList tabuList,
            TSPEnvironment tspEnviromnet,
            int[] initSolution) {


        int[] bestSol = new int[initSolution.length]; //this is the best Solution So Far
        System.arraycopy(initSolution, 0, bestSol, 0, bestSol.length);
        int bestCost = tspEnviromnet.getObjectiveFunctionValue(initSolution);
        int city1 = 0;
        int city2 = 0;
        boolean firstNeighbor = true;

        for (int i = 1; i < bestSol.length - 1; i++) {
            for (int j = 2; j < bestSol.length - 1; j++) {
                if (i == j) {
                    continue;
                }

                int[] newBestSol = new int[bestSol.length]; //this is the best Solution So Far
                System.arraycopy(bestSol, 0, newBestSol, 0, newBestSol.length);

                newBestSol = swapOperator(i, j, initSolution); //Try swapping cities i and j
                // , maybe we get a bettersolution
                int newBestCost = tspEnviromnet.getObjectiveFunctionValue(newBestSol);



                if ((newBestCost > bestCost || firstNeighbor) && tabuList.tabuList[i][j] == 0) { //if better move found, store it
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
        return bestSol;


    }

    //swaps two cities
    public static int[] swapOperator(int city1, int city2, int[] solution) {
        int temp = solution[city1];
        solution[city1] = solution[city2];
        solution[city2] = temp;
        return solution;
    }

    public static void main(String[] args) {

        TSPEnvironment tspEnvironment = new TSPEnvironment();

        try {
			tspEnvironment.distances =  Project1.readFile("./input/WesternSahara.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //Between cities. 0,1 represents distance between cities 0 and 1, and so on.
        
        int[] currSolution =getRandomArray(tspEnvironment.distances.length);   //initial solution
        printSolution(currSolution);
        //city numbers start from 0
        // the first and last cities' positions do not change

        int numberOfIterations = 100;
        int tabuLength = 10;
        TabuList tabuList = new TabuList(tabuLength);

        int[] bestSol = new int[currSolution.length]; //this is the best Solution So Far
        System.arraycopy(currSolution, 0, bestSol, 0, bestSol.length);
        int bestCost = tspEnvironment.getObjectiveFunctionValue(bestSol);

        for (int i = 0; i < numberOfIterations; i++) { // perform iterations here

            currSolution = TabuSearch.getBestNeighbour(tabuList, tspEnvironment, currSolution);
            printSolution(currSolution);
            int currCost = tspEnvironment.getObjectiveFunctionValue(currSolution);

            System.out.println("Current best cost = " + tspEnvironment.getObjectiveFunctionValue(currSolution));

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
