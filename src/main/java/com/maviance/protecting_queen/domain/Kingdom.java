package com.maviance.protecting_queen.domain;

public class Kingdom {

	private int length;
	
	private int width;

	@Override
	public String toString() {
		return "Kindom [length=" + length + ", width=" + width + "]";
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Kingdom(int length, int width) {
		super();
		this.length = length;
		this.width = width;
	}
	
	
}
