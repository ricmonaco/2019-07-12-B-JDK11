package it.polito.tdp.food.model;

public class FoodGrassiDiff {
	
	private Food food;
	private double differenzaGrassi;
	public FoodGrassiDiff(Food food, double differenzaGrassi) {
		super();
		this.food = food;
		this.differenzaGrassi = differenzaGrassi;
	}
	public Food getFood() {
		return food;
	}
	public void setFood(Food food) {
		this.food = food;
	}
	public double getDifferenzaGrassi() {
		return differenzaGrassi;
	}
	public void setDifferenzaGrassi(double differenzaGrassi) {
		this.differenzaGrassi = differenzaGrassi;
	}
	
	

}
