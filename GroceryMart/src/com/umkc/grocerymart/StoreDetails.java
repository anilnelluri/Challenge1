package com.umkc.grocerymart;

public class StoreDetails {

	String Name;
	String address;
	String city;
	String state;
	int zip;

	StoreDetails(String Name, String address, String city, String state,
			int zip) {

		this.Name = Name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public String getName() {
		return Name;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public int getZip() {
		return zip;
	}
}
