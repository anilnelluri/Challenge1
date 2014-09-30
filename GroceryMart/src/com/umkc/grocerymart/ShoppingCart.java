package com.umkc.grocerymart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

	private static ShoppingCart obj = null;
	StoreDetails storeDetails;
	ArrayList<ProductInfo> listOfItems = new ArrayList<ProductInfo>();

	private ShoppingCart() {

	}

	public static ShoppingCart shoppingCartInstance() {
		if (obj == null)
			obj = new ShoppingCart();
		return obj;
	}

	public StoreDetails getStoreDetails() {
		return storeDetails;
	}

	public void setStoreDetails(StoreDetails storeDetails) {
		this.storeDetails = storeDetails;
	}

	public void addAProduct(ProductInfo obj)
	{
		listOfItems.add(obj);
	}
	
	public ArrayList<ProductInfo> getAllItems()
	{
		return listOfItems;
	}
	public double getTotalPrice()
	{
		double ret = 0.0;
		
		for(int i = 0; i<listOfItems.size();i++)
		{
			ret += listOfItems.get(i).getPrice();
		}
		return ret;
	}
	
}
