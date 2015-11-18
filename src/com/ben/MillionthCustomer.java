package com.ben;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class MillionthCustomer {

	private static ArrayList<Product> warehouseItems = new ArrayList<Product>();
	private static int minDimension;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		// read products file
		Scanner scanner = null;
		String[] productInfo = null;
		
		try {
			scanner = new Scanner(new File(args[0]));
			
			Product pEntry = null;
			// product ID, price, length, width, height, weight
			int mId = -1;
			int mPrice = -1;
			int mLength = -1;
			int mWidth = -1;
			int mHeight = -1;
			int mWeight = -1;
			int mDimension = -1;
			double mWeightedRank = -1;
			
			while (scanner.hasNextLine()) {
				
				productInfo = scanner.nextLine().split(",");
				
				mId = Integer.valueOf(productInfo[0]);
				mPrice = Integer.valueOf(productInfo[1]);
				mLength = Integer.valueOf(productInfo[2]);
				mWidth = Integer.valueOf(productInfo[3]);
				mHeight = Integer.valueOf(productInfo[4]);
				mWeight = Integer.valueOf(productInfo[5]);
				
				// if item cannot fit the tote, skip it
				if ((mLength > Tote.height) || (mLength > Tote.width) || (mLength > Tote.length))
					continue;
				else if ((mWidth > Tote.height) || (mWidth > Tote.width) || (mWidth > Tote.length))
					continue;
				else if ((mHeight > Tote.height) || (mHeight > Tote.width) || (mHeight > Tote.length))
					continue;
				
				// get dimension of product
				mDimension = mLength * mWidth * mHeight;
				
				// save minimum dimension
				if (minDimension > mDimension) minDimension = mDimension;
				
				//mWeightedRank = ((1-((double)mDimension/Tote.dimension))*((double)mPrice/mDimension))*Tote.dimension;
				mWeightedRank = (double)mPrice/mDimension;
				
				pEntry = new Product(mId, mPrice, mWeight, mDimension, mWeightedRank);
				warehouseItems.add(pEntry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (scanner!=null) scanner.close();
		}
		
		// order by most efficient price to weight ratio
		warehouseItems.sort(new Comparator<Product>() {
			@Override
			public int compare(Product o1, Product o2) {
				// TODO Auto-generated method stub
				if (o1.getWeightedRank()-o2.getWeightedRank() > 0) return -1;
				else if (o1.getWeightedRank()-o2.getWeightedRank()==0) return 0;
				else return 1;
			}
		});
		
		// add item to tote until remaining space is less than minimum product size
		Tote tote = new Tote();
		for (Product p : warehouseItems) {
			tote.add(p);
			if (tote.getEmptySpace() < minDimension)
				break;
		}
		
		System.out.println("email to : "+tote.getProductIdSum()+"@redmart.com");
		
		tote.printProduct();
		
		
	}	
}
