package gad.binomilia;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BinomialHeap {
	private List<BinomialTreeNode> heap;
	private BinomialTreeNode min;

	public BinomialHeap() {
		heap = new ArrayList<>();
		min = null;

	}

	public int min() {
		if (heap.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return min.min();
		}
	}

	public void insert(int key, Result result) {
		result.startInsert(key,heap);
		BinomialTreeNode node = new BinomialTreeNode(key);

		if (heap.isEmpty()) {
			heap.add(node);
			result.logIntermediateStep(heap);
			min = node;
			return;
		}
		boolean temp = heap.contains(node);
		heap.add(node);
		result.logIntermediateStep(heap);
		if(temp)
		merge(node, result);
		BinomialTreeNode minimum = heap.stream().min(Comparator.comparing(BinomialTreeNode::min)).orElse(null);
		min = minimum;
	}

	public int deleteMin(Result result) {
		result.startDeleteMin(heap);
		if (min==null) {
			throw new NoSuchElementException();
		}
		int value = min();
		heap.remove(min);

		BinomialTreeNode temp = null;
		if(!heap.isEmpty()) {
			temp = heap.stream().filter(n -> n != null).filter(n -> n.rank() < min.rank()).sorted(Comparator.comparing(BinomialTreeNode::rank)).findFirst().orElse(null);
		}
		heap.addAll(min.getChildren());

		result.logIntermediateStep(heap);
		if(temp != null && !min.getChildren().isEmpty())
		merge(min.getChildWithRank(temp.rank()),result);

		if(heap.isEmpty()){
			min=null;
			return value;
		}
		BinomialTreeNode minimum = heap.stream().min(Comparator.comparing(BinomialTreeNode::min)).orElse(null);


			min = minimum;


		return value;
	}

	public void merge(BinomialTreeNode node, Result result) {
		heap.remove(heap.lastIndexOf(node));
		boolean b = true;
		BinomialTreeNode first = heap.contains(node) ? heap.get(heap.lastIndexOf(node)) : null;
		while (first != null ) {
			b = false;
			heap.remove(heap.lastIndexOf(first));
			if (first.min() <= node.min()) {
				BinomialTreeNode.merge(first, node);
				node = first;
				first = heap.contains(node) ? heap.get(heap.lastIndexOf(node)) : null;
			} else {
				BinomialTreeNode.merge(first, node);
				first = heap.contains(node) ? heap.get(heap.lastIndexOf(node)) : null;
			}
			heap.add(node);
			result.logIntermediateStep(heap);
			if(first != null){
				heap.remove(heap.size()-1);
			}

		}
		if(b){
			heap.add(node);
		}
	}

	public static String dot(BinomialTreeNode[] trees) {
		return dot(Arrays.stream(trees).toList());
	}

	public static String dot(Collection<BinomialTreeNode> trees) {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph {").append(System.lineSeparator());
		int id = 0;
		List<Integer> roots = new ArrayList<>();
		for (BinomialTreeNode tree : trees) {
			sb.append(String.format("\tsubgraph cluster_%d {%n", id));
			roots.add(id);
			id = tree.dotNode(sb, id);
			sb.append(String.format("\t}%n"));
		}
		for (int i = 0; i < roots.size() - 1; i++) {
			sb.append(String.format("\t%d -> %d [constraint=false,style=dashed];%n", roots.get(i), roots.get(i + 1)));
		}
		sb.append("}");
		return sb.toString();
	}


	public static void main(String[] args) {
      BinomialHeap bin = new BinomialHeap();
	  Result result = new StudentResult();
		String graph;
		bin.insert(1,result);
	System.out.println( graph = dot(bin.heap));
	  bin.insert(2,result);
		System.out.println( graph = dot(bin.heap));
	  bin.insert(0,result);
		System.out.println( graph = dot(bin.heap));
	  bin.insert(1,result);
		System.out.println( graph = dot(bin.heap));
	  bin.deleteMin(result);
		System.out.println( graph = dot(bin.heap));
	  bin.deleteMin(result);
		System.out.println( graph = dot(bin.heap));
	  bin.deleteMin(result);
		System.out.println( graph = dot(bin.heap));
	  bin.deleteMin(result);
		System.out.println( graph = dot(bin.heap));
	}
}