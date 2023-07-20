package gad.binomilia;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BinomialTreeNode {
	private int element;
    private List<BinomialTreeNode> children;
	public BinomialTreeNode(int element) {
		this.element = element;
		children = new ArrayList<>();
	}

	public int min() {
		return element;
	}

	public int rank() {
		return children.size();
	}

	public BinomialTreeNode getChildWithRank(int rank) {
		if(rank>=rank()){
			return null;
		}

		return children.get(rank);
	}

	public static BinomialTreeNode merge(BinomialTreeNode a, BinomialTreeNode b) {
		if(a.rank() != b.rank()){
			return null;
		}
		if(a.min()<=b.min()){
			a.children.add(b);
			return a;
		}
		b.children.add(a);
		return b;
	}
   public List<BinomialTreeNode> getChildren(){
		return children;
   }

	@Override
	public boolean equals(Object b) {
		BinomialTreeNode temp = (BinomialTreeNode) b;
		return rank()==temp.rank();
	}

	public int dotNode(StringBuilder sb, int idx) {
		sb.append(String.format("\t\t%d [label=\"%d\"];%n", idx, element));
		int rank = rank();
		int next = idx + 1;
		for (int i = 0; i < rank; i++) {
			next = getChildWithRank(i).dotLink(sb, idx, next);
		}
		return next;
	}

	private int dotLink(StringBuilder sb, int idx, int next) {
		sb.append(String.format("\t\t%d -> %d;%n", idx, next));
		return dotNode(sb, next);
	}
}