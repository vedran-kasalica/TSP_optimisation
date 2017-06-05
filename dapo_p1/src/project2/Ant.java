package project2;

public class Ant {

	public int tour[] = null;

	public double[][] distances;
	public boolean tabu[] = null;

	public Ant(double[][] g) {
		distances = g;
		tour= new int[distances.length];
		tabu = new boolean[distances.length];
	}

	public void visitTown(int town, int currentIndex) {
		tour[currentIndex + 1] = town;
		tabu[town] = true;
	}

	public boolean tabu(int i) {
		return tabu[i];
	}

	public double tourLength() {
		double length = distances[tour[distances.length - 1]][tour[0]];
		for (int i = 0; i < distances.length - 1; i++) {
			length += distances[tour[i]][tour[i + 1]];
		}
		return length;
	}

	public void clear() {
		for (int i = 0; i < distances.length; i++)
			tabu[i] = false;
	}
}