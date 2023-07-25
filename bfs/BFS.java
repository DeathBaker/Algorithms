

import java.util.*;

public class BFS {
   private Map<Integer,Integer> distance;
   private Map<Integer,Integer> parent;
	public BFS() {
		distance = new HashMap<>();
		parent = new HashMap<>();
	}

	public void sssp(Graph g, int start) {
		distance = new HashMap<>();

		parent = new HashMap<>();
		distance.put(start,0);
		parent.put(start,start);

		LinkedList<Integer> queue = new LinkedList<>();
		queue.add(start);
		while (!queue.isEmpty()){
			int temp = queue.removeFirst();
			g.getAllNeighbours(temp).forEach(n -> {
				if (!parent.containsKey(n))
				{
					queue.offer(n);
					distance.put(n, distance.get(temp)+1);
					parent.put(n,temp);
				}
			});
		}
	}

	public boolean visitedNode(int node) {
		if(parent != null)
		return parent.containsKey(node) ;
		return false;
	}

	public int getDepth(int node) {
		if(distance != null&&distance.containsKey(node)){
			 return distance.get(node);
		}
		return -1;
	}

	public int getParent(int node) {
		if(parent != null&&parent.containsKey(node)){
			return parent.get(node);
		}
		return -1;
	}

	public Set<Integer> getParent() {
		return parent.keySet();
	}
}
