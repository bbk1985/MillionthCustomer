package com.ben;

import java.util.ArrayList;

public class Tote {
	
	public static final int dimension = 45*30*35;
	public static final int LENGTH = 45;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 35;
	
	private int value;
	private int weight;
	private int currentSpace;
	
	private int cHeight;
	private int cWidth;
	private int cLength;

	private ArrayList<Product> itemsCombination = null;
	
	public Tote() {
		itemsCombination = new ArrayList<Product>();
		this.value = 0;
		this.weight = 0;
		this.cHeight = 0;
		this.cWidth = 0;
		this.cLength = 0;
		this.currentSpace = dimension;
	}
	
	protected boolean add(Product p) {
		if (isSelected(p)) {
			return false;
		}
		
		// if can fit
		if (canFit(p)) {
			itemsCombination.add(p);
			return true;
		}
	
		// if cannot fit, try reshuffling and add
		return canReshuffle(p);
	}

	protected void updateToteDimensions() {
		int mWidth=0;
		int mHeight=0;
		int mLength=0;

		for (int i=0; i<itemsCombination.size();i++) {
			Product mProduct = itemsCombination.get(i);
			mWidth += mProduct.getWidth();
			mHeight += mProduct.getHeight();
			mLength += mProduct.getLength();
		}
		
		cWidth = mWidth;
		cHeight = mHeight;
		cLength = mLength;
	}
	
	// see if remaining space can fit this side
	protected boolean canFit(int side) {

		// try reshuffling
		for (Product p : itemsCombination) {
			ArrayList<ArrayList<Integer>> rotations = p.getPermutations();
			for (ArrayList<Integer> rotated : rotations) {
				// if current tote can fit product, no need for reshuffling
				updateToteDimensions();
				// if not exceeding dimensions, return true
				if ((WIDTH-(this.cWidth+side)>=0) 
						&& (HEIGHT-(this.cHeight+side)>=0) 
						&& (LENGTH-(this.cLength+side)>=0)) {
					return true;
				}				

				if ((rotated.get(0)==p.getWidth()) 
						&& (rotated.get(1)==p.getHeight()) 
						&& (rotated.get(2)==p.getLength())) {}
				else {
					p.setWidth(rotated.get(0));
					p.setHeight(rotated.get(1));
					p.setLength(rotated.get(2));
				}
			}
		}
		
		// if all else fails
		return false;
	}	
	// checks if a product can fit into current tote in any rotation
	protected boolean canFit(Product p) {

		updateToteDimensions();
		// if not exceeding dimensions, return true
		if ((WIDTH-(this.cWidth+p.getWidth())>=0) 
				&& (HEIGHT-(this.cHeight-p.getHeight())>=0) 
				&& (LENGTH-(this.cLength-p.getLength())>=0)) {
			return true;
		}
		
		// else try and rotate product
		ArrayList<ArrayList<Integer>> rotations = p.getPermutations();
		for (ArrayList<Integer> rotated : rotations) {
			// if current rotation is same
			if ((rotated.get(0)==p.getWidth()) 
					&& (rotated.get(1)==p.getHeight()) 
					&& (rotated.get(2)==p.getLength()))
				continue;
			else { 
				// if rotation results in an OK fit
				if ((WIDTH-(this.cWidth+rotated.get(0))>=0) 
						&& (HEIGHT-(this.cHeight-rotated.get(1))>=0) 
						&& (LENGTH-(this.cLength-rotated.get(2))>=0)) {
					p.setWidth(rotated.get(0));
					p.setHeight(rotated.get(1));
					p.setLength(rotated.get(2));
					return true;
				}
			}
		}
		
		// if all else fails
		return false;
	}
	
	// checks if products in tote can be rearranged
	// needs recursion here
	protected boolean canReshuffle(Product newProduct) {

		for (Product p : itemsCombination) {
			ArrayList<ArrayList<Integer>> rotations = p.getPermutations();
			for (ArrayList<Integer> rotated : rotations) {
				// if current tote can fit product, no need for reshuffling
				if (canFit(newProduct)) {
					return true;
				}
				
				// if can fit, then dont need to reshuffle
				// else continue rotating
				// the idea of doing it before rotating is because if already fits, 
				//		dont need to do for the following products
				
				if ((rotated.get(0)==p.getWidth()) 
						&& (rotated.get(1)==p.getHeight()) 
						&& (rotated.get(2)==p.getLength())) {}
				else {
					p.setWidth(rotated.get(0));
					p.setHeight(rotated.get(1));
					p.setLength(rotated.get(2));
				}
			}
		}
		
		return false;
	}
	
	protected ArrayList<Product> getProducts() {
		return itemsCombination;
	}
	
	protected boolean isSelected(Product p) {
		if (itemsCombination.contains(p))
			return true;
		else return false;
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
		updateTote();
		return this.weight;
	}
	
	protected int getValue() {
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
