import java.util.HashSet;
import java.util.Scanner;

public class SnakesandLaddersTheQuickestWayUp {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = Integer.parseInt(in.nextLine());
		int nGrids = 100;
		int nMaxDie = 6;
		for (int t = 0; t < T; t++) {
			Graph G = new Graph(nGrids + 1);
			String str[] = in.nextLine().split(",");
			int nLadders = Integer.parseInt(str[0]);
			int nSnakes = Integer.parseInt(str[1]);
			String[] Ladders = in.nextLine().split(" ");
			String[] Snakes = in.nextLine().split(" ");
			// add the ladders to the directed graph
			for (int l = 0; l < nLadders; l++) {
				G.addEdge(Integer.parseInt(Ladders[l].split(",")[0]),
						Integer.parseInt(Ladders[l].split(",")[1]));
			}
			// add the snakes to the directed graph
			for (int s = 0; s < nSnakes; s++) {
				G.addEdge(Integer.parseInt(Snakes[s].split(",")[0]),
						Integer.parseInt(Snakes[s].split(",")[1]));
			}
			for (int v = 1; v < G.V(); v++) {
				for (int w = 1; w <= nMaxDie; w++) {
					if (v + w < G.V())
						G.addEdge(v, v + w);
				}
			}
			System.out.println(Search.getMinDistance(G, 1));
		}
	}

	public static class Search {
		private static int[] distanceTo;// the array that stores the minimum
										// amount of steps need to reach that
										// index (ignore 0-index,from 1-100)

		public static int getMinDistance(Graph G, int s) {
			distanceTo = new int[G.V()];
			// calculate min values considering the ladders
			for (int v = s + 1; v < distanceTo.length; v++) {
				distanceTo[v] = LookForLadders(G, v);
			}
			// take into account the snakes
			for (int v = G.V() - 1; v > 0; v--) {
				distanceTo[v] = LookForSnakes(G, v);
			}
			// recalculate since snakes might have had change the minimum values
			for (int v = s + 1; v < distanceTo.length; v++) {
				distanceTo[v] = LookForLadders(G, v);
			}
			return distanceTo[G.V() - 1];
		}

		public static int LookForLadders(Graph G, int max) {
			distanceTo[max] = Integer.MAX_VALUE;
			for (int v = 1; v < max; v++) {
				if (G.adj[v].contains(max)) {
					if (max <= v + 6)
						distanceTo[max] = Math.min(distanceTo[max],
								distanceTo[v] + 1);
					else
						distanceTo[max] = Math.min(distanceTo[max],
								distanceTo[v]);
				}
			}
			return distanceTo[max];
		}

		public static int LookForSnakes(Graph G, int min) {
			for (int v = G.V() - 1; v > min; v--) {
				if (G.adj[v].contains(min)) {
					distanceTo[min] = Math.min(distanceTo[min], distanceTo[v]);
				}
			}
			return distanceTo[min];
		}

	}

	// directed graph
	public static class Graph {
		private final int V;
		private HashSet<Integer>[] adj;

		public Graph(int V) {
			this.V = V;
			adj = (HashSet<Integer>[]) new HashSet[V];
			for (int v = 0; v < V; v++) {
				adj[v] = new HashSet<Integer>();
			}
		}

		public void addEdge(int v, int w) {
			adj[v].add(w);
		}

		public Iterable<Integer> adj(int v) {
			return adj[v];
		}

		public static int degree(Graph G, int v) {
			int degree = 0;
			for (int w : G.adj(v))
				degree++;
			return degree;
		}

		public int V() {
			return V;
		}
	}

}
