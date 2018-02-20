package com.maze;

public class Cell {
	
	private int posX;
	private int posY;
	
	public Cell(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public String printCell() {
		return "(" + posX + ", " + posY + ")";
	}
	
	public boolean equals(Cell cell1) {
		if (cell1 == null || this == null) {
			return false;
		}
		if (this.getPosX()==cell1.getPosX() && this.getPosY()==cell1.getPosY()) {
			return true;
		} else {
			return false;
		}
	}
}
