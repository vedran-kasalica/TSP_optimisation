package project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class TSPEnvironment { //Tabu Search Environment
    
    public int[][] distances;
    
    public TSPEnvironment(){
        
    }
    
    public int getObjectiveFunctionValue(int solution[]){ //returns the path cost
        int cost = 0;
   
        for(int i = 0 ; i < solution.length-1; i++){
            cost+= distances[solution[i]][solution[i+1]];
        }
   
        return cost;
        
    }
    
    public int getObjectiveFunctionValueForChange(int solution[], int a1, int a2, int cost){ //returns the path cost
    	if(a1+1==a2){
    		 cost -=distances[solution[a1]][solution[a1-1]];
    	     cost +=distances[solution[a2]][solution[a1-1]];
    	     cost -=distances[solution[a2]][solution[a2+1]];
    	     cost +=distances[solution[a1]][solution[a2+1]];
    	}else{
    	cost -=distances[solution[a1]][solution[a1+1]];
        cost +=distances[solution[a2]][solution[a1+1]];
        cost -=distances[solution[a1]][solution[a1-1]];
        cost +=distances[solution[a2]][solution[a1-1]];
   
        cost -=distances[solution[a2]][solution[a2+1]];
        cost +=distances[solution[a1]][solution[a2+1]];
        cost -=distances[solution[a2]][solution[a2-1]];
        cost +=distances[solution[a1]][solution[a2-1]];
    	}
        return cost;
        
    }
    
    
    public static int[][] readFile(String file) throws FileNotFoundException {
		File reader = new File(file);
		Scanner scanner = new Scanner(reader);
		Scanner input = scanner.useLocale(Locale.US);

		// skipping the first 3 lines
		input.nextLine();
		input.nextLine();
		input.nextLine();
		int size = input.nextInt();
		int[][] adjMatrix = new int[size][size];

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
					adjMatrix[i][j] = (int)(Math.sqrt(Math.pow(coordinates[i][0] - coordinates[j][0], 2)
							+ Math.pow(coordinates[i][1] - coordinates[j][1], 2)));
			}
		}
		scanner.close();
		return adjMatrix;
	}
    
   

}

