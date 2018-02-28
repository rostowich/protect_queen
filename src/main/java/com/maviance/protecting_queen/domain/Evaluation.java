package com.maviance.protecting_queen.domain;

public class Evaluation {

	private Kingdom kingdom;
	
	private Position position;

	public Kingdom getKingdom() {
		return kingdom;
	}

	public void setKingdom(Kingdom kingdom) {
		this.kingdom = kingdom;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "Evaluation [kingdom=" + kingdom + ", position=" + position + "]";
	}

	public Evaluation(Kingdom kingdom, Position position) {
		super();
		this.kingdom = kingdom;
		this.position = position;
	}

	public Evaluation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
