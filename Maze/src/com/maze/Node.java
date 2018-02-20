package com.maze;

import java.util.HashMap;
import java.util.Map;

public class Node {
	
	private Cell posNode;
	private Map<Integer, Path> nodePaths;
	
	public Node(Cell posNode) {
		this.posNode = posNode;
		this.nodePaths = new HashMap<>();
	}

	public Cell getPosNode() {
		return posNode;
	}

	public Map<Integer, Path> getNodePaths() {
		return nodePaths;
	}
	
	public int getNumPaths() {
		return nodePaths.size();
	}

	public void putPath(Path path) {
		nodePaths.put(getNumPaths(), path);
	}
	
}
