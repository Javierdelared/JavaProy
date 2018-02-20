package com.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Matrix {

	private int rowN;
	private int colN;
	private int[][] matrix;
	private int[][] matrixVisited;
	private Cell sCell;
	private Cell fCell;
	private List<Node> listNodes;
	private Path nodePath;
	private Path completePath;
	private boolean solved;
	private boolean unsolvable;

	public Matrix(int[][] matrixInt) {
		rowN = matrixInt.length;
		colN = matrixInt[0].length;
		matrix = matrixInt;
		matrixVisited = new int[rowN][colN];
		for (int i = 0; i < rowN; i++) {
			Arrays.fill(matrixVisited[i], 1);
		}
		sCell = getSCell();
		fCell = getFCell();
		listNodes = getListNodes();
		solved = false;
		unsolvable = false;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public int getRowN() {
		return rowN;
	}

	public int getColN() {
		return colN;
	}

	public static Matrix createMockMatrix() {
		int[][] mat1 = new int[][] { { 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 1, 1, 1, 1, 0 }, { 0, 0, 1, 0, 0, 0, 1, 0 },
				{ 0, 1, 1, 1, 1, 1, 1, 0 }, { 0, 1, 0, 1, 0, 0, 1, 0 }, { 0, 1, 0, 0, 0, 0, 1, 0 },
				{ 0, 1, 1, 1, 1, 1, 1, 0 }, { 0, 0, 0, 1, 0, 0, 0, 0 } };
		return new Matrix(mat1);
	}

	public Boolean isValidMaze() {

		for (int i = 0; i < rowN; i++) {
			if (matrix[i].length != colN || matrix[i][0] == 1 || matrix[i][colN - 1] == 1) {
				return false;
			}
		}
		if (IntStream.of(matrix[0]).sum() != 1 || IntStream.of(matrix[colN - 1]).sum() != 1) {
			return false;
		}
		return true;
	}

	public void drawMaze() {
		for (int[] row : matrix) {
			for (int cell : row) {
				if (cell == 1) {
					System.out.print("  ");
				} else {
					System.out.print("##");
				}
			}
			System.out.println();
		}
	}

	public List<Node> getListNodes() {

		List<Node> listNodes = new ArrayList<>();
		for (int i = 0; i < colN; i++) {
			if (matrix[0][i] == 1) {
				listNodes.add(new Node(new Cell(0, i)));
			}
		}
		for (int i = 1; i < rowN - 1; i++) {
			for (int j = 1; j < colN - 1; j++) {
				if (matrix[i][j] == 1) {
					int counter = 0;
					if (matrix[i - 1][j] == 1) {
						counter++;
					}
					if (matrix[i + 1][j] == 1) {
						counter++;
					}
					if (matrix[i][j - 1] == 1) {
						counter++;
					}
					if (matrix[i][j + 1] == 1) {
						counter++;
					}
					if (counter > 2) {
						listNodes.add(new Node(new Cell(i, j)));
					}
				}
			}
		}
		for (int i = 0; i < colN; i++) {
			if (matrix[rowN - 1][i] == 1) {
				listNodes.add(new Node(new Cell(rowN - 1, i)));
			}
		}
		return listNodes;
	}

	public void printListNodes() {
		// Very slow for some reason
		// listNodes.stream().forEach(n -> System.out.print(n.getPosNode().printCell() + ", "));
		for(Node n: listNodes) {
			System.out.print(n.getPosNode().printCell() + ", ");
		}
		System.out.println();
	}

	public Cell getSCell() {
		for (int i = 0; i < colN; i++) {
			if (matrix[0][i] == 1) {
				return new Cell(0, i);
			}
		}
		return null;
	}

	public Cell getFCell() {
		for (int i = 0; i < colN; i++) {
			if (matrix[rowN - 1][i] == 1) {
				return new Cell(rowN - 1, i);
			}
		}
		return null;
	}

	public Cell nextCell(Cell cell) {
		int x = cell.getPosX();
		int y = cell.getPosY();
		if (x > 0 && matrix[x - 1][y] == 1 && matrixVisited[x - 1][y] == 1) {
			return new Cell(x - 1, y);
		}
		if (y > 0 && matrix[x][y - 1] == 1 && matrixVisited[x][y - 1] == 1) {
			return new Cell(x, y - 1);
		}
		if (y < colN - 1 && matrix[x][y + 1] == 1 && matrixVisited[x][y + 1] == 1) {
			return new Cell(x, y + 1);
		}
		if (x < rowN - 1 && matrix[x + 1][y] == 1 && matrixVisited[x + 1][y] == 1) {
			return new Cell(x + 1, y);
		}
		return null;
	}

	public int isNode(Cell cell) {
		for (int i = 0; i < listNodes.size(); i++) {
			if (cell.equals(listNodes.get(i).getPosNode())) {
				return i;
			}
		}
		return -1;
	}

	public void solMaze() {

		if (solved || unsolvable) {
			return ;
		}

		Path nodePath = new Path();

		Cell cell = sCell;
		Cell node = sCell;

		Path path1 = new Path();

		while (!node.equals(fCell)) {

			while (isNode(cell) == -1) {
				path1.putCell(cell);
				matrixVisited[cell.getPosX()][cell.getPosY()] = 0;
				while(nextCell(cell) == null) {
					if (cell == sCell) {
						System.out.println("¯\\_(··)_/¯");
						System.out.println("No se ha podido resolver el laberinto");
						unsolvable = true;
						return ;
					}
					if (isNode(cell) != -1) {
						nodePath.getNodePath().remove(nodePath.getDistance()-1);
					}
					cell = nodePath.getFinalNode();
					path1 = new Path();
				}
				cell = nextCell(cell);
			}
			if (!cell.equals(nodePath.getFinalNode())) {
				path1.putCell(cell);
				matrixVisited[cell.getPosX()][cell.getPosY()] = 0;
				if (!cell.equals(sCell)) {
					listNodes.get(isNode(node)).putPath(path1);
					path1 = new Path();
				}
				nodePath.putCell(cell);
				node = cell;
			} else {
				path1 = new Path();
			}
			cell = nextCell(cell);
		}
		solved = true;

		this.nodePath = nodePath;
		this.completePath = getCompletePath();
	}

	public Path getCompletePath() {

		if (this.completePath != null) {
			return completePath;
		}

		Map<Integer, Cell> pathN = new HashMap<>();
		if (solved) {
			pathN = nodePath.getNodePath();
		} else {
			solMaze();
		}
		if (unsolvable) {
			return null;
		}

		Path pathC = new Path();
		int pathNSize = pathN.size();
		for (int i = 1; i < pathNSize; i++) {
			found:
			for (Node n : listNodes) {
				if (n.getPosNode().equals(pathN.get(i-1))) {
					int nodeSize = n.getNumPaths();
					for(int j=0; j < nodeSize; j++) {
						if(n.getNodePaths().get(j).getFinalNode().equals(pathN.get(i))) {
							int pathSize = n.getNodePaths().get(j).getDistance();
							for(int k = 0; k < pathSize; k++) {
								pathC.putCell(n.getNodePaths().get(j).getNodePath().get(k));
							}
							break found;
						}
					}
				}
			}
		}
		return pathC;
	}

	public void printSolMaze() {
		solMaze();
		if(!solved) {
			return ;
		}
		if(solved) {
			nodePath.printPath();
			completePath.printPath();
			System.out.println();
		}
	}

	public void drawSolMaze() {
		solMaze();
		if(!solved) {
			return ;
		}
		Map<Integer, Cell> pathCells = completePath.getNodePath();
		boolean found = false;
		for (int i = 0; i < rowN; i++) {
			for (int j = 0; j < colN; j++) {
				if (matrix[i][j] == 1) {
					found = false;
					for(Cell pathCell: pathCells.values()) {
						if (pathCell.equals(new Cell(i, j))) {
							System.out.print(". ");
							found = true;
						}
					}
					if (!found){
						System.out.print("  ");
					}
				} else {
					System.out.print("##");
				}
			}
			System.out.println();
		}
	}

}
