package com.ben;

public class Product implements Comparable<Product> {

	// product ID, price, length, width, height, weight
	private int id;
	private int price;
	private int weight;
	private int dimension;
	private double weightedRank;
	
	public Product() {
		
	}
	
	public Product(int id, int price, int weight, int dimension, double weightedRank) {
		this.id = id;
		this.price = price;
		this.weight = weight;
		this.dimension = dimension;
		this.weightedRank = weightedRank;
	}
	
	protected int getId() { return this.id; }
	protected int getPrice() { return this.price; }
	protected int getWeight() { return this.weight; }
	protected int getDimension() { return this.dimension; }
	protected double getWeightedRank() { return this.weightedRank; }
	
	@Override
	public String toString() {
		return this.id+","+this.price+","+this.weight+","+this.dimension+","+this.weightedRank;
	}

	@Override
	public int compareTo(Product o) {
		// TODO Auto-generated method stub
		if (o.getWeightedRank()-this.weightedRank < 0) return -1;
		else if (o.getWeightedRank()==weightedRank) return 0;
		else return 1;
	}
	
	@Override
	public boolean equals(Object other) {
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Product))return false;
	    
	    Product o = (Product) other;
	    if (o.getId()==this.id) return true;
	    else return false;
	}
}
