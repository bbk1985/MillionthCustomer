package com.ben;

import java.util.ArrayList;

public class Tote {
	
	public static final int dimension = 45*30*35;
	public static final int length = 45;
	public static final int width = 30;
	public static final int height = 35;
	
	private int value;
	private int weight;
	private int currentSpace;
	
	private ArrayList<Product> itemsCombination = null;
	
	public Tote() {
		itemsCombination = new ArrayList<Product>();
		this.value = 0;
		this.weight = 0;
		this.currentSpace = dimension;
	}
	
	protected boolean add(Product p) {
		if (isSelected(p)) return false;
		
		if (p.getDimension() > this.currentSpace) return false;
		else {
			itemsCombination.add(p);
			this.currentSpace = this.currentSpace - p.getDimension();
			return true;
		}
	}
	
	protected void remove(int index) {
		itemsCombination.remove(index);
	}
	
	protected boolean isSelected(Product p) {
		if (itemsCombination.contains(p))
			return true;
		else return false;
	}
	
	protected void removeProduct(Product p) {
		itemsCombination.remove(p);
	}
	
	protected boolean hasSpaceFor(Product p) {
		if (this.currentSpace < p.getDimension())
			return false;
		else 
			return true;
	}
	
	protected int getEmptySpace() {
		return this.currentSpace;
	}
	
	protected int getWeight() {
		if (this.weight==0)
			updateTote();
		
		return this.weight;
	}
	
	protected int getValue() {
		if (this.value==0)
			updateTote();
		
		return this.value;
	}
	
	protected void updateTote() {
		int gram = 0;
		int cent = 0;
		
		for (Product p : itemsCombination) {
			gram += p.getWeight();
			cent += p.getPrice();
		}
		
		this.value = cent;
		this.weight = gram;
	}
	
	protected int getProductIdSum() {
		int sum = 0;
		for (Product p : itemsCombination) {
			sum += p.getId();
		}
		return sum;
	}
	
	@Override
	public String toString() {
		
		if (this.weight==0) updateTote();
		
		StringBuilder sb = new StringBuilder();
		for (Product p : itemsCombination) {
			sb.append(p.getDimension() + "+");
		}
		
		return "tote value = " + this.value + ", tote weight = " + this.weight + ", dimension sum = "+ (Tote.dimension-this.currentSpace) + ", dimension = " + sb.toString();
	}
	
	public void printProduct() {
		for (Product p : itemsCombination) {
			System.out.println(p.toString());
		}
	}
}
