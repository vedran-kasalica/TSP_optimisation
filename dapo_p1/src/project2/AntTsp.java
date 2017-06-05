package project2;

import java.util.Random;

public class AntTsp {
	private double c = 1.0;
	private double alpha = 1; //pheromone relatively importance
	private double beta = 5; // relative importance of heuristic factor
	private double evaporation = 0.5; // pheromone evaporation coefficient
	private double Q = 500; // amount of pheromone ants released
	private double numAntFactor = 0.2;
	private double pr = 0.01;

	private int maxIterations = 100;

	public int n = 0; // # towns
	public int m = 0; // # ants
	public double graph[][] = null;
	private double trails[][] = null;
	private Ant ants[] = null;
	private Random rand = new Random();
	private double probs[] = null;

	private int currentIndex = 0;

	public int[] bestTour;
	public double bestTourLength = Double.MAX_VALUE;
	public double previousBestTourLength = 0;
	public double difference = bestTourLength - previousBestTourLength;


	public AntTsp(double[][] g) {
		graph = g;
		n = graph.length;
		m = (int) (n * numAntFactor);

		// all memory allocations done here
		trails = new double[n][n];
		probs = new double[n];
		ants = new Ant[m];
		for (int j = 0; j < m; j++)
			ants[j] = new Ant(g);
	}

	public static double pow(final double a, final double b) {
		final int x = (int) (Double.doubleToLongBits(a) >> 32);
		final int y = (int) (b * (x - 1072632447) + 1072632447);
		return Double.longBitsToDouble(((long) y) << 32);
	}

	private void probTo(Ant ant) {
		int i = ant.tour[currentIndex];

		double denom = 0.0;
		for (int l = 0; l < n; l++)
			if (!ant.tabu(l))
				denom += pow(trails[i][l], alpha) * pow(1.0 / graph[i][l], beta);

		for (int j = 0; j < n; j++) {
			if (ant.tabu(j)) {
				probs[j] = 0.0;
			} else {
				double numerator = pow(trails[i][j], alpha) * pow(1.0 / graph[i][j], beta);
				probs[j] = numerator / denom;
			}
		}

	}

	private int selectNextTown(Ant ant) {
		if (rand.nextDouble() < pr) {
			int t = rand.nextInt(n - currentIndex); // random town
			int j = -1;
			for (int i = 0; i < n; i++) {
				if (!ant.tabu(i))
					j++;
				if (j == t)
					return i;
			}

		}
		probTo(ant);
		double r = rand.nextDouble();
		double tot = 0;
		for (int i = 0; i < n; i++) {
			tot += probs[i];
			if (tot >= r)
				return i;
		}
		return n-1;
	}

	private void updateTrails() {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				trails[i][j] *= evaporation;

		for (Ant a : ants) {
			double contribution = Q / a.tourLength();
			for (int i = 0; i < n - 1; i++) {
				trails[a.tour[i]][a.tour[i + 1]] += contribution;
			}
			trails[a.tour[n - 1]][a.tour[0]] += contribution;
		}
	}

	private void moveAnts() {
		// each ant follows trails...
		while (currentIndex < n - 1) {
			for (Ant a : ants)
				a.visitTown(selectNextTown(a),currentIndex);
			currentIndex++;
		}
	}

	private void setupAnts() {
		currentIndex = -1;
		for (int i = 0; i < m; i++) {
			ants[i].clear(); // faster than fresh allocations.
			ants[i].visitTown(rand.nextInt(n),currentIndex);
		}
		currentIndex++;

	}

	private void updateBest() {
		if (bestTour == null) {
			bestTour = ants[0].tour;
			bestTourLength = ants[0].tourLength();
//			previousBestTourLength = 0;
		}
		for (Ant a : ants) {
			if (a.tourLength() < bestTourLength) {
				previousBestTourLength = bestTourLength;
				bestTourLength = a.tourLength();
				//difference = bestTourLength - previousBestTourLength;
				bestTour = a.tour.clone();
				System.out.println("Previous is " + previousBestTourLength + " and current is "+ bestTourLength);
			}
		}
	}

	public static String tourToString(int tour[]) {
		String t = new String();
		for (int i : tour)
			t = t + " " + i;
		return t;
	}

	public int[] solve() {
		// clear trails
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				trails[i][j] = c;

		int iteration = 0;
		while (iteration < maxIterations && Math.abs(difference) >= 0.1) {
			setupAnts();
			moveAnts();
			updateTrails();
			updateBest();
			iteration++;
		}
		System.out.println("Best tour length: " + (bestTourLength));
		System.out.println("Best tour:" + tourToString(bestTour));
		return bestTour.clone();
	}

}
