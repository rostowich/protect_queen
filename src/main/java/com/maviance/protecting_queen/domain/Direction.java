package com.maviance.protecting_queen.domain;

public enum Direction {

	NORTH (0,1,"WEST","EAST"),
	SOUTH(0,-1,"EAST","WEST"),
	EAST(1,0,"NORTH","SOUTH"),
	WEST(-1,0,"SOUTH","NORTH");
	
	private int XAddedValue;
	
	private int YAddedValue;
	
	private String leftValue;
	
	private String rightValue;

	public int getXAddedValue() {
		return XAddedValue;
	}

	public int getYAddedValue() {
		return YAddedValue;
	}

	public String getLeftValue() {
		return leftValue;
	}

	public String getRightValue() {
		return rightValue;
	}

	private Direction(int xAddedValue, int yAddedValue, String leftValue, String rightValue) {
		XAddedValue = xAddedValue;
		YAddedValue = yAddedValue;
		this.leftValue = leftValue;
		this.rightValue = rightValue;
	}

	
	
	
}
