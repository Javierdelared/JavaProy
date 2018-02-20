package com.maze;

import java.util.HashMap;
import java.util.Map;

public class Path {

		private Map<Integer, Cell> path;

		public Path() {
			this.path = new HashMap<>();
		}

		public Cell getInitialNode() {
			return path.get(0);
		}

		public Cell getFinalNode() {
			return path.get(getDistance()-1);
		}

		public Map<Integer, Cell> getNodePath() {
			return path;
		}

		public int getDistance() {
			return path.size();
		}
		
		public void putCell(Cell cell) {
			path.put(getDistance(), cell);
		}
		
		public void printPath() {
			path.values().forEach( c-> System.out.print(c.printCell() + ", "));
			System.out.println();
		}
		
}
