package com.ben;

import java.util.ArrayList;
import java.util.Arrays;

public class Product implements Comparable<Product> {

	// product ID, price, length, width, height, weight
	private int id;
	private int price;
	private int weight;
	private int dimension;
	private double weightedRank;
	
	private int length;
	private int width;
	private int height;
	
	public Product() {
		
	}
	
	public Product(int id, int price, int weight, int dimension, double weightedRank, int length, int width, int height) {
		this.id = id;
		this.price = price;
		this.weight = weight;
		this.dimension = dimension;
		this.weightedRank = weightedRank;
		
		this.length = length;
		this.width = width;
		this.height = height;
	}
	
	protected int getId() { return this.id; }
	protected int getPrice() { return this.price; }
	protected int getWeight() { return this.weight; }
	protected int getDimension() { return this.dimension; }
	protected double getWeightedRank() { return this.weightedRank; }
	
	protected int getHeight() { return this.height; }
	protected void setHeight(int height) { this.height = height; }
	
	protected int getWidth() { return this.width; }
	protected void setWidth(int width) { this.width = width; }
	
	protected int getLength() { return this.length; }
	protected void setLength(int length) { this.length = length; }
	
	protected ArrayList<ArrayList<Integer>> permutations = null;
	protected ArrayList<ArrayList<Integer>> getPermutations() {
		this.permutations = new ArrayList<ArrayList<Integer>>();
		permute(new ArrayList<Integer>(), new ArrayList<Integer>(Arrays.asList(this.width, this.height, this.length)));
		return this.permutations;
	}
	
	protected void permute(ArrayList<Integer> current, ArrayList<Integer> remainder) {
		if (remainder.size()==0)
			permutations.add(current);
		else {
			for (int i=0; i<remainder.size(); i++) {
				ArrayList<Integer> nextPermutation = new ArrayList<Integer>(current);
				nextPermutation.add(remainder.get(i));
				
				ArrayList<Integer> remainingPermutation = new ArrayList<Integer>(remainder);
				remainingPermutation.remove(remainder.get(i));
				
				permute(nextPermutation, remainingPermutation);
			}
		}
	}
	
	@Override
	public String toString() {
		return this.id+","+this.price+","+this.length+","+this.width+","+this.height+","+this.weight+","+","+this.weightedRank;
	}

	@Override
	public int compareTo(Product o) {
		// TODO Auto-generated method stub
		if (this.weightedRank - o.getWeightedRank() < 0) return -1;
		else if (o.getWeightedRank()==weightedRank) {
			return this.weight-o.getWeight();
		}
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
