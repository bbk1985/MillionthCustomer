package com.ben;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class MillionthCustomer {

	private static ArrayList<Product> warehouseItems = new ArrayList<Product>();
	private static ArrayList<Tote> toteHistory = new ArrayList<Tote>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		// read products file
		Scanner scanner = null;
		String[] productInfo = null;
		
		int minSide = 0;
		
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
				if ((mLength > Tote.HEIGHT) && (mLength > Tote.WIDTH) && (mLength > Tote.LENGTH))
					continue;
				else if ((mWidth > Tote.HEIGHT) && (mWidth > Tote.WIDTH) && (mWidth > Tote.LENGTH))
					continue;
				else if ((mHeight > Tote.HEIGHT) && (mHeight > Tote.WIDTH) && (mHeight > Tote.LENGTH))
					continue;
				
				// keep track of smallest side
				if ((minSide==0)||(minSide>mLength)||(minSide>mWidth)||(minSide>mHeight)) 
					minSide = Math.min(mLength, Math.min(mWidth, mHeight));
				
				// get dimension of product
				mDimension = mLength * mWidth * mHeight;
				
				//mWeightedRank = ((1-((double)mDimension/Tote.dimension))*((double)mPrice/mDimension))*Tote.dimension;
				mWeightedRank = (double)mPrice/mDimension;
				
				pEntry = new Product(mId, mPrice, mWeight, mDimension, mWeightedRank, mLength, mWidth, mHeight);

				warehouseItems.add(pEntry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (scanner!=null) scanner.close();
		}
		
		// order by most efficient price to size ratio
		warehouseItems.sort(new Comparator<Product>() {
			@Override
			public int compare(Product o1, Product o2) {
				// TODO Auto-generated method stub
				if (o1.getWeightedRank()-o2.getWeightedRank() > 0) return -1;
				// if price to size ratio is same, order by lightest weight
				else if (o1.getWeightedRank()-o2.getWeightedRank()==0) {
					return (o1.getWeight()-o2.getWeight());
				}
				else return 1;
			}
		});
		
		// keep adding to tote history
		// will have to implement if minimum size reached then break iteration
		Tote tote = null;
		for (Product ip : warehouseItems) {
			tote = new Tote();
			tote.add(ip);
			for (Product p : warehouseItems) {
				tote.add(p);
				if (!tote.canFit(minSide)) {
					break;
				}
			}
			// add to history list
			toteHistory.add(tote);
			tote = null;
		}
		
		toteHistory.sort(new Comparator<Tote>() {
			@Override
			public int compare(Tote o1, Tote o2) {
				// TODO Auto-generated method stub
				return o2.getValue()-o1.getValue();
			}
		});
		
		tote = toteHistory.get(0);
		for (Product p : tote.getProducts()) {
			System.out.println(p.toString());
		}

		System.out.println("email to : "+tote.getProductIdSum()+"@redmart.com");
	}
}
