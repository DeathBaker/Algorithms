

import java.util.*;

public class Graph {

	private List<Node> nodes;
	private int id;

	public static final class Node {
		private int id;
       private List<Pair> weight;
	   public Node(int id){
		   this.id = id;
		   weight = new ArrayList<>();
	   }
		public int getID() {
			return id;
		}

		public Collection<Node> getNeighbours() {
			return weight.stream().map(n->n.node()).distinct().toList();
		}

		public int getShortestDistanceToNeighbour(Node neighbour) {
			Pair temp = weight.stream().filter(n -> n.node().equals(neighbour)).sorted(Comparator.comparing(Pair::distance)).findFirst().orElse(null);
			if(temp == null){
				return 0;
			}
		   return temp.distance();
		}
       public void put(Node n,int weight){
		   this.weight.add(new Pair(n,weight));
	   }

		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Node)){
				return false;
			}
			return getID() == ((Node) obj).getID();
		}
	}

	public Graph() {
		nodes = new ArrayList<>();
		id = 0;
	}

	public int addNode() {
		nodes.add(new Node(id));
		return id++;
	}

	public Node getNode(int id) {
		if(id>=nodes.size() || id<0){
			return null;
		}
		return nodes.get(id);
	}

	public Collection<Node> getAllNodes() {
		return nodes.stream().toList();
	}

	public void addEdge(int from, int to, int weight) {
		if(weight<0){
			throw new IllegalArgumentException();
		}
		if(from<0||from>=nodes.size()||to<0||to>=nodes.size()){
			return;
		}
		nodes.get(from).put(nodes.get(to),weight);
	}
	public int getSize(){
		return id;
	}
	public static Graph of(int... format) {
		Graph graph=new Graph();
		for (int i = 0; i < format[0]; i++) {
			graph.addNode();
		}
		for (int i = 3; i < format.length; i+=2) {
			if(0<=format[i-2] && 0<=format[i-1] && 0<=format[i]) {
				graph.addEdge(format[i-2], format[i], format[i-1]);
			}
		}
		return graph;
	}

}
