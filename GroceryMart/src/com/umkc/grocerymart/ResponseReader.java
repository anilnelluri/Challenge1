package com.umkc.grocerymart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ResponseReader {

	public String readDataFromUrl(String url) {

		URL serviceUrl;
		String inputLine = null;
		StringBuilder dataRead = new StringBuilder();
		try {
			serviceUrl = new URL(url);
			URLConnection yc = serviceUrl.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));

			while ((inputLine = in.readLine()) != null) {
				dataRead.append(inputLine);
				System.out.println(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Data returning is: " + dataRead.toString());
		return dataRead.toString();
	}
	
	
	public ArrayList<StoreDetails> getStoreDetailsFromZipcode(String zipcode) {
		
		String url = "http://www.SupermarketAPI.com/api.asmx/StoresByZip?APIKEY=e5fd5ff20a&ZipCode="+zipcode;
		ArrayList<StoreDetails> arrayOfStores = new ArrayList<StoreDetails>();
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			System.out.println("creating DB");
			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(readDataFromUrl(url)));

			doc = db.parse(is);

			System.out.println("content read");
			doc.getDocumentElement().normalize();
			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("Store");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				NodeList nl = nNode.getChildNodes();
				String Name = "";
				String address = "";
				String city = "";
				String state = "";
				int zip = 0;
				for (int i = 0; i < nl.getLength(); i++) {

					if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Node eElement = (Node) nl.item(i);
						if (eElement.getNodeName().equalsIgnoreCase("Storename")) {
							Name = eElement.getTextContent();
						}

						else if (eElement.getNodeName().equalsIgnoreCase("Address"))
							address = eElement.getTextContent();

						else if (eElement.getNodeName().equalsIgnoreCase("City"))
							city = eElement.getTextContent();

						else if (eElement.getNodeName().equalsIgnoreCase("State"))
							state = eElement.getTextContent();

						else if (eElement.getNodeName().equalsIgnoreCase("Zip"))
							zip = Integer.parseInt(eElement.getTextContent());

					}
				}
				arrayOfStores.add(new StoreDetails(Name, address, city, state, zip));
			}

		} catch (ParserConfigurationException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (SAXException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return arrayOfStores;

	}
	
	public ArrayList<ProductInfo> getItemDetailsFromCategory(String item) {
		
		String url = "http://www.SupermarketAPI.com/api.asmx/COMMERCIAL_SearchByProductName?APIKEY=?APIKEY=e5fd5ff20a&ItemName="+item;
		ArrayList<ProductInfo> productList = new ArrayList<ProductInfo>();
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			System.out.println("creating DB");
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(readDataFromUrl(url)));
			
			doc = db.parse(is);
			
			System.out.println("content read");
			doc.getDocumentElement().normalize();
			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("Product_Commercial");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				NodeList nl = nNode.getChildNodes();
				String name = "";
				String description = "";
				String category = "";
				String id = "";
				double price = 0.0;

				for (int i = 0; i < nl.getLength(); i++) {

					if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Node eElement = (Node) nl.item(i);
						if (eElement.getNodeName().equalsIgnoreCase("Itemname")) {
							name = eElement.getTextContent();
						} else if (eElement.getNodeName()
								.equalsIgnoreCase("ItemID"))
							id = eElement.getTextContent();

						else if (eElement.getNodeName().equalsIgnoreCase(
								"ItemDescription"))
							description = eElement.getTextContent();

						else if (eElement.getNodeName().equalsIgnoreCase(
								"ItemCategory"))
							category = eElement.getTextContent();

						else if (eElement.getNodeName().equalsIgnoreCase("Pricing")
								) {
							try {
								price = Double.parseDouble(eElement
										.getTextContent());
							} catch (Exception e) {
							}
						}

					}
				}
				productList.add(new ProductInfo(name, description, category, id, price));
			}
			
		} catch (ParserConfigurationException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (SAXException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return productList;
		
	}
	
}