
import java.util.*;



public class Dijkstra {
     private int[] distance;
	 private Graph.Node[] predecesor;
	 private Graph.Node s;
	 private Graph.Node g;
	public Dijkstra() {

	}

	public void findRoute(Graph g, Graph.Node start, Graph.Node goal) {
		s = start;
		this.g = goal;
		distance = new int[g.getSize()];
		predecesor = new Graph.Node[g.getSize()];
		PriorityQueue<Pair> nodes = new PriorityQueue<>(Comparator.comparing(Pair::distance));
		for(int i=0;i<distance.length;i++){
			distance[i] = Integer.MAX_VALUE;
		}
		distance[start.getID()] = 0;

		nodes.offer(new Pair(start,0));
		while (!nodes.isEmpty()){
			Pair temp1 = nodes.poll();
			Graph.Node temp = temp1.node();

			if(temp == goal){
				if(start==goal){

				}
				break;
			}
			temp.getNeighbours().forEach(n ->{
				int newDistance = distance[temp.getID()]+temp.getShortestDistanceToNeighbour(n);
				if(newDistance<distance[n.getID()]){
					predecesor[n.getID()] = temp;

					if(distance[n.getID()]==Integer.MAX_VALUE){
						nodes.offer(new Pair(n,newDistance));
					}else {
						nodes.remove(n);
						nodes.offer(new Pair(n,newDistance));
					}
					distance[n.getID()] =newDistance;
				}
			});
		}
		if(distance[this.g.getID()] == Integer.MAX_VALUE){
			throw new RuntimeException();
		}
	}

	public List<Graph.Node> getShortestPath() {
		if(predecesor==null || predecesor[g.getID()] == null){
			return null;
		}
		int current = g.getID();
		List<Graph.Node> temp = new ArrayList<>();
		temp.add(g);
		while(g != s && predecesor[current] != null){
			temp.add(0,predecesor[current]);
			current = predecesor[current].getID();
		}
		return temp;
	}

	public int getShortestPathLength() {
		if(distance==null||distance[g.getID()] ==Integer.MAX_VALUE){
			return 0;
		}
		return distance[g.getID()];
	}


}