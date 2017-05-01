package dapo_p1;

import java.util.*; // For ArrayDeque, HashMap, HashSet, LinkedList

public final class test {

	static ArrayList<int[]> bestPairs;
	static int pairSum = 0;

	/**
	 * Given an undirected graph, returns a graph containing the edges of a
	 * maximum matching in that graph.
	 *
	 * @param g
	 *            The graph in which a maximum matching should be found.
	 * @return A graph containing a maximum matching in that graph.
	 */

	public static int sum(ArrayList<int[]> newPairs) {
		int sum = 0;
		for (int i = 0; i < newPairs.size(); i++) {
			sum += newPairs.get(i)[0] + 10 * newPairs.get(i)[1];
		}
		return sum;
	}

	public static void generatePairs(int[] array, int[][] pairsCombination, ArrayList<int[]> newPairs,
			Set<Integer> taken) {
		int num = 0;
		while (true) {
			if (num >= array.length) {
				for (int j = 0; j < newPairs.size(); j++)
					System.out.print("(" + newPairs.get(j)[0] + "," + newPairs.get(j)[1] + "), ");
				System.out.println();
				if (sum(newPairs) > pairSum) {
					pairSum = sum(newPairs);
					bestPairs = new ArrayList<int[]>();
					for (int i = 0; i < newPairs.size(); i++)
						bestPairs.add(newPairs.get(i));
				}
				break;
			}
			if (!taken.contains(array[num])) {
				break;
			}
			num++;
		}
		if (num == array.length)
			return;
		for (int i = 0; i < pairsCombination.length; i++) {
			if ((pairsCombination[i][0] == array[num] && !taken.contains(pairsCombination[i][1]))
					|| (!taken.contains(pairsCombination[i][0]) && pairsCombination[i][1] == array[num])) {
				taken.add(pairsCombination[i][0]);
				taken.add(pairsCombination[i][1]);
				int[] tmp = { pairsCombination[i][0], pairsCombination[i][1] };
				newPairs.add(tmp);
				generatePairs(array, pairsCombination, newPairs, taken);
				taken.remove(pairsCombination[i][0]);
				taken.remove(pairsCombination[i][1]);
				newPairs.remove(tmp);
			}
		}

	}

	public static void main(String[] args) {

		int[] x = { 1, 3, 5, 8, -1, 0, 0 };
		int end = 0;
		for (int i = 0; i < x.length - 1; i++) {
			if (x[i] == -1) {
				end = i;
				break;
			}
		}
		int[] array = new int[end];
		for (int i = 0; i < end; i++) {
			array[i] = x[i];
		}

		int noPair = array.length * (array.length - 1) / 2;
		int[][] pairs = new int[noPair][2];

		int tmp = 0;
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = i + 1; j < array.length; j++) {
				pairs[tmp][0] = array[i];
				pairs[tmp++][1] = array[j];
			}
		}
		for (int i = 0; i < pairs.length; i++)
			System.out.println(pairs[i][0] + " " + pairs[i][1]);

		generatePairs(array, pairs, new ArrayList<>(), new HashSet<Integer>());

		System.out.println("Rezultat za sumu od:" + pairSum);
		for (int j = 0; j < bestPairs.size(); j++)
			System.out.print("(" + bestPairs.get(j)[0] + "," + bestPairs.get(j)[1] + "), ");
		System.out.println();

	}

}