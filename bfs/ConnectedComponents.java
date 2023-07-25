

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConnectedComponents {
	private BFS search;

	public ConnectedComponents() {
		this.search = new BFS();
	}

	public ConnectedComponents(BFS search) {
		this.search = search;
	}

	public int countConnectedComponents(Graph g) {
     if(g==null ||g.getSize()==0){
		 return 0;
	 }
	 boolean[] bool = new boolean[g.getSize()];
	 int connected = 0 ;

	for (int i=0;i<g.getSize();i++){
	   if(!bool[i]) {
		   search.sssp(g, i);
		   search.getParent().stream().forEach(n ->bool[n]=true);
		   connected++;
	   }
	}
    return connected;
	}
	public static void main(String[] args){
		BFS bfs = new BFS();
		Graph graph = new Graph();
		for(int i = 0;i<250000;i++){
			graph.addNode();
		}
		graph.addEdge(5,8);
		graph.addEdge(8,2000);
		ConnectedComponents connect = new ConnectedComponents(bfs);
		System.out.println(connect.countConnectedComponents(graph));
	}
}