package gad.bfs;

import java.util.*;
import java.util.stream.IntStream;

public class Graph {
 private  Map<Integer,Set<Integer>> graph;
 private int id;
	public Graph() {
     graph =new HashMap<>();
	id = 0;
	}

	public int addNode() {

		graph.put(id,new HashSet<>());
		return id++;
	}

	public Collection<Integer> getAllNodes() {
		return IntStream.range(0, id).mapToObj(n -> n).toList();
	}

	public Collection<Integer> getAllNeighbours(int id) {
		if(id>=this.id || id<0){
			return null;
		}
		return graph.get(id).stream().toList();
	}

	public void addEdge(int idA, int idB) {
    if(idA>=this.id || idA>=id || idA<0 || idB<0){
		return;
	}
	graph.get(idA).add(idB);
	graph.get(idB).add(idA);
	}

	public int getSize() {
		return graph.size();
	}
}