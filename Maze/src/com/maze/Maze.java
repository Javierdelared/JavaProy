package com.maze;

public class Maze {

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		Matrix mat1 = Matrix.createMockMatrix();
		System.out.println("Time: " + (System.nanoTime() - startTime)/1000000 + " ms");
		System.out.println(mat1.isValidMaze());
		System.out.println("Time: " + (System.nanoTime() - startTime)/1000000 + " ms");
		mat1.drawMaze();
		System.out.println("Time: " + (System.nanoTime() - startTime)/1000000 + " ms");
		mat1.printListNodes();
		System.out.println("Time: " + (System.nanoTime() - startTime)/1000000 + " ms");
		mat1.printSolMaze();
		System.out.println("Time: " + (System.nanoTime() - startTime)/1000000 + " ms");
		mat1.drawSolMaze();
		System.out.println("Time: " + (System.nanoTime() - startTime)/1000000 + " ms");
	}

}
